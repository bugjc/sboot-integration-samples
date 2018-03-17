var behaviorDom = $('#behaviorId');
var tempObjDom = $('#tempObj');
$(function(){

    //提示框
    $("[data-toggle='tooltip']").tooltip();
    //表格分页
    $('#list').DataTable({
        "scrollX"	  : true,
        'paging'      : true,
        'lengthChange': true,
        'searching'   : true,
        'ordering'    : true,
        'info'        : true,
        'autoWidth'   : false,
        "pagingType"  : "full_numbers",
        "pageLength"  : 10
    });
    tempObjDom.tagsinput({
        tagClass: function(item) {
            switch (item.continent) {
                case 'Europe'   : return 'label label-primary';
                case 'America'  : return 'label label-danger label-important';
                case 'Australia': return 'label label-success';
                case 'Africa'   : return 'label label-default';
                //case 'Asia'     : return 'label label-warning';
                case 'Asia'     : return 'label label-info';
            }
        },
        itemValue: 'value',
        itemText: 'text'
    });
    //select init
    selectDataInit(behaviorDom,"/other/list");
    $("#add").click(function(){
        var _this = $(this);
        reloadModelData("/rule/add","添加规则","添加",_this,"add");
        productTableInit();
        $("#ruleModel").modal("show");
    });

    //编辑
    $(".edit").click(function(){
        var _this = $(this);
        reloadModelData("/rule/edit","编辑规则","更新",_this,"edit");
        productTableInit();
        $("#ruleModel").modal("show");
    });

    //选择赠品
    $("#selProductBtn").click(function(){
        $("#ruleModel").modal("hide");
        $("#ruleModel_1").modal("show");
    });

    //确认按钮
    $("#submitBtn").click(function(){
        debugger;
        var id = $("input[name='id']").val();
        var url = $("input[name='url']").val();
        var ruleName = $("input[name='ruleName']").val();
        var behaviorId = $("select[name='behaviorId']").selectpicker('val');
        var tempObj = tempObjDom.val();
        var beginTime = $("input[name='beginTime']").val();
        var endTime = $("input[name='endTime']").val();
        var status = $("select[name='status']").val();

        if(url == '/rule/add'){
            if(!checkAccount(ruleName,behaviorId,tempObj,beginTime,endTime,status)){
                return false;
            }
        }
        var arr = [];
        var tempObjArr = tempObj.split(",");
        $.each(tempObjArr,function(index,item){
            var obj = {
                behaviorId:behaviorId,
                giveProductId:item
            };
            arr.push(obj);
        });
        tempObj = JSON.stringify(arr);
        $.ajax({
            type:"post",
            url:url,
            cache:false,
            dataType:"json",
            data:{id:id,ruleName:ruleName,tempObj:tempObj,startTime:beginTime,endTime:endTime,status:status,time:new Date().getTime()},
            success:function(data){
                if(data.status == 'success'){
                    $("#ruleModel").modal("hide");
                    window.location.href=window.location.href;
                }else{
                    alert(data.msg);
                }
            }
        });
    });

});

//修改状态
function updStatus(id,status){
    $.ajax({
        type:"POST",
        url:"/rule/updateByStatus",
        dataType:"json",
        data:{id:id,status:status},
        cache:false,
        success:function(data){
            if(data.status == 'success'){
                window.location.href=window.location.href;
            }else{
                alert(data.msg);
            }
        }
    });
}

//删除
function del(id,path){
    if(confirm("你确定要删除此类型吗？")){
        $.ajax({
            type:"POST",
            url:"/rule/del",
            dataType:"json",
            data:{id:id},
            cache:false,
            success:function(data){
                if(data.status == 'success'){
                    window.location.href=window.location.href;
                }else{
                    alert(data.msg);
                }
            }
        });
    }
}

//验证数据
function checkAccount(ruleName,behaviorId,tempObj,beginTime,endTime,status){
    if(Reg.isNull(ruleName)){
        $("input[name='ruleName']").focus();
        alert("规则名称不能为空");
        return false;
    }
    if(Reg.isNull(behaviorId)){
        $("select[name='behaviorId']").focus();
        alert("行为不能为空");
        return false;
    }
    if(Reg.isNull(tempObj)){
        $("input[name='tempObj']").focus();
        alert("赠品不能为空");
        return false;
    }
    if (Reg.isNull(beginTime) || Reg.isNull(endTime)) {
        $("input[name='dateTimeRange']").focus();
        alert("存活时间不能为空！");
        return false;
    }
    if(Reg.isNull(status)){
        $("select[name='status']").focus();
        alert("状态不能为空");
        return false;
    }
    return true;
}

//加载模态框的数据
function reloadModelData(url,title,btntext,_this,type){
    debugger;
    $("#ruleModel #modelHead").text(title);
    $("#ruleModel #submitBtn").text(btntext);
    $("input[name='type']").val(type);
    $("input[name='url']").val(url);
    if(type == 'add'){
        $("input[name='ruleName']").val("");
        $("select[name='behaviorId']").val("");
        $("select[name='status']").val("");
        $("textarea[name='tempObj']").val("");
        $("#dateTimeRange").val("");
        $("input[name='beginTime']").val("");
        $("input[name='endTime']").val("");
        behaviorDom.selectpicker("val","");
        dateTimeRangeInit(new Date(),new Date());
    }else{
        //获取obj
        var obj = _this.parents("tr").find("input").val();
        $("input[name='obj']").val(obj);
        obj = JSON.parse(obj);
        $("input[name='id']").val(obj.id);
        $("input[name='ruleName']").val(obj.ruleName);
        $("select[name='behaviorId']").val(obj.behaviorId);
        $("select[name='status']").val(obj.status);
        $("#dateTimeRange").val(obj.startTime+" 至 "+obj.endTime);
        $("input[name='beginTime']").val(obj.startTime);
        $("input[name='endTime']").val(obj.endTime);
        //行为选中
        behaviorDom.selectpicker("val",obj.behaviorId);
        //data range 选中
        dateTimeRangeInit(obj.startTime,obj.endTime);

    }
}

//初始化select数据
function selectDataInit(_this,url) {
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        data: {},
        cache: false,
        success: function (data) {
            if (data.status == 'success') {
                _this.empty();
                _this.append("<option value=''>请选择</option>");
                $.each(data.data, function (index, item) {
                    _this.append("<option value='" + item.id + "'>" + item.name + "</option>");
                });
                _this.selectpicker({
                    width: '100%',
                    size: 5
                });
                _this.selectpicker('refresh');
            } else {
                alert(data.msg);
            }
        }
    });
}

//商品列表init
function productTableInit(){
    product_table = $('#giveProductList').DataTable({
        destroy: true,
        select: {
            style: 'multi'
        },
        "ajax": '/give/product/listByStatus',
        "columns": [
            {
                "data": null, render: function (data, type, row, meta) {
                    // 显示行号
                    var startIndex = meta.settings._iDisplayStart;
                    return startIndex + meta.row + 1;
                }
            },
            {"data": "id", visible: false},
            {"data": "name", render: function (data, type, row, meta) {
                    return data;
                }},
            {"data": "giveProductTypeName"},
            {"data": "giveType", render: function (data, type, row, meta) {
                if (data == '0'){
                    return '满减'
                }else if (data == '1'){
                    return '折扣'
                }else{
                    return data;
                }

                }},
            {"data": "startTime"},
            {"data": "endTime"}
        ],
        "createdRow": function( row, data, dataIndex ) {
            debugger;
            var type = $("input[name='type']").val();
            if (type == "edit"){
                var obj = $("input[name='obj']").val();
                obj = JSON.parse(obj);
                var arr = (obj.giveProductId).split(",");
                if (isEqu(arr,data.id)) {
                    var obj = {
                        "value":data.id,
                        "text":data.name,
                        "continent":"Europe"
                    }
                    tempObjDom.tagsinput("add",obj);
                    $(row).addClass( 'selected');
                }
            }

        }

    });
}

//确定选择事件
$("#selectBtn").click(function () {
    var select_result = product_table.rows({selected: true}).data();
    console.info(select_result);
    if(select_result.length == 0){
        alert("请至少选择一行！");
        return false;
    }
    $.each(select_result, function (index, item) {
        var obj = {
            "value":item.id,
            "text":item.name,
            "continent":"Europe"
        };
        tempObjDom.tagsinput('add', obj);
    });
    debugger;
    $("#ruleModel_1").modal("hide");
    $("#ruleModel").modal("show");
});
//日期区间
function dateTimeRangeInit(startDate,endDate){
    //开始结束时间选择
    $('#dateTimeRange').daterangepicker({
        startDate: startDate,
        endDate: endDate,
        applyClass: 'btn-sm btn-success',
        cancelClass: 'btn-sm btn-default',
        locale: {
            applyLabel: '确认',
            cancelLabel: '取消',
            fromLabel: '起始时间',
            toLabel: '结束时间',
            customRangeLabel: '自定义时间',
            firstDay: 1
        },
        ranges: {},
        opens: 'right',	// 日期选择框的弹出位置
        separator: ' 至 ',
        showWeekNumbers: true,		// 是否显示第几周


        //timePicker: true,
        //timePickerIncrement : 10,	// 时间的增量，单位为分钟
        //timePicker12Hour : false,	// 是否使用12小时制来显示时间


        //maxDate : moment(),			// 最大时间
        format: 'YYYY-MM-DD'

    }, function (start, end, label) { // 格式化日期显示框
        // $("input[name='beginTime']").val(start.format('YYYY-MM-DD'));
        // $('input[name="endTime"]').val(end.format('YYYY-MM-DD'));
        $("input[name='beginTime']").val(start.format('YYYY-MM-DD'));
        $('input[name="endTime"]').val(end.format('YYYY-MM-DD'));
    }).next().on('click', function () {
        $(this).prev().focus();
    });
}

function isEqu(arr,id){
    var flag = false;
    $.each(arr, function (index, item) {
        if (id == item){
            flag = true;
        }
    });
    return flag;
}
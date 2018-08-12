//全局dom变量
var businessDom = $('#businessId');
var limitValueDom = $('#limitValue');
var giveProductTypeDom = $('#giveProductTypeId');
//全局对象
var product_table;

$(function () {

    //提示框
    $("[data-toggle='tooltip']").tooltip();
    //select init
    selectDataInit(giveProductTypeDom,"/give/product/type/list");
    selectDataBusinessInit(businessDom,"/business/list");
    //dateTimeRange
    dateTimeRangeInit(new Date(),new Date());
    //tags
    limitValueDom.tagsinput();
    //表格分页
    $('#list').DataTable({
        "scrollX": true,
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
        "pagingType": "full_numbers",
        "pageLength": 10
    });

    //添加-1
    $("#add").click(function () {
        var _this = $(this);
        reloadModelData_1("/give/product/add",_this,"add");
        productTableInit();
        $("#giveProductModel_1").modal("show");
    });

    //编辑-1
    $(".edit").click(function(){
        var _this = $(this);
        reloadModelData_1("/give/product/edit",_this, "edit");
        productTableInit();
        $("#giveProductModel_1").modal("show");
    });

    //添加或编辑-1
    $("#submitBtn_2").click(function () {
        hideDomAll(0);
        $("#giveProductModel_2").modal("hide");
        $("#giveProductModel_1").modal("show");
    });

    //添加或编辑-2
    $("#submitBtn_1").click(function () {
        hideDomAll($("select[name='limitCondition']").val());
        var type = $("input[name='type']").val();
        reloadModelData_2(type);
        //校验第一步表单
        var name = $("input[name='name']").val();
        var giveProductTypeId = $("select[name='giveProductTypeId']").val();
        var useThreshold = $("input[name='useThreshold']").val();
        var giveType = $("select[name='giveType']").val();
        var giveValue = $("input[name='giveValue']").val();
        if(!checkAccount_1(name, giveProductTypeId, useThreshold,giveType,giveValue)){
            return false;
        }
        //校验通过打开第2步窗口
        $("#giveProductModel_1").modal("hide");
        $("#giveProductModel_2").modal("show");
    });

    //确认按钮
    $("#submitBtn").click(function () {
        debugger;
        var id = $("input[name='id']").val();
        var url = $("input[name='url']").val();
        var name = $("input[name='name']").val();
        var giveProductTypeId = $("select[name='giveProductTypeId']").val();
        var useThreshold = $("input[name='useThreshold']").val();
        var giveType = $("select[name='giveType']").val();
        var giveValue = $("input[name='giveValue']").val();

        var limitCondition = $("select[name='limitCondition']").val();
        var businessId = $("select[name='businessId']").selectpicker('val');
        var limitValue = $("input[name='limitValue']").val();
        var inventoryNumber = $("input[name='inventoryNumber']").val();
        var beginTime = $("input[name='beginTime']").val();
        var endTime = $("input[name='endTime']").val();
        var status = $("select[name='status']").val();
        var description = $("textarea[name='description']").val();
        if (url == '/give/product/add') {
            if (!checkAccount_2(limitCondition,businessId, limitValue,inventoryNumber,beginTime,endTime,status,description)) {
                return false;
            }
        }
        if(limitCondition == '1'){
            limitValue = businessId.toString();
        }else if(limitCondition == '2'){
            limitValue = $("input[name='limitValueTemp']").val();
        }
        debugger;
        $.ajax({
            type: "post",
            url: url,
            cache: false,
            dataType: "json",
            data: {
                id: id,
                name: name,
                giveProductTypeId: giveProductTypeId,
                useThreshold: useThreshold,
                giveType: giveType,
                giveValue: giveValue,
                limitCondition: limitCondition,
                limitValue: limitValue,
                inventoryNumber: inventoryNumber,
                startTime: beginTime,
                endTime:endTime,
                status: status,
                description: description,
                time: new Date().getTime()
            },
            success: function (data) {
                if (data.status == 'success') {
                    $("#giveProductModel_2").modal("hide");
                    window.location.href = window.location.href;
                } else {
                    alert(data.msg);
                }
            }
        });
    });

});

//加载模态框的数据
function reloadModelData_1(url,_this,type) {
    $("input[name='type']").val(type);
    $("input[name='url']").val(url);
    debugger;
    if (type == 'edit') {
        //获取obj
        var obj = _this.parents("tr").find("input").val();
        $("input[name='obj']").val(obj);
        obj = JSON.parse(obj);
        $("input[name='name']").val(obj.name);
        $("input[name='id']").val(obj.id);
        $("select[name='giveProductTypeId']").val(obj.giveProductTypeId);
        $("input[name='useThreshold']").val(obj.useThreshold);
        $("select[name='giveType']").val(obj.giveType);
        $("input[name='giveValue']").val(obj.giveValue);
        //赠品类型选中
        giveProductTypeDom.selectpicker("val",obj.giveProductTypeId);
        $("#giveProductModel_1 #modelHead_1").text("编辑赠品信息（1）");
    }else{
        $("input[name='name']").val("");
        $("input[name='id']").val("");
        $("select[name='giveProductTypeId']").val("");
        $("input[name='useThreshold']").val("");
        $("select[name='giveType']").val("");
        $("input[name='giveValue']").val("");
        $("select[name='limitCondition']").val("0");
        $("input[name='limitValue']").val("");
        $("input[name='inventoryNumber']").val("");
        $("#dateTimeRange").val("");
        $("input[name='beginTime']").val("");
        $("input[name='endTime']").val("");
        $("select[name='status']").val("");
        $("textarea[name='description']").val("");
        giveProductTypeDom.selectpicker("val","");
        dateTimeRangeInit(new Date(),new Date());
        $("#giveProductModel_1 #modelHead_1").text("添加赠品信息（1）");
    }
};

//加载模态框的数据
function reloadModelData_2(type) {
    debugger;
    if (type == 'edit') {
        var obj = $("input[name='obj']").val();
        obj = JSON.parse(obj);
        $("select[name='limitCondition']").val(obj.limitCondition);
        $("input[name='inventoryNumber']").val(obj.inventoryNumber);
        $("#dateTimeRange").val(obj.startTime+" 至 "+obj.endTime);
        $("input[name='beginTime']").val(obj.startTime);
        $("input[name='endTime']").val(obj.endTime);
        $("select[name='status']").val(obj.status);
        $("textarea[name='description']").val(obj.description);
        $("#giveProductModel_2 #modelHead_2").text("编辑赠品信息（2）");
        $("#giveProductModel_2 #submitBtn").text("更新");
        dateTimeRangeInit(obj.startTime,obj.endTime);
        if(obj.limitCondition == '1'){
            var arr = obj.limitValue.split(",");
            //限制业务选中
            businessDom.selectpicker('val',arr);
            businessDom.parents(".form-group").removeClass("hide").addClass("show");
        }else if (obj.limitCondition == '2'){
            var arr = obj.limitValue.split(",");
            //限制产品选中
            limitValueDom.parents(".form-group").removeClass("hide").addClass("show");
        }
    }else{
        $("#giveProductModel_2 #modelHead_2").text("添加赠品信息（2）");
        $("#giveProductModel_2 #submitBtn").text("添加");
    }
};

//修改状态
function updStatus(id,status){
    $.ajax({
        type:"POST",
        url:"/give/product/updateByStatus",
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
function del(id, path) {
    if (confirm("你确定要删除此赠品吗？")) {
        $.ajax({
            type: "POST",
            url: "/give/product/del",
            dataType: "json",
            data: {id: id},
            cache: false,
            success: function (data) {
                if (data.status == 'success') {
                    window.location.href = window.location.href;
                } else {
                    alert(data.msg);
                }
            }
        });
    }
}

//优惠类型
$("#giveType").change(function () {
    var _this = $(this);
    if(_this.val() == "0"){
        $("#giveValueDiv").empty().append("<input type=\"text\" class=\"form-control\" name=\"giveValue\" value=\"\" id=\"giveValue\" placeholder=\"请输入减免金额；例：5（减免5元）\">")
    }else{
        $("#giveValueDiv").empty().append("<input type=\"text\" class=\"form-control\" name=\"giveValue\" value=\"\" id=\"giveValue\" placeholder=\"请输入折扣；例：9.5（打9.5折）\">")
    }
});

//验证数据-1
function checkAccount_1(name, giveProductTypeId, useThreshold,giveType,giveValue){
    if (Reg.isNull(name)) {
        $("input[name='name']").focus();
        alert("赠品名称不能为空！");
        return false;
    }
    if (Reg.isNull(giveProductTypeId)) {
        $("select[name='giveProductTypeId']").focus();
        alert("赠品类型不能为空！");
        return false;
    }
    if (!Reg.intOrFloat(useThreshold)) {
        $("input[name='useThreshold']").focus();
        alert("使用门槛只能是整数或小数！");
        return false;
    }
    if (Reg.isNull(giveType)) {
        $("select[name='giveType']").focus();
        alert("优惠类型不能为空！");
        return false;
    }
    if (!Reg.intOrFloat(giveValue)) {
        $("input[name='giveValue']").focus();
        alert("优惠值不能为空！");
        return false;
    }
    return true;
}

function checkAccount_2(limitCondition,businessId, limitValue,inventoryNumber,beginTime,endTime,status,description){
    if (limitCondition == '1' && Reg.isNull(businessId)) {
        $("select[name='businessId']").focus();
        alert("限制的业务未选择！");
        return false;
    }
    if (limitCondition == '2' && Reg.isNull(limitValue)) {
        $("select[name='businessId']").focus();
        alert("限制的产品未选择！");
        return false;
    }
    if (!Reg.noZeroInt(inventoryNumber)) {
        $("input[name='inventoryNumber']").focus();
        alert("库存数量必须是一个非零的正整数！");
        return false;
    }
    if (Reg.isNull(beginTime) || Reg.isNull(endTime)) {
        $("input[name='dateTimeRange']").focus();
        alert("存活时间不能为空！");
        return false;
    }
    if (Reg.isNull(status)) {
        $("select[name='status']").focus();
        alert("赠品状态不能为空！");
        return false;
    }
    if (Reg.isNull(description)) {
        $("textarea[name='description']").focus();
        alert("赠品描述不能为空！");
        return false;
    }
    return true;
}

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

//初始化select数据
function selectDataBusinessInit(_this,url) {
    $.ajax({
        type: "POST",
        url: url,
        dataType: "json",
        data: {},
        cache: false,
        success: function (data) {
            debugger;
            if (data.status == 'success') {
                _this.empty();
                _this.append("<option disabled>请选择</option>");
                var arrName = new Array();
                var tempName = data.data[0].groupName;
                arrName.push(tempName);
                var tempGroup = "<optgroup label='"+tempName+"' >";
                $.each(data.data, function (index, item) {
                    if (tempName != item.groupName){
                        if(!isEqu(arrName,item.groupName)){
                            tempName = item.groupName;
                            tempGroup += "</optgroup>";
                            _this.append(tempGroup);
                            arrName.push(tempName);
                            tempGroup = "<optgroup label='"+tempName+"' >";
                        }
                     }
                    tempGroup +="<option>" + item.name + "</option>";
                });
                tempGroup += "</optgroup>";
                _this.append(tempGroup);
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
    product_table = $('#productList').DataTable({
        destroy: true,
        select: {
            style: 'multi'
        },
        "ajax": '/product/listByStatus',
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
            {"data": "productTypeName"},
            {"data": "price"},
            {"data": "inventoryNumber"},
            {"data": "createTime"}
        ],
        "createdRow": function( row, data, dataIndex ) {
            debugger;
            var type = $("input[name='type']").val();
            if (type == "edit"){
                var obj = $("input[name='obj']").val();
                obj = JSON.parse(obj);
                var arr = (obj.limitValue).split(",");
                if (obj.limitCondition == '2' && isEqu(arr,data.id)) {
                    limitValueDom.tagsinput("add", data.name);
                    $(row).addClass( 'selected' );
                }
            }

        }

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

//确定选择事件
$("#selectBtn").click(function () {
    var select_result = product_table.rows({selected: true}).data();
    if(select_result.length == 0){
        alert("请至少选择一行！");
        return false;
    }
    var productIds = "";
    $.each(select_result, function (index, item) {
        productIds += item.id+",";
        limitValueDom.tagsinput("add", item.name);
    });
    debugger;
    productIds = productIds.substring(0,productIds.length-1);
    $("input[name='limitValueTemp']").val(productIds);
    $("#giveProductModel_2_1").modal("hide");
    $("#giveProductModel_2").modal("show");
    limitValueDom.parents(".form-group").removeClass("hide").addClass("show");
});

//下拉点击事件
function simOptionClick4IE() {
    var evt = window.event;
    var selectObj = evt ? evt.srcElement : null;
    // IE Only
    if (evt && selectObj && evt.offsetY && evt.button != 2
        && (evt.offsetY > selectObj.offsetHeight || evt.offsetY < 0)) {

        // 记录原先的选中项
        var oldIdx = selectObj.selectedIndex;

        setTimeout(function () {
            var option = selectObj.options[selectObj.selectedIndex];
            // 此时可以通过判断 oldIdx 是否等于 selectObj.selectedIndex
            // 来判断用户是不是点击了同一个选项,进而做不同的处理.
            showOptionValue(option)

        }, 60);
    }
};

//限制条件option被点击
function showOptionValue(opt, msg) {
    var value = $(opt).val();
    hideDomAll(0);
    if (value == '1') {
        //显示业务列表
        businessDom.parents(".form-group").removeClass("hide").addClass("show");

    } else if (value == '2') {
        //打开产品模态窗口
        $("#giveProductModel_2").modal("hide");
        $("#giveProductModel_2_1").modal("show");

    }
}

//需要隐藏的dom
function hideDomAll(code){
    if ( code == '0'){
        businessDom.parents(".form-group").removeClass("show").addClass("hide");
        limitValueDom.parents(".form-group").removeClass("show").addClass("hide");
    }else if(code == '1'){
        businessDom.parents(".form-group").removeClass("hide").addClass("show");
        limitValueDom.parents(".form-group").removeClass("show").addClass("hide");
    }else if(code == '2'){
        businessDom.parents(".form-group").removeClass("show").addClass("hide");
        limitValueDom.parents(".form-group").removeClass("hide").addClass("show");
    }

}

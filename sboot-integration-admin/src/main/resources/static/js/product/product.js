//全局dom变量
var giveProductTypeDom = $('#productTypeId');

$(function(){
    //提示框
    $("[data-toggle='tooltip']").tooltip();
    
    selectDataInit(giveProductTypeDom,"/product/type/list");
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

    $("#add").click(function(){
        reloadModelData("/product/add","添加产品","添加","","","","","","","","add");
        $("#giveProductTypeModel").modal("show");
    });

    //确认按钮
    $("#submitBtn").click(function(){
        var id = $("input[name='id']").val();
        var url = $("input[name='url']").val();
        var name = $("input[name='name']").val();
        var productTypeId = $("input[name='productTypeId']").val();
        var image = $("input[name='image']").val();
        var price = $("input[name='price']").val();
        var inventoryNumber = $("input[name='inventoryNumber']").val();
        var status = $("select[name='status']").val();
        if(url == '/product/add'  || url == "/product/edit"){
            if(!checkAccount(name,productTypeId,inventoryNumber)){
                return false;
            }
        }
        var data;
        if(url == '/product/add') {
        	data = {id:id,name:name,productTypeId:productTypeId,image:image,price:price,inventoryNumber:inventoryNumber,status:status,"createTime":new Date()};
        }else {
        	data = {id:id,name:name,productTypeId:productTypeId,image:image,price:price,inventoryNumber:inventoryNumber,status:status,"updateTime":new Date()};
        }
        $.ajax({
            type:"post",
            url:url,
            cache:false,
            dataType:"json",
            data:data,
            success:function(data){
                if(data.status == 'success'){
                    $("#giveProductTypeModel").modal("hide");
                    window.location.href=window.location.href;
                }else{
                    alert(data.msg);
                }
            }
        });
    });

});

//删除
function del(id){
    if(confirm("你确定要删除此类型吗？")){
        $.ajax({
            type:"POST",
            url:"/product/del",
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

//编辑
function edit(id,name,productTypeId,image,price,inventoryNumber,status){
    console.log(id+","+name+","+productTypeId+","+image+","+price+","+inventoryNumber+","+status);
    reloadModelData("/product/edit","编辑产品","更新",id,name,productTypeId,image,price,inventoryNumber,status,"edit");
    $("#giveProductTypeModel").modal("show");
}

//验证数据
function checkAccount(name,productTypeId,inventoryNumber){
    if(name == ''){
        $("input[name='name']").focus();
        alert("产品名称不能为空");
        return false;
    }
    if(productTypeId == ''){
        $("input[name='productTypeId']").focus();
        alert("产品类型不能为空");
        return false;
    }
    if(inventoryNumber == ''){
        $("input[name='inventoryNumber']").focus();
        alert("库存不能为空");
        return false;
    }
    return true;
}

//加载模态框的数据
function reloadModelData(url,title,btntext,id,name,productTypeId,image,price,inventoryNumber,status,type){
    $("#giveProductTypeModel #modelHead").text(title);
    $("#giveProductTypeModel #submitBtn").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='id']").val(id);
    $("input[name='name']").val(name);
    $("select[name='productTypeId']").val(productTypeId);
    $("input[name='image']").val(image);
    $("input[name='price']").val(price);
    $("input[name='inventoryNumber']").val(inventoryNumber);
    $("select[name='status']").val(status);
    if(type == 'add'){
        //TODO 不同的动作执行不同的数据完整性校验
    }else{
    }
}

//修改状态
function updStatus(id,status){
    $.ajax({
        type:"POST",
        url:"/product/updateByStatus",
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


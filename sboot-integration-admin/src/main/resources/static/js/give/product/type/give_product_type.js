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

    $("#add").click(function(){
        reloadModelData("/give/product/type/add","添加赠送品类型","添加","","","","0","","add");
        $("#giveProductTypeModel").modal("show");
    });

    //确认按钮
    $("#submitBtn").click(function(){
        var id = $("input[name='id']").val();
        var url = $("input[name='url']").val();
        var name = $("input[name='name']").val();
        var code = $("input[name='code']").val();
        var status = $("select[name='status']").val();
        var description = $("textarea[name='description']").val();
        if(url == '/give/product/type/add'){
            if(!checkAccount(name,code,description)){
                return false;
            }
        }
        $.ajax({
            type:"post",
            url:url,
            cache:false,
            dataType:"json",
            data:{id:id,name:name,code:code,status:status,description:description,time:new Date().getTime()},
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
function del(id,path){
    if(confirm("你确定要删除此类型吗？")){
        $.ajax({
            type:"POST",
            url:"/give/product/type/del",
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

//修改状态
function updStatus(id,status){
    $.ajax({
        type:"POST",
        url:"/give/product/type/updateByStatus",
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

//编辑
function edit(id,name,code,status,description){
    console.log(id+","+name+","+code+"|,"+status+","+description);
    reloadModelData("/give/product/type/edit","编辑赠送品类型","更新",id,name,code,status,description,"edit");
    $("#giveProductTypeModel").modal("show");
}

//验证数据
function checkAccount(name,code,description){
    if(Reg.isNull(name)){
        $("input[name='name']").focus();
        alert("类型名称不能为空");
        return false;
    }
    if(Reg.isNull(code)){
        $("input[name='code']").focus();
        alert("编码不能为空");
        return false;
    }
    if(Reg.isNull(description)){
        $("textarea[name='description']").focus();
        alert("类型描述不能为空");
        return false;
    }
    return true;
}

//加载模态框的数据
function reloadModelData(url,title,btntext,id,name,code,status,description,type){
    $("#giveProductTypeModel #modelHead").text(title);
    $("#giveProductTypeModel #submitBtn").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='id']").val(id);
    $("input[name='name']").val(name);
    $("input[name='code']").val(code);
    $("select[name='status']").val(status);
    $("textarea[name='description']").val(description);
    if(type == 'add'){
        //TODO 不同的动作执行不同的数据完整性校验
    }else{

    }
}
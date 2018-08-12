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
        reloadModelData("/product/type/add","添加产品类型","添加","","","","","add");
        $("#giveProductTypeModel").modal("show");
    });

    //确认按钮
    $("#submitBtn").click(function(){
        var id = $("input[name='id']").val();
        var url = $("input[name='url']").val();
        var name = $("input[name='name']").val();
        var image = $("input[name='image']").val();
        var status = $("select[name='status']").val();
        var createTime = $("input[name='createTime']").val();
        var updateTime = $("input[name='updateTime']").val();
        if(url == '/other/add'  || url == "/other/edit"){
            if(!checkAccount(name,code,description)){
                return false;
            }
        }
        var data;
        if(url == '/product/type/add') {
        	data = {id:id,name:name,image:image,status:status,"createTime":new Date()};
        }else {
        	data = {id:id,name:name,image:image,status:status,"updateTime":new Date()};
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
function del(id,path){
    if(confirm("你确定要删除此类型吗？")){
        $.ajax({
            type:"POST",
            url:"/product/type/del",
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
function edit(id,name,image,status){
    console.log(id+","+name+","+image+","+status);
    reloadModelData("/product/type/edit","编辑产品类型","更新",id,name,image,status,"edit");
    $("#giveProductTypeModel").modal("show");
}

//验证数据
function checkAccount(name){
    if(name == ''){
        $("input[name='name']").focus();
        alert("产品名称不能为空");
        return false;
    }
    /*if(code == ''){
        $("input[name='code']").focus();
        alert("编码不能为空");
        return false;
    }
    if(description == ''){
        $("textarea[name='description']").focus();
        alert("类型描述不能为空");
        return false;
    }*/
    return true;
}

//加载模态框的数据
function reloadModelData(url,title,btntext,id,name,image,status,type){
    $("#giveProductTypeModel #modelHead").text(title);
    $("#giveProductTypeModel #submitBtn").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='id']").val(id);
    $("input[name='name']").val(name);
    $("input[name='image']").val(image);
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
        url:"/product/type/updateByStatus",
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


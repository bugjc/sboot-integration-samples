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
        reloadModelData("/other/add","添加用户行为","添加","","","","0","0","",new Date().getTime(),"add");
        $("#giveProductTypeModel").modal("show");
    });

    //确认按钮
    $("#submitBtn").click(function(){
        var id = $("input[name='id']").val();
        var url = $("input[name='url']").val();
        var name = $("input[name='name']").val();
        var code = $("input[name='code']").val();
        var type = $("select[name='type']").val();
        var status = $("select[name='status']").val();
        var description = $("textarea[name='description']").val();
        var createTime = $("input[name='createTime']").val();
        var updateTime = $("input[name='updateTime']").val();
        if(url == '/other/add'  || url == "/other/edit"){
            if(!checkAccount(name,code,description)){
                return false;
            }
        }
        var data;
        if(url == '/other/add') {
        	data = {id:id,name:name,code:code,type:type,status:status,description:description,"createTime":new Date()};
        }else {
        	data = {id:id,name:name,code:code,type:type,status:status,description:description,"updateTime":new Date()};
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
            url:"/other/del",
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
function edit(id,name,code,status,behaviorType,description){
    console.log(id+","+name+","+code+"|,"+status+","+description);
    reloadModelData("/other/edit","编辑用户行为","更新",id,name,code,status,behaviorType,description,new Date().getTime(),"edit");
    $("#giveProductTypeModel").modal("show");
}

//验证数据
function checkAccount(name,code,description){
    if(name == ''){
        $("input[name='name']").focus();
        alert("类型名称不能为空");
        return false;
    }
    if(code == ''){
        $("input[name='code']").focus();
        alert("编码不能为空");
        return false;
    }
    if(description == ''){
        $("textarea[name='description']").focus();
        alert("类型描述不能为空");
        return false;
    }
    return true;
}

//加载模态框的数据
function reloadModelData(url,title,btntext,id,name,code,status,behaviorType,description,time,type){
    $("#giveProductTypeModel #modelHead").text(title);
    $("#giveProductTypeModel #submitBtn").text(btntext);
    $("input[name='url']").val(url);
    $("input[name='id']").val(id);
    $("input[name='name']").val(name);
    $("input[name='code']").val(code);
    $("select[name='type']").val(behaviorType);
    $("select[name='status']").val(status);
    $("textarea[name='description']").val(description);
    if(type == 'add'){
        //TODO 不同的动作执行不同的数据完整性校验
    	$("input[name='craetTime']").val(time);
    }else{
    	$("input[name='updateTime']").val(time);
    }
}

//获取用户名
function userName(user_id,qx,title){
	/*var zNodes =[];
	reloadQXModal(id,qx,title);*/
	$.ajax({
		type:"POST",
		url:"/other/userName",
		cache:false,
		data:{user_id:user_id},
		dataType:"json",
		success:function(data){
			if(data.status == 'success'){
				alert("创建用户名："+data.userName);
				//return data.userName;
			}else{
				alert(data.msg);
				//return "";
			}
		}
	});
}


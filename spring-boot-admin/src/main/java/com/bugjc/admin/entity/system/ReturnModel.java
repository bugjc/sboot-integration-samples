package com.bugjc.admin.entity.system;

import com.bugjc.admin.util.Tools;

import java.util.HashMap;

public class ReturnModel{

	private static HashMap<String,Object> model=null;
	
	private ReturnModel() {}
	
	public static HashMap<String, Object> getModel(String msg,String status,Object data){
		if(model == null){
			model = new HashMap<>();
		}
		if(Tools.notEmpty(msg)){
			model.put("msg", msg);
		}
		if(Tools.notEmpty(status)){
			model.put("status", status);
		}
		if(data != null){
			model.put("data", data);
		}else{
			model.put("data", null);
		}
		return model;
	}

	public static HashMap<String, Object> getSuccessModel(){
		if(model == null){
			model = new HashMap<>();
		}
		model.put("status", "success");
		model.put("msg", "操作成功");
		return model;
	}

	public static HashMap<String, Object> getSuccessModel(String msg,Object data){
		if(model == null){
			model = new HashMap<>();
		}
		model.put("status", "success");
		if(Tools.notEmpty(msg)){
			model.put("msg", msg);
		}
		if(data != null){
			model.put("data", data);
		}else{
			model.put("data", null);
		}
		return model;
	}
	
	public static HashMap<String, Object> getErrorModel(){
		if(model == null){
			model = new HashMap<>();
		}
		model.put("status", "failed");
		model.put("msg", "你请求的是一个冒牌接口");
		return model;
	}

	public static HashMap<String, Object> getErrorModel(String msg){
		if(model == null){
			model = new HashMap<>();
		}
		model.put("status", "failed");
		model.put("msg", msg);
		return model;
	}

	public static HashMap<String, Object> getNotAuthModel(){
		if(model == null){
			model = new HashMap<>();
		}
		model.put("status", "notauth");
		model.put("msg", "你权限不足");
		return model;
	}
}

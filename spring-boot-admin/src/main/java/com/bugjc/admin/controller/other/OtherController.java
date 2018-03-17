package com.bugjc.admin.controller.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bugjc.admin.config.GlobalProperty;
import com.bugjc.admin.controller.BaseController;
import com.bugjc.admin.entity.system.Const;
import com.bugjc.admin.entity.system.ReturnModel;
import com.bugjc.admin.entity.system.User;
import com.xiaoleilu.hutool.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/other")
public class OtherController extends BaseController {
	
	@Autowired
	private GlobalProperty globalProperty;

    /**
     * 行为列表
     * @return
     */
    @RequestMapping(value="/list",method= RequestMethod.GET)
    public Object list(Model model){

    	//请求参数设置
        Map<String,Object> paramMap = this.getParameterMap();
        User user = ((User)this.getSession().getAttribute(Const.SESSION_USER));
        paramMap.put("msUserId",user.getUserId());
    	String jsonStr = HttpUtil.post(globalProperty.serviceBaseUrl+"/other/list", paramMap);
        JSONObject parseObj = JSON.parseObject(jsonStr);
        //解析接收对象
        if (parseObj.getInteger("code") != 200){
            return ReturnModel.getErrorModel(parseObj.getString("message"));
        }
        JSONObject data = (JSONObject) parseObj.get("data");
        model.addAttribute("list", data.get("list"));
    	System.out.println(data);
    	return  "page/behavior/list";
    }

    @PostMapping("/list")
    @ResponseBody
    public Object list(){
        //1、发起远程调用
        String jsonStr = HttpUtil.post(this.getServiceUrl(OtherApi.LIST_URL),new HashMap<>());
        //2、解析接收对象
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (jsonObject.getInteger("code") != 200){
            return ReturnModel.getErrorModel(jsonObject.getString("message"));
        }
        JSONObject data = (JSONObject) jsonObject.get("data");
        return ReturnModel.getSuccessModel("操作成功！",data.get("list"));
    }
    
    /**
     * 行为编辑
     * @return
     */
    @RequestMapping(value="/edit")
    @ResponseBody
    public Object edit(){
    	Map<String,Object> paramMap = this.getParameterMap();
        String jsonStr = HttpUtil.post(globalProperty.serviceBaseUrl+"/other/update",paramMap);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        if (jsonObject.getInteger("code") != 200){
            return ReturnModel.getErrorModel(jsonObject.getString("message"));
        }
        return ReturnModel.getSuccessModel();
    }
    
    /**
     * 行为新增
     * @return
     */
    @RequestMapping(value="/add")
    @ResponseBody
    public Object add(){
    	 Map<String,Object> paramMap = this.getParameterMap();
    	 User user = ((User)this.getSession().getAttribute(Const.SESSION_USER));
     	 paramMap.put("msUserId", user.getUserId());
         String jsonStr = HttpUtil.post(globalProperty.serviceBaseUrl+"/other/add",paramMap);
         JSONObject jsonObject = JSON.parseObject(jsonStr);
         if (jsonObject.getInteger("code") != 200){
             return ReturnModel.getErrorModel(jsonObject.getString("message"));
         }
         return ReturnModel.getSuccessModel();
    }
    
    /**
     * 行为删除
     * @return
     */
    @RequestMapping(value="/del")
    @ResponseBody
    public Object del(){
    	 Map<String,Object> paramMap = this.getParameterMap();
         String jsonStr = HttpUtil.post(globalProperty.serviceBaseUrl+"/other/delete",paramMap);
         JSONObject jsonObject = JSON.parseObject(jsonStr);
         if (jsonObject.getInteger("code") != 200){
             return ReturnModel.getErrorModel(jsonObject.getString("message"));
         }
         return ReturnModel.getSuccessModel();
    }

}

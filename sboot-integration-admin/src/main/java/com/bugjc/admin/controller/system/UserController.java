package com.bugjc.admin.controller.system;

import com.bugjc.admin.controller.BaseController;
import com.bugjc.admin.entity.system.Const;
import com.bugjc.admin.entity.system.ReturnModel;
import com.bugjc.admin.entity.system.User;
import com.bugjc.admin.service.system.IUserService;
import com.bugjc.admin.util.Jurisdiction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	private String menuUrl = "user/list";
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public Object login(Model model){
		if(!Jurisdiction.buttonJurisdiction(menuUrl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		model.addAttribute("users", userService.getUserList());
		model.addAttribute("meid", ((User)this.getSession().getAttribute(Const.SESSION_USER)).getUserId());
		return "page/system/user/list";
	}
	
	/**
	 * 获取用户角色
	 * @return
	 */
	@RequestMapping(value="/getRole",method=RequestMethod.GET)
	@ResponseBody
	public Object userRole(){
		if(!Jurisdiction.buttonJurisdiction(menuUrl,"edit", this.getSession())){return ReturnModel.getNotAuthModel();}
		return userService.getRole(this.getParameterMap());
	}
	
	/**
	 * 添加用户
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Object add(){
		if(!Jurisdiction.buttonJurisdiction(menuUrl,"add", this.getSession())){return ReturnModel.getNotAuthModel();}
		return userService.add(this.getParameterMap());
	}
	
	
	/**
	 * 编辑用户
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Object edit(){
		if(!Jurisdiction.buttonJurisdiction(menuUrl,"edit", this.getSession())){return ReturnModel.getNotAuthModel();}
		return userService.edit(this.getParameterMap());
	}
	
	/**
	 * 编辑用户
	 * @return
	 */
	@RequestMapping(value="/editRole",method=RequestMethod.POST)
	@ResponseBody
	public Object editRole(){
		if(!Jurisdiction.buttonJurisdiction(menuUrl,"edit", this.getSession())){return ReturnModel.getNotAuthModel();}
		return userService.editRole(this.getParameterMap());
	}
	
	/**
	 * 删除用户
	 * @return
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public Object del(){
		if(!Jurisdiction.buttonJurisdiction(menuUrl,"del", this.getSession())){return ReturnModel.getNotAuthModel();}
		return userService.del(this.getParameterMap());
	}
	
	
}

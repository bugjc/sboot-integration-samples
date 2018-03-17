package com.bugjc.admin.dao.system;

import com.bugjc.admin.entity.system.Role;
import com.bugjc.admin.entity.system.User;
import com.bugjc.admin.util.ParameterMap;

import java.util.List;

public interface UserDao {
	public User getUserInfo(ParameterMap pm);
	public List<Role> getUserRoleList(ParameterMap pm);
	public List<ParameterMap> getUserList();
	public void saveLoginTime(String userId);
	public void saveUser(ParameterMap pm);
	public void bathSaveUserRole(List<ParameterMap> parame);
	public void editUser(ParameterMap pm);
	public void delUser(String userId);
	public void delUserRole(String userId);
}

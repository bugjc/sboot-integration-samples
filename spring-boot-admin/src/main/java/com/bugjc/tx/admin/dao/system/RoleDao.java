package com.bugjc.tx.admin.dao.system;

import com.bugjc.admin.util.ParameterMap;
import com.bugjc.tx.admin.util.ParameterMap;

import java.util.List;

public interface RoleDao {
	public List<ParameterMap> list();
	public List<ParameterMap> getRoleByuId(ParameterMap pm);
	public ParameterMap getRoleById(ParameterMap pm);
	public void updateRoleQX(ParameterMap pm);
	public void addRole(ParameterMap pm);
	public void delRole(String roleId);
	public void delUserRole(String roleId);
}

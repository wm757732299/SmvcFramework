package com.wm.service;

import java.util.List;
import java.util.Map;

import com.wm.mapper.entity.SysRole;
import com.wm.service.base.IBaseService;

public interface SysRoleService extends IBaseService<SysRole> {

	public List<SysRole> queryRoleList(Map<String, Object> param);

	public void batDelete(String[] ids);

	public List<Map<String, Object>> queryAutzeUserList(Map<String, Object> param);

	public void authorizeUser(String roleId, String[] userIds);

	public void cancelAuthorize(String roleId, String[] userIds);

	/**
	 * 
	 * @param roleId
	 * @param selectNode 新增的权限节点
	 * @param removeNode 移除的权限节点
	 * @param rmNodeType 移除节点的类型（父节点或叶子节点）
	 */
	public void authorityUpdate(String roleId, String selectNode,
			String removeNode,String rmNodeType);

}

package com.wm.service;

import java.util.List;

import com.wm.mapper.entity.SysAction;
import com.wm.service.base.IBaseService;

public interface SysActionService extends IBaseService<SysAction> {

	public void batDelete(String[] ids);

	/**
	 * 根据登录用户id查询允许的行为权限
	 * 
	 * @param userId
	 */
	public List<SysAction> queryLoginAct(String userId);

	/**
	 * 根据登录用户id查询某个菜单下指定角色的行为权限授权状态
	 * 
	 * @param userId
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public List<SysAction> queryMenuAct(String userId, String menuId, String roleId);

	/**
	 * 为指定角色授权操作
	 * 
	 * @param actIds
	 * @param roleId
	 * @return
	 */
	public void addAct(String[] actIds, String roleId);

	/**
	 * 删除指定角色行为权限
	 * 
	 * @param actIds
	 * @param roleId
	 * @return
	 */
	public void removeAct(String[] actIds, String roleId);
}
package com.wm.service;

import java.util.List;

import com.wm.mapper.entity.SysAction;
import com.wm.service.base.IBaseService;

public interface SysActionService extends IBaseService<SysAction>{

	public void batDelete(String[] ids);

	/**
	 * 根据登录用户id查询允许的操作行为
	 * @param userId
	 */
	public List<SysAction> queryLoginAct(String userId);

}
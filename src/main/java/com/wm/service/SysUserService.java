package com.wm.service;

import java.util.List;
import java.util.Map;

import com.wm.mapper.entity.SysUser;
import com.wm.service.base.IBaseService;

public interface SysUserService extends IBaseService<SysUser>{

	public List<SysUser> queryUserList(Map<String, Object> param);
	
}

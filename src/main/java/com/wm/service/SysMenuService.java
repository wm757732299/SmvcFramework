package com.wm.service;

import java.util.List;

import com.wm.mapper.entity.SysMenu;
import com.wm.service.base.IBaseService;

public interface SysMenuService extends IBaseService<SysMenu>{

	public List<SysMenu> queryMenuInfo(String condition);

	public List<SysMenu> queryMenuTree(SysMenu sysMenu);

	public long queryCount(SysMenu sysMenu);
}

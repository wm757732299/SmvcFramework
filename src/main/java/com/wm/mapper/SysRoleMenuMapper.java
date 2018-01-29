package com.wm.mapper;


import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.SysRoleMenu;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("sysRoleMenuMapper")
public interface SysRoleMenuMapper extends IBaseMapper<SysRoleMenu> {

	public void deleteByCondition(SysRoleMenu srm);
	
	/**
	 * 查询给定角色和父节点下的子节点总数
	 * @param srm
	 */
	public Integer queryNodeCount(SysRoleMenu srm);
	
}
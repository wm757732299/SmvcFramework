package com.wm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.SysMenu;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("sysMenuMapper")
public interface SysMenuMapper extends IBaseMapper<SysMenu> {

	public List<SysMenu> queryMenuInfo(String condition);

	public List<SysMenu> queryMenuTree(SysMenu sysMenu);

}

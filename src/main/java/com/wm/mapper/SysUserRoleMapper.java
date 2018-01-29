package com.wm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.SysUserRole;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("sysUserRoleMapper")
public interface SysUserRoleMapper extends IBaseMapper<SysUserRole> {

	public List<Map<String, Object>>  queryAutzeUserList(Map<String, Object> param);

	public void deleteByCondition(SysUserRole sur);
}
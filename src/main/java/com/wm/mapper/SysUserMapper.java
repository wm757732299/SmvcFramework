package com.wm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.SysUser;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("sysUserMapper")
public interface SysUserMapper extends IBaseMapper<SysUser> {
	
    /**
     * 获取用户所有权限信息
     * 
     * @param uAccount
     * @return
     */
    public List<Map<String, Object>> getAuthList(String uAccount);
}

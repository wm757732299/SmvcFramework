package com.wm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.SysUserMapper;
import com.wm.mapper.entity.SysUser;
import com.wm.service.SysUserService;

@Service("sysUserService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class SysUserServiceImpl implements SysUserService {

	@Resource(type = SysUserMapper.class)
	private SysUserMapper sysUserMapper;

	public long insert(SysUser t) {
		return sysUserMapper.insert(t);
	}

	public long delete(SysUser t) {
		// TODO Auto-generated method stub
		return sysUserMapper.deleteTrueByKey(t.getId());
	}

	public long update(SysUser t) {
		// TODO Auto-generated method stub
		return sysUserMapper.update(t);
	}

	public SysUser queryByKey(String id) {
		// TODO Auto-generated method stub
		return sysUserMapper.queryByKey(id);
	}

	public List<SysUser> queryByCondition(SysUser t) {
		// TODO Auto-generated method stub
		return sysUserMapper.queryByCondition(t);
	}

	public List<SysUser> queryByPage(SysUser t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SysUser> queryUserList(Map<String, Object> param) {
//		System.out.println(SysUserServiceImpl.class.getAnnotation(DataSourceChoose.class).sourceKey()); 获得注解值
		PageHelper.startPage((Integer)param.get("curPage"), (Integer)param.get("pageSize"));
		return  sysUserMapper.queryByPage(param);
	}

}

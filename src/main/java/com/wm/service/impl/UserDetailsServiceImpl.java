package com.wm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.SysUserMapper;
import com.wm.mapper.entity.SysUser;
import com.wm.model.LoginUserDetails;
import com.wm.service.SysUserService;

/**
 * 自定义用户认证管理器
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2017.12.06
 *
 */

@Service("userDetailsService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource(type = SysUserService.class)
	private SysUserService sysUserService;

	@Resource(type = SysUserMapper.class)
	private SysUserMapper sysUserMapper;

	public LoginUserDetails loadUserByUsername(String uAccount)
			throws UsernameNotFoundException, DataAccessException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

		SysUser su = new SysUser();
		su.setuAccount(uAccount);
		List<SysUser> uList = sysUserMapper.queryByCondition(su);
		if (uList == null || uList.size() == 0) {
			throw new UsernameNotFoundException("用户名不存在");
		}
		if (uList.size() > 1) {
			throw new UsernameNotFoundException("用户名异常");
		}
		SysUser loginer = uList.get(0);
		String password = loginer.getuPwd();

		List<Map<String, Object>> authList = sysUserMapper
				.getAuthList(uAccount);
		if (authList == null || authList.size() == 0) {
			throw new UsernameNotFoundException("该用户无权限登录");
		}

		Integer roleLevel=null;
		try {
			StringBuffer sb = new StringBuffer();
			
			for (Map<String, Object> authMap : authList) {
				String authName = authMap.get("roleUnique").toString();
				sb.append(","+authMap.get("roleName"));
				auths.add(new SimpleGrantedAuthority(authName));
			}
			sb.delete(0, 1);
			loginer.setRoleName(sb.toString());
			roleLevel = Integer.parseInt(authList.get(0).get("roleLevel")
					.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("解析权限异常");
		}

		return getLoginUser(uAccount, password, auths, roleLevel, loginer);
	}

	private LoginUserDetails getLoginUser(String uAccount, String password,
			Collection<GrantedAuthority> auths, Integer roleLevel,
			SysUser sysUser) {
		LoginUserDetails loginUserDetails = new LoginUserDetails(uAccount,
				password, true, true, true, true, auths);
		loginUserDetails.setSysUser(sysUser);
		loginUserDetails.setRoleLevel(roleLevel);
		return loginUserDetails;
	}
}

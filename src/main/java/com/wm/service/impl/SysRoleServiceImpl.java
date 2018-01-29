package com.wm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.SysRoleMapper;
import com.wm.mapper.SysRoleMenuMapper;
import com.wm.mapper.SysUserRoleMapper;
import com.wm.mapper.entity.SysMenu;
import com.wm.mapper.entity.SysRole;
import com.wm.mapper.entity.SysRoleMenu;
import com.wm.mapper.entity.SysUserRole;
import com.wm.service.SysRoleService;

@Service("sysRoleService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class SysRoleServiceImpl implements SysRoleService {

	@Resource(type = SysRoleMapper.class)
	private SysRoleMapper sysRoleMapper;

	@Resource(type = SysUserRoleMapper.class)
	private SysUserRoleMapper sysUserRoleMapper;

	@Resource(type = SysRoleMenuMapper.class)
	private SysRoleMenuMapper sysRoleMenuMapper;

	public long insert(SysRole t) {
		return sysRoleMapper.insert(t);
	}

	public long delete(SysRole t) {
		return sysRoleMapper.deleteTrueByKey(t.getId());
	}

	public void batDelete(String[] ids) {
		SysRoleMenu srm =new SysRoleMenu();
		SysUserRole sur = new SysUserRole();
		for (int i = 0; i < ids.length; i++) {
			
			srm.setRoleId(ids[i]);
			sysRoleMenuMapper.deleteByCondition(srm );
			
			sur.setRoleId(ids[i]);
			sysUserRoleMapper.deleteByCondition(sur );
			
			sysRoleMapper.deleteTrueByKey(ids[i]);
		}
	}

	public long update(SysRole t) {
		return sysRoleMapper.update(t);
	}
	
	public SysRole queryByKey(String id) {
		return sysRoleMapper.queryByKey(id);
	}

	public List<SysRole> queryByCondition(SysRole t) {
		return sysRoleMapper.queryByCondition(t);
	}

	public List<SysRole> queryByPage(SysRole t) {
		return null;
	}

	public List<SysRole> queryRoleList(Map<String, Object> param) {
		// System.out.println(SysUserServiceImpl.class.getAnnotation(DataSourceChoose.class).sourceKey());
		// 获得注解值
		PageHelper.startPage((Integer) param.get("curPage"),
				(Integer) param.get("pageSize"));
		return sysRoleMapper.queryByPage(param);
	}

	public List<Map<String, Object>> queryAutzeUserList(
			Map<String, Object> param) {
		PageHelper.startPage((Integer) param.get("curPage"),
				(Integer) param.get("pageSize"));
		return sysUserRoleMapper.queryAutzeUserList(param);
	}

	public void authorizeUser(String roleId, String[] userIds) {

		SysUserRole sur = new SysUserRole();
		sur.setRoleId(roleId);
		sur.setTimeStamp(new Timestamp(new Date().getTime()));
		for (int i = 0; i < userIds.length; i++) {
			sur.setUserId(userIds[i]);
			sysUserRoleMapper.insert(sur);
		}

	}

	public void cancelAuthorize(String roleId, String[] userIds) {
		SysUserRole sur = new SysUserRole();
		sur.setRoleId(roleId);
		for (int i = 0; i < userIds.length; i++) {
			sur.setUserId(userIds[i]);
			sysUserRoleMapper.deleteByCondition(sur);
		}
	}

	public void authorityUpdate(String roleId, String selectNode,
			String removeNode,String rmNodeType) {
		if (selectNode != null&&!"".equals(selectNode)) {
			String[] seNodeIds = selectNode.split(",");
			if (seNodeIds.length > 0 ) {
				SysRoleMenu srm = new SysRoleMenu();
				srm.setRoleId(roleId);
				Timestamp timestamp =new Timestamp(new Date().getTime());
				for (int i = 0; i < seNodeIds.length; i++) {
					srm.setMenuId(seNodeIds[i]);
						List<SysRoleMenu> list = sysRoleMenuMapper.queryByCondition(srm);
						if(list!=null&&list.size()>0){
							srm =list.get(0);
							srm.setTimeStamp(timestamp);
							sysRoleMenuMapper.update(srm);
						}else{
							srm.setTimeStamp(timestamp);
							sysRoleMenuMapper.insert(srm);
						}
				}
			}
		}

		
		if (removeNode != null&&!"".equals(removeNode)) {
			String[] rmNodeIds = removeNode.split(",");
			String[] rmNodeTypes = rmNodeType.split(",");
			if (rmNodeIds.length > 0 && rmNodeIds.length == rmNodeTypes.length) {
				SysRoleMenu srm = new SysRoleMenu();
				srm.setRoleId(roleId);
				List<String> prentNodes = new ArrayList<String>();
				for (int i = 0; i < rmNodeIds.length; i++) {
					srm.setMenuId(rmNodeIds[i]);
					if(SysMenu.PARENT_NODE.toString().equals(rmNodeTypes[i])){
						prentNodes.add(rmNodeIds[i]);
					}else{
						sysRoleMenuMapper.deleteByCondition(srm);
					}
					
				}
				if(prentNodes.size()>0){
					for (int i = 0; i < prentNodes.size(); i++) {
						srm.setMenuId(prentNodes.get(i));
						Integer count =	sysRoleMenuMapper.queryNodeCount(srm);
						if(count == 0){
							sysRoleMenuMapper.deleteByCondition(srm);
						}
					}
				}
				
			}
		}

	}
}
package com.wm.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.SysActionMapper;
import com.wm.mapper.SysRoleActionMapper;
import com.wm.mapper.entity.SysAction;
import com.wm.mapper.entity.SysRoleAction;
import com.wm.mapper.entity.SysUser;
import com.wm.service.SysActionService;

@Service("sysActionService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class SysActionServiceImpl implements SysActionService {

	@Resource(type = SysActionMapper.class)
	private SysActionMapper sysActionMapper;
	
	@Resource(type = SysRoleActionMapper .class)
	private SysRoleActionMapper sysRoleActionMapper ;

	public long insert(SysAction t) {
		return sysActionMapper.insert(t);
	}

	public long delete(SysAction t) {
		return sysActionMapper.deleteTrueByKey(t.getId());
	}

	public long update(SysAction t) {
		return sysActionMapper.update(t);
	}

	public SysAction queryByKey(String id) {
		return sysActionMapper.queryByKey(id);
	}

	public List<SysAction> queryByCondition(SysAction t) {
		return sysActionMapper.queryByCondition(t);
	}

	public List<SysAction> queryByPage(SysAction t) {
		return null;
	}

	public void batDelete(String[] ids) {
		SysRoleAction sra = new SysRoleAction();
		for (int i = 0; i < ids.length; i++) {
			sra.setActionId(ids[i]);
			sysRoleActionMapper.deleteByCondition(sra);
			sysActionMapper.deleteTrueByKey(ids[i]);
		}
	}

	public List<SysAction> queryLoginAct(String userId) {
		return sysActionMapper.queryLoginAct(userId);
		
	}

	public List<SysAction> queryMenuAct(String userId,String menuId,String roleId) {
		Map<String, String> param = new HashMap<String, String>();
		if(!SysUser.SYS_ADMIN.equals(userId)){
			param.put("userId", userId);
		}
		param.put("menuId", menuId);
		param.put("roleId", roleId);
		return sysActionMapper.queryMenuAct(param);
	}

	public void addAct(String[] actIds, String roleId) {
		SysRoleAction sra = new SysRoleAction();
		sra.setRoleId(roleId);
		sra.setTimeStamp(new Date());
		for (String actId : actIds) {
			sra.setActionId(actId);
		    sysRoleActionMapper.insert(sra);
		}
	}

	public void removeAct(String[] actIds, String roleId) {
		SysRoleAction sra = new SysRoleAction();
		sra.setRoleId(roleId);
		for (String actId : actIds) {
			sra.setActionId(actId);
			sysRoleActionMapper.deleteByCondition(sra);
		}
	}

}
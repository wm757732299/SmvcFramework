package com.wm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.SysActionMapper;
import com.wm.mapper.entity.SysAction;
import com.wm.service.SysActionService;

@Service("sysActionService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class SysActionServiceImpl implements SysActionService {

	@Resource(type = SysActionMapper.class)
	private SysActionMapper sysActionMapper;

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
		for (int i = 0; i < ids.length; i++) {
			sysActionMapper.deleteTrueByKey(ids[i]);
		}
	}

	public List<SysAction> queryLoginAct(String userId) {
		return sysActionMapper.queryLoginAct(userId);
		
	}

}
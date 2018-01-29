package com.wm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.SysMenuMapper;
import com.wm.mapper.entity.SysMenu;
import com.wm.service.SysMenuService;

@Service("sysMenuService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class SysMenuServiceImpl implements SysMenuService {

	@Resource(type = SysMenuMapper.class)
	private SysMenuMapper sysMenuMapper;

	public long insert(SysMenu t) {
		// TODO Auto-generated method stub
		return sysMenuMapper.insert(t);
	}

	public long delete(SysMenu t) {
		if(SysMenu.CHILD_NODE==t.getNodeType()){
			sysMenuMapper.deleteTrueByKey(t.getId());
		}else if(SysMenu.PARENT_NODE==t.getNodeType()){
			List<SysMenu> children = t.getChildren();
			for (int i = 0; i < children.size(); i++) {
				this.delete(children.get(i));
			}
			sysMenuMapper.deleteTrueByKey(t.getId());
		}
		return 0;
	}

	public long update(SysMenu t) {
		// TODO Auto-generated method stub
		return sysMenuMapper.update(t);
	}

	public SysMenu queryByKey(String id) {
		// TODO Auto-generated method stub
		return sysMenuMapper.queryByKey(id);
	}

	public List<SysMenu> queryByCondition(SysMenu t) {
		// TODO Auto-generated method stub
		return sysMenuMapper.queryByCondition(t);
	}

	public List<SysMenu> queryByPage(SysMenu t) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SysMenu> queryMenuInfo(String condition) {
		// TODO Auto-generated method stub
		return sysMenuMapper.queryMenuInfo(condition);
	}

	public List<SysMenu> queryMenuTree(SysMenu sysMenu) {
		// TODO Auto-generated method stub
		return sysMenuMapper.queryMenuTree(sysMenu);
	}

	public long queryCount(SysMenu sysMenu){
		return sysMenuMapper.queryCount(sysMenu);
	}
}

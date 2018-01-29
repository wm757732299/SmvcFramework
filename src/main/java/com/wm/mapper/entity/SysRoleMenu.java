package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;

public class SysRoleMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1084567406227265884L;

	private String id;
	private String roleId;
	private String menuId;
	private Date timeStamp;
	private int deleteMark;

	public SysRoleMenu() {
		this.deleteMark = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getDeleteMark() {
		return deleteMark;
	}

	public void setDeleteMark(int deleteMark) {
		this.deleteMark = deleteMark;
	}

}

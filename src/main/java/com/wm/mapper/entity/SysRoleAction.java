package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;

public class SysRoleAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1684366515868705941L;

	private String id;
	private String roleId;
	private String actionId;
	private Date timeStamp;
	private int deleteMark;

	public SysRoleAction() {
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

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
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

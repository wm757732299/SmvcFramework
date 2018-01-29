package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;

public class SysUserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 573232161370626635L;

	private String id;
	private String userId;
	private String roleId;
	private Date timeStamp;
	private int deleteMark;

	public SysUserRole() {
		this.deleteMark = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;

public class SysRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4007421181647701375L;

	public static final String ROLE_USER ="ROLE_USER";
	
	private String id;
	private String roleUnique;
	private String roleName;
	private Integer roleLevel;
	private String remarks;
	private Date timeStamp;
	private int deleteMark;

	public SysRole() {
		this.deleteMark = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleUnique() {
		return roleUnique;
	}

	public void setRoleUnique(String roleUnique) {
		this.roleUnique = roleUnique;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

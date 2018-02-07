package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;

public class SysAction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6601786866453807446L;

	private String id;
	private Integer actCode;
	private String actName;
	private String actUrl;
	private Integer actType;
	private String menuId;
	private Date createTime;
	private Date timeStamp;
	private int deleteMark;
	
	//-----非持久化字段-----//
	
	private int auttyMark;//是否选中
	
	private String menuName;

	public SysAction() {
		this.deleteMark = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getActCode() {
		return actCode;
	}

	public void setActCode(Integer actCode) {
		this.actCode = actCode;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getActUrl() {
		return actUrl;
	}

	public void setActUrl(String actUrl) {
		this.actUrl = actUrl;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getAuttyMark() {
		return auttyMark;
	}

	public void setAuttyMark(int auttyMark) {
		this.auttyMark = auttyMark;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
}

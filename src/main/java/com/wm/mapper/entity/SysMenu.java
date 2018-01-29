package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6970018888988495392L;
	
	/**
	 * 叶子节点
	 */
	public static final Integer CHILD_NODE = 0;
	/**
	 * 分支节点、父节点
	 */
	public static final Integer PARENT_NODE = 1;
	
	
	private String id;
	private String menuCode;
	private String menuName;
	private String menuIcon;
	private String menuUrl;
	private String menuSort;
	private String menuType;
	private String menuParent;
	private Integer isAvailable;
	private Integer nodeType;
	private Date timeStamp;
	private int deleteMark;

	//-----非持久化字段-----//
	
	private List<SysMenu> children;
	
	private int auttyMark;//是否选中
	private String userId;
	private String roleId;
	
	public SysMenu() {
		this.deleteMark=0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
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

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public int getAuttyMark() {
		return auttyMark;
	}

	public void setAuttyMark(int auttyMark) {
		this.auttyMark = auttyMark;
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

}

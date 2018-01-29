package com.wm.mapper.entity;

import java.io.Serializable;
import java.util.Date;

public class Video implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2380726323899725804L;

	private String id;
	private String vName;
	private String vCover;
	private String vBrief;
	private String vUploader;
	private String vAuthor;
	private String vUrl;
	private String vType;
	private String vStatus;
	private Integer pageview;
	private Integer likeCount;
	private Integer dislikeCount;
	private Date createTime;
	private Date timeStamp;
	private int deleteMark;
	
	//非持久化字段
	private int comtCount;

	public Video() {
		this.deleteMark=0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getvCover() {
		return vCover;
	}

	public void setvCover(String vCover) {
		this.vCover = vCover;
	}

	public String getvBrief() {
		return vBrief;
	}

	public void setvBrief(String vBrief) {
		this.vBrief = vBrief;
	}

	public String getvUploader() {
		return vUploader;
	}

	public void setvUploader(String vUploader) {
		this.vUploader = vUploader;
	}

	public String getvAuthor() {
		return vAuthor;
	}

	public void setvAuthor(String vAuthor) {
		this.vAuthor = vAuthor;
	}

	public String getvUrl() {
		return vUrl;
	}

	public void setvUrl(String vUrl) {
		this.vUrl = vUrl;
	}

	public String getvType() {
		return vType;
	}

	public void setvType(String vType) {
		this.vType = vType;
	}

	public String getvStatus() {
		return vStatus;
	}

	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}

	public Integer getPageview() {
		return pageview;
	}

	public void setPageview(Integer pageview) {
		this.pageview = pageview;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getDislikeCount() {
		return dislikeCount;
	}

	public void setDislikeCount(Integer dislikeCount) {
		this.dislikeCount = dislikeCount;
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

	public int getComtCount() {
		return comtCount;
	}

	public void setComtCount(int comtCount) {
		this.comtCount = comtCount;
	}

}

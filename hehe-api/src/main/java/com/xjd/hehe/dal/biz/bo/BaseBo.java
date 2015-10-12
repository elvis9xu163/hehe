package com.xjd.hehe.dal.biz.bo;

import java.util.Date;

public class BaseBo {
	// 文档状态, 0为初始状态， -1表示已删除， 其它状态由各文档确定
	protected Integer status = 0;

	// 创建时间
	protected Date createdTime;

	// 修改时间
	protected Date modifiedTime;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}

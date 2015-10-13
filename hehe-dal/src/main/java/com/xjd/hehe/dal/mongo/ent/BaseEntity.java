package com.xjd.hehe.dal.mongo.ent;

import java.util.Date;

import org.mongodb.morphia.annotations.Id;

public class BaseEntity {
	@Id
	protected String id;

	/** 添加时间 */
	protected Date ctime = new Date();

	/** 更新时间 */
	protected Date utime = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

}

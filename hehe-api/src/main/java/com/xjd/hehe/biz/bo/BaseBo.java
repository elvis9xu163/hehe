package com.xjd.hehe.biz.bo;

import java.util.Date;

/**
 * @author elvis.xu
 * @since 2015-10-26 00:08
 */
public class BaseBo {
	protected String id;

	/** 添加时间 */
	protected Date ctime;

	/** 更新时间 */
	protected Date utime;

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

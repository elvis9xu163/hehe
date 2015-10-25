package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

/**
 * @author elvis.xu
 * @since 2015-10-25 23:21
 */
@Entity(value = "Api", noClassnameStored = true)
@Indexes(@Index(fields = {@Field("ver"), @Field("api")}, unique = true))
public class ApiEntity extends BaseEntity {
	/** 接口版本 */
	private Integer ver;
	/** 接口名 */
	private String api;
	/** 是否需要登录: 0-否, 1-是 */
	private Byte bLogin;
	/** 状态: 0-正常, 1-维护中, 2-不可用 */
	private Byte status;
	/** 维护时消息 */
	private String mMsg;

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Byte getbLogin() {
		return bLogin;
	}

	public void setbLogin(Byte bLogin) {
		this.bLogin = bLogin;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getmMsg() {
		return mMsg;
	}

	public void setmMsg(String mMsg) {
		this.mMsg = mMsg;
	}
}

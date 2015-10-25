package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 * @author elvis.xu
 * @since 2015-10-25 22:29
 */
@Entity(value = "ReqLog", noClassnameStored = true)
public class ReqLogEntity extends BaseEntity {
	/** 接口版本 */
	private Integer ver;
	/** 接口名 */
	private String api;
	/** 客户端IP */
	private String ip;
	/** 终端标识(IOS建议使用deviceToken,Android使用MAC地址) */
	private String endId;
	/** 终端型号(如Iphone6Plus) */
	private String endModel;
	/** 终端系统(如IOS8.1) */
	private String endSys;
	/** 应用版本(如10) */
	private Integer appVer;
	/** 用户token */
	@Indexed
	private String token;

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public String getEndModel() {
		return endModel;
	}

	public void setEndModel(String endModel) {
		this.endModel = endModel;
	}

	public String getEndSys() {
		return endSys;
	}

	public void setEndSys(String endSys) {
		this.endSys = endSys;
	}

	public Integer getAppVer() {
		return appVer;
	}

	public void setAppVer(Integer appVer) {
		this.appVer = appVer;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}

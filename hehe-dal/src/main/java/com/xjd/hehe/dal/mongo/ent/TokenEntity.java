package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 * @author elvis.xu
 * @since 2015-10-25 09:45
 */
@Entity(value = "Token", noClassnameStored = true)
public class TokenEntity extends BaseEntity {
	/** 用户ID */
	@Indexed
	private String uid;
	/** 终端标识(IOS建议使用deviceToken,Android使用手机ID) */
	@Indexed
	private String endId;
	/** 状态: 0-正常, 1-无效 */
	private Byte status = 0;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEndId() {
		return endId;
	}

	public void setEndId(String endId) {
		this.endId = endId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
}

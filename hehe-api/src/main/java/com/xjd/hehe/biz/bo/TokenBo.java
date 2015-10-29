package com.xjd.hehe.biz.bo;

/**
 * @author elvis.xu
 * @since 2015-10-26 00:08
 */
public class TokenBo extends BaseBo {
	/** 用于签名 */
	private String salt;
	/** 用户ID */
	private String uid;
	/** 终端标识 */
	private String endId;
	/** 状态 */
	private Byte status;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

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

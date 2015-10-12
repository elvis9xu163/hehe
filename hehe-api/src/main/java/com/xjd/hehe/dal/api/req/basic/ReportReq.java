package com.xjd.hehe.dal.api.req.basic;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class ReportReq extends BaseReq {
	@Range(min = 1, max = 3, message = RespCode.RES_0011)
	private Byte targetType;
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String targetId;
	@NotBlank(message = RespCode.RES_0012)
	private String content;

	public Byte getTargetType() {
		return targetType;
	}

	public void setTargetType(Byte targetType) {
		this.targetType = targetType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

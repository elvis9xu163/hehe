package com.xjd.hehe.dal.api.req.dynamic;

import org.hibernate.validator.constraints.NotBlank;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class AddCommentReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String dynamicId;

	@NotBlank(message = RespCode.RES_0012)
	private String content;

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

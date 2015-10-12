package com.xjd.hehe.dal.api.req.dynamic;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class LikeReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String dynamicId;

	@NotNull(message = RespCode.RES_0012)
	@Range(min = 0, max = 1, message = RespCode.RES_0011)
	private Byte like;

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Byte getLike() {
		return like;
	}

	public void setLike(Byte like) {
		this.like = like;
	}

}

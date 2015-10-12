package com.xjd.hehe.dal.api.req.dynamic;

import org.hibernate.validator.constraints.NotBlank;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class AddHitsReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String dynamicId;

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

}

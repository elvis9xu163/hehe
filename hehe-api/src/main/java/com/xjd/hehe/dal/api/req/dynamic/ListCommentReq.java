package com.xjd.hehe.dal.api.req.dynamic;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class ListCommentReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String dynamicId;
	private Long baseTime;
	@NotNull(message = RespCode.RES_0012)
	@Range(min = 1, max = 100, message = RespCode.RES_0013)
	private Integer count;

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Long getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(Long baseTime) {
		this.baseTime = baseTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}

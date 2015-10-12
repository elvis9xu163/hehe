package com.xjd.hehe.dal.api.req.buddy;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class ListIdolReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String buddyId;
	private Long baseTime;
	@NotNull(message = RespCode.RES_0012)
	@Range(min = 1, max = 100, message = RespCode.RES_0013)
	private Integer count;

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
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

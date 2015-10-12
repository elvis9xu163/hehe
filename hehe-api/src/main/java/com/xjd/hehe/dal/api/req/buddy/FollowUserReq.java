package com.xjd.hehe.dal.api.req.buddy;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class FollowUserReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String buddyId;

	@NotNull(message = RespCode.RES_0012)
	@Range(min = 0, max = 1, message = RespCode.RES_0011)
	private Byte follow;

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

	public Byte getFollow() {
		return follow;
	}

	public void setFollow(Byte follow) {
		this.follow = follow;
	}

}

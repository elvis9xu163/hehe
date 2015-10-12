package com.xjd.hehe.dal.api.req.buddy;

import org.apache.commons.lang3.StringUtils;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.xjd.hehe.dal.api.util.RequestContext;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;

public class GetUserInfoReq extends BaseReq {
	@ObjectId
	private String buddyId;

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

	public void validate() {
		if (StringUtils.isBlank(buddyId) && StringUtils.isBlank(RequestContext.getUserBuddyId())) {
			throw new BizException(RespCode.RES_0012, new Object[] { "buddyId" });
		}
	}
}

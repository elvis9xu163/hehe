package com.xjd.hehe.dal.api.req.dynamic;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;

public class AddDynamicReq extends BaseReq {
	private String content;
	private List<String> picUrls;
	@ObjectId
	private String circleId;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(List<String> picUrls) {
		this.picUrls = picUrls;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public void validate() {
		if (StringUtils.trimToNull(content) == null && CollectionUtils.isEmpty(picUrls)) {
			throw new BizException(RespCode.RES_0012, new Object[] { "content或picUrls" });
		}
	}
}

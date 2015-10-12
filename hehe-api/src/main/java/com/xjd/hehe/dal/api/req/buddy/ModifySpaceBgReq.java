package com.xjd.hehe.dal.api.req.buddy;

import com.xjd.hehe.dal.api.req.BaseReq;

public class ModifySpaceBgReq extends BaseReq {
	private String bgImgUrl;

	public String getBgImgUrl() {
		return bgImgUrl;
	}

	public void setBgImgUrl(String bgImgUrl) {
		this.bgImgUrl = bgImgUrl;
	}

}

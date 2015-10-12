package com.xjd.hehe.dal.api.req.buddy;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.respcode.RespCode;

public class ListMsgReq extends BaseReq {
	@NotNull(message = RespCode.RES_0012)
	@Range(min = 0, max = 2, message = RespCode.RES_0011)
	private Byte qType;
	private Long baseTime;
	@NotNull(message = RespCode.RES_0012)
	@Range(min = 1, max = 100, message = RespCode.RES_0013)
	private Integer count;

	public Byte getqType() {
		return qType;
	}

	public void setqType(Byte qType) {
		this.qType = qType;
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

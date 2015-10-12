package com.xjd.hehe.dal.api.view.vo;

import com.xjd.hehe.dal.entity.Message.Arg;

public class MessageVo extends BaseVo {
	private String msgId;
	private Integer type;
	private BuddyVo creator;
	private String content;
	private DynamicVo dynamic;
	private Arg arg;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BuddyVo getCreator() {
		return creator;
	}

	public void setCreator(BuddyVo creator) {
		this.creator = creator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public DynamicVo getDynamic() {
		return dynamic;
	}

	public void setDynamic(DynamicVo dynamic) {
		this.dynamic = dynamic;
	}

	public Arg getArg() {
		return arg;
	}

	public void setArg(Arg arg) {
		this.arg = arg;
	}

}

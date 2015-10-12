package com.xjd.hehe.dal.biz.bo;

import com.xjd.hehe.dal.entity.Message.Arg;

public class MessageBo extends BaseBo {
	private String id;

	private Integer type;

	private String creatorId;

	private BuddyBo creator;

	private String receiverId;

	private Integer targetType;

	private String targetId;

	private Object target;

	private String content;

	private Arg arg;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public Integer getTargetType() {
		return targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BuddyBo getCreator() {
		return creator;
	}

	public void setCreator(BuddyBo creator) {
		this.creator = creator;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public Arg getArg() {
		return arg;
	}

	public void setArg(Arg arg) {
		this.arg = arg;
	}

}

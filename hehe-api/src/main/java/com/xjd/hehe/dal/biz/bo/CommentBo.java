package com.xjd.hehe.dal.biz.bo;

import java.util.List;

public class CommentBo extends BaseBo {
	private String id;
	private String content;
	private String dynamicId;
	private String ownerId;
	// 根据ownerId查询而来
	private BuddyBo owner;
	private List<ReplyBo> replyList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public BuddyBo getOwner() {
		return owner;
	}

	public void setOwner(BuddyBo owner) {
		this.owner = owner;
	}

	public List<ReplyBo> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyBo> replyList) {
		this.replyList = replyList;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}

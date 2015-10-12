package com.xjd.hehe.dal.api.view.vo;

import java.util.List;

public class CommentVo extends BaseVo {
	private String commentId;
	private String content;
	private String dynamicId;
	private BuddyVo owner;
	private List<ReplyVo> replyList;

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
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

	public BuddyVo getOwner() {
		return owner;
	}

	public void setOwner(BuddyVo owner) {
		this.owner = owner;
	}

	public List<ReplyVo> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyVo> replyList) {
		this.replyList = replyList;
	}

}

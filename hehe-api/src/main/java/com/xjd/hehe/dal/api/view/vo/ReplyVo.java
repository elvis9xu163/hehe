package com.xjd.hehe.dal.api.view.vo;

public class ReplyVo extends BaseVo {
	private String replyId;
	private String content;
	private String commentId;
	private BuddyVo targetBuddy;
	private BuddyVo owner;

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public BuddyVo getTargetBuddy() {
		return targetBuddy;
	}

	public void setTargetBuddy(BuddyVo targetBuddy) {
		this.targetBuddy = targetBuddy;
	}

	public BuddyVo getOwner() {
		return owner;
	}

	public void setOwner(BuddyVo owner) {
		this.owner = owner;
	}

}

package com.xjd.hehe.dal.biz.bo;

public class ReplyBo extends BaseBo {
	private String id;
	private String content;
	private String commentId;
	private String targetBuddyId;
	// 根据targetBuddyId查询而来
	private BuddyBo targetBuddy;
	private String ownerId;
	// 根据ownerId查询而来
	private BuddyBo owner;

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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public BuddyBo getOwner() {
		return owner;
	}

	public void setOwner(BuddyBo owner) {
		this.owner = owner;
	}

	public String getTargetBuddyId() {
		return targetBuddyId;
	}

	public void setTargetBuddyId(String targetBuddyId) {
		this.targetBuddyId = targetBuddyId;
	}

	public BuddyBo getTargetBuddy() {
		return targetBuddy;
	}

	public void setTargetBuddy(BuddyBo targetBuddy) {
		this.targetBuddy = targetBuddy;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}

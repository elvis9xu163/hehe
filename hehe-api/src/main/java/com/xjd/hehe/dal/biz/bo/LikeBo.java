package com.xjd.hehe.dal.biz.bo;

public class LikeBo extends BaseBo {
	private String likeId;
	private String targetId;
	private String ownerId;
	// 根据ownerId查询而来
	private BuddyBo owner;

	public String getLikeId() {
		return likeId;
	}

	public void setLikeId(String likeId) {
		this.likeId = likeId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public BuddyBo getOwner() {
		return owner;
	}

	public void setOwner(BuddyBo owner) {
		this.owner = owner;
	}

}

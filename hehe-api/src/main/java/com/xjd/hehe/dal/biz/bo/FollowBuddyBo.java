package com.xjd.hehe.dal.biz.bo;

public class FollowBuddyBo extends BaseBo {
	private String buddyId;

	// 根据buddyId查询而来
	private BuddyBo buddy;

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

	public BuddyBo getBuddy() {
		return buddy;
	}

	public void setBuddy(BuddyBo buddy) {
		this.buddy = buddy;
	}

}

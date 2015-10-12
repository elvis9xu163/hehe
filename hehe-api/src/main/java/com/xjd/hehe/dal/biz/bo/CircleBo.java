package com.xjd.hehe.dal.biz.bo;

import java.util.List;

public class CircleBo extends BaseBo {
	private String id;
	private Integer type;
	private String title;
	private String summary;
	private String categoryId;
	// 根据categoryId查询而来
	private CircleCategoryBo category;
	private String ownerId;
	// 根据ownerId查询而来
	private BuddyBo owner;
	private List<FollowBuddyBo> followBuddyList;
	private Integer viewCount;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<FollowBuddyBo> getFollowBuddyList() {
		return followBuddyList;
	}

	public void setFollowBuddyList(List<FollowBuddyBo> followBuddyList) {
		this.followBuddyList = followBuddyList;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public BuddyBo getOwner() {
		return owner;
	}

	public void setOwner(BuddyBo owner) {
		this.owner = owner;
	}

	public CircleCategoryBo getCategory() {
		return category;
	}

	public void setCategory(CircleCategoryBo category) {
		this.category = category;
	}

}

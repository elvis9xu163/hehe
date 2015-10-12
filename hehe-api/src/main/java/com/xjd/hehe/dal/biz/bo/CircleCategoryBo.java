package com.xjd.hehe.dal.biz.bo;

public class CircleCategoryBo extends BaseBo {
	private String id;
	private String name;
	private String description;
	private String creatorId;
	private BuddyBo creator;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public BuddyBo getCreator() {
		return creator;
	}

	public void setCreator(BuddyBo creator) {
		this.creator = creator;
	}

}

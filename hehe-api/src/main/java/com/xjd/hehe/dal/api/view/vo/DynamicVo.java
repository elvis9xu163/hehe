package com.xjd.hehe.dal.api.view.vo;

import java.util.List;

import com.xjd.bpg.annotation.GuardProp;

//@JsonInclude(value = Include.NON_NULL)
public class DynamicVo extends BaseVo {
	private String dynamicId;
	private BuddyVo owner;
	private String content;
	private List<String> images;
	private Integer viewCount;
	private Integer likeCount;
	private List<BuddyVo> likerList;
	private Integer commentCount;
	private CircleVo circle;
	private Byte likeFlag;

	@GuardProp("listDynamicForRecommend")
	private Long recTime;

	public BuddyVo getOwner() {
		return owner;
	}

	public void setOwner(BuddyVo owner) {
		this.owner = owner;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public List<BuddyVo> getLikerList() {
		return likerList;
	}

	public void setLikerList(List<BuddyVo> likerList) {
		this.likerList = likerList;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public CircleVo getCircle() {
		return circle;
	}

	public void setCircle(CircleVo circle) {
		this.circle = circle;
	}

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public Byte getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(Byte likeFlag) {
		this.likeFlag = likeFlag;
	}

	public Long getRecTime() {
		return recTime;
	}

	public void setRecTime(Long recTime) {
		this.recTime = recTime;
	}

}

package com.xjd.hehe.dal.biz.bo;

import java.util.Date;
import java.util.List;

public class DynamicBo extends BaseBo {
	private String dynamicId;
	private String ownerId;
	// 根据ownerId查询而来
	private BuddyBo owner;
	private String circleId;
	// 根据circleId查询而来
	private CircleBo circle;
	private String content;
	private List<String> images;
	private Integer viewCount;
	private Integer likeCount;
	private List<LikeBo> likeList;
	private Integer commentCount;
	private List<CommentBo> commentList;
	private Byte recommendFlag;
	private String recBuddyId;
	private Date recTime;
	private Integer recLevel;
	// 当前用户是否点赞，由业务判断而来（根据Entity的likeList判断而来）
	private Byte likeFlag;

	public BuddyBo getOwner() {
		return owner;
	}

	public void setOwner(BuddyBo owner) {
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

	public List<LikeBo> getLikeList() {
		return likeList;
	}

	public void setLikeList(List<LikeBo> likeList) {
		this.likeList = likeList;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public CircleBo getCircle() {
		return circle;
	}

	public void setCircle(CircleBo circle) {
		this.circle = circle;
	}

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public List<CommentBo> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentBo> commentList) {
		this.commentList = commentList;
	}

	public Byte getRecommendFlag() {
		return recommendFlag;
	}

	public void setRecommendFlag(Byte recommendFlag) {
		this.recommendFlag = recommendFlag;
	}

	public String getRecBuddyId() {
		return recBuddyId;
	}

	public void setRecBuddyId(String recBuddyId) {
		this.recBuddyId = recBuddyId;
	}

	public Date getRecTime() {
		return recTime;
	}

	public void setRecTime(Date recTime) {
		this.recTime = recTime;
	}

	public Integer getRecLevel() {
		return recLevel;
	}

	public void setRecLevel(Integer recLevel) {
		this.recLevel = recLevel;
	}

	public Byte getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(Byte likeFlag) {
		this.likeFlag = likeFlag;
	}

}

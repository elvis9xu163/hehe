package com.xjd.hehe.dal.biz.bo;

import java.util.List;

/**
 * @author elvis.xu
 * @since 2015-9-1
 */
public class BuddyBo extends BaseBo {
	// ==mysql
	private Byte userType;
	private String imgUrl;
	private String userName;
	// patient
	private Byte diabetesType;
	private String diabetesTypeName;
	private Long diabetesTime;

	// doctor
	private Integer hospitalId;
	private String hospital;
	private String title;

	// ==mongo
	private String buddyId;
	private Integer userId;
	private String signature;
	private Byte vFlag;

	private List<FollowCircleBo> followCircleList;
	private List<FollowBuddyBo> followingList;
	private List<FollowBuddyBo> followerList;

	private Byte idolFlag;
	private Integer idolCount;
	private Integer fansCount;
	private Long msgCount;
	private String bgImgUrl;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Byte getDiabetesType() {
		return diabetesType;
	}

	public void setDiabetesType(Byte diabetesType) {
		this.diabetesType = diabetesType;
	}

	public Long getDiabetesTime() {
		return diabetesTime;
	}

	public void setDiabetesTime(Long diabetesTime) {
		this.diabetesTime = diabetesTime;
	}

	public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Byte getvFlag() {
		return vFlag;
	}

	public void setvFlag(Byte vFlag) {
		this.vFlag = vFlag;
	}

	public String getBuddyId() {
		return buddyId;
	}

	public void setBuddyId(String buddyId) {
		this.buddyId = buddyId;
	}

	public List<FollowCircleBo> getFollowCircleList() {
		return followCircleList;
	}

	public void setFollowCircleList(List<FollowCircleBo> followCircleList) {
		this.followCircleList = followCircleList;
	}

	public List<FollowBuddyBo> getFollowingList() {
		return followingList;
	}

	public void setFollowingList(List<FollowBuddyBo> followingList) {
		this.followingList = followingList;
	}

	public List<FollowBuddyBo> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(List<FollowBuddyBo> followerList) {
		this.followerList = followerList;
	}

	public Byte getIdolFlag() {
		return idolFlag;
	}

	public void setIdolFlag(Byte idolFlag) {
		this.idolFlag = idolFlag;
	}

	public Integer getIdolCount() {
		return idolCount;
	}

	public void setIdolCount(Integer idolCount) {
		this.idolCount = idolCount;
	}

	public Integer getFansCount() {
		return fansCount;
	}

	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}

	public Long getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Long msgCount) {
		this.msgCount = msgCount;
	}

	public String getBgImgUrl() {
		return bgImgUrl;
	}

	public void setBgImgUrl(String bgImgUrl) {
		this.bgImgUrl = bgImgUrl;
	}

	public String getDiabetesTypeName() {
		return diabetesTypeName;
	}

	public void setDiabetesTypeName(String diabetesTypeName) {
		this.diabetesTypeName = diabetesTypeName;
	}

}

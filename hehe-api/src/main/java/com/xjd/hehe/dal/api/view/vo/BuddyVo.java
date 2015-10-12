package com.xjd.hehe.dal.api.view.vo;

import com.xjd.bpg.annotation.GuardProp;

/**
 * <pre>
 * 
 * </pre>
 * @author elvis.xu
 * @since 2015-9-1
 */
// @JsonInclude(value = Include.NON_NULL)
public class BuddyVo extends BaseVo {
	private Integer userId;
	private String buddyId;
	private Byte userType;
	private String imgUrl;
	private String userName;
	private String signature;
	private Byte vFlag;

	// patient
	@GuardProp({ "getUserInfo", "listExpPatient" })
	private Byte diabetesType;
	@GuardProp({ "getUserInfo", "listExpPatient" })
	private String diabetesTypeName;
	@GuardProp({ "getUserInfo", "listExpPatient" })
	private Long diabetesTime;

	// doctor
	@GuardProp({ "getUserInfo", "listExpDoctor" })
	private Integer hospitalId;
	@GuardProp({ "getUserInfo", "listExpDoctor" })
	private String hospital;
	@GuardProp({ "getUserInfo", "listExpDoctor" })
	private String title;

	// circle
	@GuardProp({ "listExpPatient", "listExpDoctor" })
	private Long enrollTime;
	@GuardProp({ "listLiker" })
	private Long likeTime;
	// 关注/被关注时间
	@GuardProp({ "listIdol", "listFans" })
	private Long followTime;
	//
	@GuardProp({ "getUserInfo", "listIdol", "listFans" })
	private Byte idolFlag;
	@GuardProp({ "getUserInfo" })
	private Integer idolCount;
	@GuardProp({ "getUserInfo" })
	private Integer fansCount;
	@GuardProp({ "getUserInfo" })
	private Long msgCount;
	@GuardProp({ "getUserInfo" })
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

	public Long getEnrollTime() {
		return enrollTime;
	}

	public void setEnrollTime(Long enrollTime) {
		this.enrollTime = enrollTime;
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

	public Byte getIdolFlag() {
		return idolFlag;
	}

	public void setIdolFlag(Byte idolFlag) {
		this.idolFlag = idolFlag;
	}

	public Long getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Long likeTime) {
		this.likeTime = likeTime;
	}

	public Long getFollowTime() {
		return followTime;
	}

	public void setFollowTime(Long followTime) {
		this.followTime = followTime;
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

package com.xjd.hehe.api.view.vo;

import com.xjd.bpg.annotation.GuardProp;

/**
 * @author elvis.xu
 * @since 2015-10-31 14:48
 */
public class UserVo {
	/** ID */
	private String id;

	/** 名称 */
	private String name;

	/** 头像URL */
	private String avatar;

	@GuardProp("getUser")
	/** 手机号 */
	private String mobile;

	@GuardProp("getUser")
	/** 邮箱 */
	private String mail;

	@GuardProp("getUser")
	/** 等级 */
	private Integer level = 0;

	@GuardProp("getUser")
	/** 经验值 */
	private Integer exp = 0;

	@GuardProp("getUser")
	/** 积分 */
	private Integer score = 0;

	@GuardProp("getUser")
	/** 发布的笑话数 */
	private Integer njoke = 0;

	@GuardProp("getUser")
	/** 发布的评论数 */
	private Integer ncmt = 0;

	@GuardProp("getUser")
	/** 收藏数 */
	private Integer nfavor = 0;

	@GuardProp("getUser")
	/** 关注话题数 */
	private Integer ntopic = 0;

	@GuardProp("getUser")
	/** 用户类型: 0-正常, 1-游客, 2-虚假 */
	private Byte type = 0;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getNjoke() {
		return njoke;
	}

	public void setNjoke(Integer njoke) {
		this.njoke = njoke;
	}

	public Integer getNcmt() {
		return ncmt;
	}

	public void setNcmt(Integer ncmt) {
		this.ncmt = ncmt;
	}

	public Integer getNfavor() {
		return nfavor;
	}

	public void setNfavor(Integer nfavor) {
		this.nfavor = nfavor;
	}

	public Integer getNtopic() {
		return ntopic;
	}

	public void setNtopic(Integer ntopic) {
		this.ntopic = ntopic;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

}

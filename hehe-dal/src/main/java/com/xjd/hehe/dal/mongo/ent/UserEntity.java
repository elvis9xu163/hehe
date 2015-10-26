package com.xjd.hehe.dal.mongo.ent;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "User", noClassnameStored = true)
public class UserEntity extends BaseEntity {
	/** 名称 */
	private String name;

	/** 手机号 */
	private String mobile;

	/** 邮箱 */
	private String mail;

	/** 密码 */
	private String pwd;

	/** 等级 */
	private Integer level = 0;

	/** 经验值 */
	private Integer exp = 0;

	/** 积分 */
	private Integer score = 0;

	/** 头像URL */
	private String avatar;

	/** 发布的笑话数 */
	private Integer njoke = 0;

	/** 发布的评论数 */
	private Integer ncmt = 0;

	/** 收藏数 */
	private Integer nfavor = 0;

	/** 关注话题数 */
	private Integer ntopic = 0;

	/** 收藏的joke列表 */
	private List<String> favors;

	/** 关注的topic列表 */
	private List<String> topics;

	/** 用户类型: 0-正常, 1-游客, 2-虚假 */
	private Byte type = 0;

	/** 用户类型修改时间 */
	private Date typeTime;

	/** 用户状态: 0-正常, 1-无效 */
	private Byte status = 0;

	/** 关联的系统外用户 */
	private List<Ref> refs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<String> getFavors() {
		return favors;
	}

	public void setFavors(List<String> favors) {
		this.favors = favors;
	}

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public List<Ref> getRefs() {
		return refs;
	}

	public void setRefs(List<Ref> refs) {
		this.refs = refs;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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

	public Date getTypeTime() {
		return typeTime;
	}

	public void setTypeTime(Date typeTime) {
		this.typeTime = typeTime;
	}

	public Integer getNtopic() {
		return ntopic;
	}

	public void setNtopic(Integer ntopic) {
		this.ntopic = ntopic;
	}

	public static class Ref {
		/** id */
		private String id;

		/** name */
		private String name;

		/** 头像 */
		private String avatar;

		/** 来源平台：0-自身, 10-哈哈 */
		private Byte from;

		/** 添加时间 */
		private Date ctime;

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

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public Byte getFrom() {
			return from;
		}

		public void setFrom(Byte from) {
			this.from = from;
		}

		public Date getCtime() {
			return ctime;
		}

		public void setCtime(Date ctime) {
			this.ctime = ctime;
		}

	}
}

package com.xjd.hehe.dal.mongo.ent;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "User", noClassnameStored = true)
public class UserEntity extends BaseEntity {
	/** 名称 */
	private String name;

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

	/** 收藏的joke列表 */
	private List<String> favors;

	/** 关注的topic列表 */
	private List<String> topics;

	/** 是否是虚假用户: 0-否, 1-是 */
	private Byte fake = 0;

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

	public Byte getFake() {
		return fake;
	}

	public void setFake(Byte fake) {
		this.fake = fake;
	}

	public List<Ref> getRefs() {
		return refs;
	}

	public void setRefs(List<Ref> refs) {
		this.refs = refs;
	}

	public static class Ref {
		/** id */
		private String id;

		/** name */
		private String name;

		/** 头像 */
		private String avatar;

		/** 来源平台：0-自身, 10-哈哈 */
		private String from;

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

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
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

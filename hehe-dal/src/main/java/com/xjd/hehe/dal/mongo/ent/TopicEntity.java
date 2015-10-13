package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "Topic", noClassnameStored = true)
public class TopicEntity extends BaseEntity {
	/** 创建用户ID */
	private String uid;

	/** 名 */
	private String name;

	/** 关联的joke数 */
	private Integer njoke = 0;

	/** 关注的用户数 */
	private Integer nuser = 0;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNjoke() {
		return njoke;
	}

	public void setNjoke(Integer njoke) {
		this.njoke = njoke;
	}

	public Integer getNuser() {
		return nuser;
	}

	public void setNuser(Integer nuser) {
		this.nuser = nuser;
	}
}

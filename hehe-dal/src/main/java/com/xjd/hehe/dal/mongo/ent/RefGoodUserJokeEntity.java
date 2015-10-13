package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "RefGoodUserJoke", noClassnameStored = true)
public class RefGoodUserJokeEntity extends BaseEntity {
	/** user id */
	private String uid;

	/** joke id */
	private String jid;

	/** good or bad: 0-bad, 1-good */
	private Byte gb;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public Byte getGb() {
		return gb;
	}

	public void setGb(Byte gb) {
		this.gb = gb;
	}

}

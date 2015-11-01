package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "RefGoodUser", noClassnameStored = true)
public class RefGoodUserEntity extends BaseEntity {
	/** user id */
	private String uid;

	/** object id */
	private String oid;

	/** object type */
	private String otype;

	/** good or bad: 0-bad, 1-good */
	private Byte gb;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOtype() {
		return otype;
	}

	public void setOtype(String otype) {
		this.otype = otype;
	}

	public Byte getGb() {
		return gb;
	}

	public void setGb(Byte gb) {
		this.gb = gb;
	}
}

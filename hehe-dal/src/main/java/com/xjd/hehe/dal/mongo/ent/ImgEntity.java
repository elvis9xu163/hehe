package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "Img", noClassnameStored = true)
public class ImgEntity extends BaseEntity {
	/** 本系统中的URI */
	private String uri;

	/** 原图高 */
	private Integer h;

	/** 原图宽 */
	private Integer w;

	/** 来源平台：0-自身, 10-哈哈 */
	private Byte from = 0;

	/** 对应其它网站的url */
	private String refUrl;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	public Integer getW() {
		return w;
	}

	public void setW(Integer w) {
		this.w = w;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	public Byte getFrom() {
		return from;
	}

	public void setFrom(Byte from) {
		this.from = from;
	}
}

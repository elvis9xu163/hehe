package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

@Entity(value = "Img", noClassnameStored = true)
public class ImgEntity extends BaseEntity {
	/** 本系统中的URI */
	@Indexed
	private String uri;

	/** 原图高 */
	private Integer h;

	/** 原图宽 */
	private Integer w;

//	FIXME @Indexed(unique = true, dropDups = true)
	@Indexed
	/** 图片文件的MD5值 */
	private String md5;

	/** 图片大小 */
	private Long size;

	/** 图片业务(多个之间用逗号分隔): [JOKE],[AVATAR] */
	private String biz;

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

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}
}

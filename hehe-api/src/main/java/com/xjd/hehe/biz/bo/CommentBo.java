package com.xjd.hehe.biz.bo;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "Comment", noClassnameStored = true)
public class CommentBo extends BaseBo {
	/** 用户ID */
	private String uid;

	/** 关联的jokeId */
	private String jid;

	/** 回复的commentId */
	private String cid;

	/** 文字内容 */
	private String txt;

	/** 点赞数 */
	private Integer ngood = 0;

	/** 鄙视数 */
	private Integer nbad = 0;

	/** 来源平台：0-自身, 10-哈哈 */
	private Byte from = 0;

	/** 状态: 0-待审核, 1-审核通过, 2-审核拒绝 */
	private Byte status = 0;


	// ==== 关联
	private UserBo user;
	private CommentBo pcmt;

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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Integer getNgood() {
		return ngood;
	}

	public void setNgood(Integer ngood) {
		this.ngood = ngood;
	}

	public Integer getNbad() {
		return nbad;
	}

	public void setNbad(Integer nbad) {
		this.nbad = nbad;
	}

	public Byte getFrom() {
		return from;
	}

	public void setFrom(Byte from) {
		this.from = from;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public UserBo getUser() {
		return user;
	}

	public void setUser(UserBo user) {
		this.user = user;
	}

	public CommentBo getPcmt() {
		return pcmt;
	}

	public void setPcmt(CommentBo pcmt) {
		this.pcmt = pcmt;
	}
}

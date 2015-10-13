package com.xjd.hehe.dal.mongo.ent;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "Comment", noClassnameStored = true)
public class CommentEntity extends BaseEntity {
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

	/** 回复数 */
	private Integer ncmt = 0;

	/** 来源平台：0-自身, 10-哈哈 */
	private Byte from = 0;

	/** 状态: 0-待审核, 1-审核通过, 2-审核拒绝 */
	private Byte status = 0;

	/** 来源平台的关联属性 */
	private Ref ref;

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

	public Integer getNcmt() {
		return ncmt;
	}

	public void setNcmt(Integer ncmt) {
		this.ncmt = ncmt;
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

	public Ref getRef() {
		return ref;
	}

	public void setRef(Ref ref) {
		this.ref = ref;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public static class Ref {
		/** ID */
		private String id;
		/** UID */
		private String uid;
		/** 点赞数 */
		private Integer good = 0;
		/** 鄙视数 */
		private Integer bad = 0;
		/** 评论数 */
		private Integer cmt = 0;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public Integer getGood() {
			return good;
		}

		public void setGood(Integer good) {
			this.good = good;
		}

		public Integer getBad() {
			return bad;
		}

		public void setBad(Integer bad) {
			this.bad = bad;
		}

		public Integer getCmt() {
			return cmt;
		}

		public void setCmt(Integer cmt) {
			this.cmt = cmt;
		}

	}
}

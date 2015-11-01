package com.xjd.hehe.api.view.vo;

import org.mongodb.morphia.annotations.Entity;

import com.xjd.hehe.biz.bo.BaseBo;

@Entity(value = "Comment", noClassnameStored = true)
public class CommentVo extends BaseBo {
	/** ID */
	private String id;

	/** 文字内容 */
	private String txt;

	/** 点赞数 */
	private Integer ngood = 0;

	/** 鄙视数 */
	private Integer nbad = 0;

	// ==== 关联
	private UserVo user;
	private CommentVo pcmt;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
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

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	public CommentVo getPcmt() {
		return pcmt;
	}

	public void setPcmt(CommentVo pcmt) {
		this.pcmt = pcmt;
	}
}

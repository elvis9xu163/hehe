package com.xjd.hehe.api.view.vo;

import java.util.List;

public class JokeVo {
	/** ID */
	private String id;

	/** 内容类型: 0-仅文字, 1-仅图片, 2-图片+文字, 3-仅语音, 4-文字+语音 */
	private Byte ctype;

	/** 文字内容 */
	private String txt;

	/** 图片列表 */
	private List<String> pics;

	/** 点赞数 */
	private Integer ngood = 0;

	/** 鄙视数 */
	private Integer nbad = 0;

	/** 评论数 */
	private Integer ncmt = 0;

	/** 状态: 0-待审核, 1-审核通过, 2-审核拒绝, 3-待发布 */
	private Byte status = 0;

	private UserVo user;
	private List<TopicVo> topics;
	private JokeVo pjoke;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Byte getCtype() {
		return ctype;
	}

	public void setCtype(Byte ctype) {
		this.ctype = ctype;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public List<String> getPics() {
		return pics;
	}

	public void setPics(List<String> pics) {
		this.pics = pics;
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

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}

	public List<TopicVo> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicVo> topics) {
		this.topics = topics;
	}

	public JokeVo getPjoke() {
		return pjoke;
	}

	public void setPjoke(JokeVo pjoke) {
		this.pjoke = pjoke;
	}
}

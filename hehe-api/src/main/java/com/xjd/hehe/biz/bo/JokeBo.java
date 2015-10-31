package com.xjd.hehe.biz.bo;

import java.util.List;

public class JokeBo extends BaseBo {
	/** 用户ID */
	private String uid;

	/** 内容类型: 0-仅文字, 1-仅图片, 2-图片+文字, 3-仅语音, 4-文字+语音 */
	private Byte ctype;

	/** 文字内容 */
	private String txt;

	/** 图片列表 */
	private List<String> pics;

	/** topic列表 */
	private List<String> topics;

	/** 点赞数 */
	private Integer ngood = 0;

	/** 鄙视数 */
	private Integer nbad = 0;

	/** 评论数 */
	private Integer ncmt = 0;

	/** 父joke id */
	private String pjid;

	/** 来源平台：0-自身, 10-哈哈 */
	private Byte from = 0;

	/** 状态: 0-待审核, 1-审核通过, 2-审核拒绝, 3-待发布 */
	private Byte status = 0;

	// === biz
	private UserBo user;
	private List<TopicBo> topicList;
	private JokeBo pjoke;


	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	public String getPjid() {
		return pjid;
	}

	public void setPjid(String pjid) {
		this.pjid = pjid;
	}

	public UserBo getUser() {
		return user;
	}

	public void setUser(UserBo user) {
		this.user = user;
	}

	public List<TopicBo> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<TopicBo> topicList) {
		this.topicList = topicList;
	}

	public JokeBo getPjoke() {
		return pjoke;
	}

	public void setPjoke(JokeBo pjoke) {
		this.pjoke = pjoke;
	}
}

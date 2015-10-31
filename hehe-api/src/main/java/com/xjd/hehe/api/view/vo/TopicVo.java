package com.xjd.hehe.api.view.vo;

public class TopicVo {
	/** ID */
	private String id;

	/** 名 */
	private String name;

	/** 关联的joke数 */
	private Integer njoke = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}

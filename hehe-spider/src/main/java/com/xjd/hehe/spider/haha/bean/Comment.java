package com.xjd.hehe.spider.haha.bean;

/**
 * @author elvis.xu
 * @since 2015-10-14 00:41
 */
public class Comment {
	private Long id;
	private String content;
	private Long user_id;
	private String user_name;
	private String user_pic;
	private String time;
	private Integer light;
	private Integer able;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_pic() {
		return user_pic;
	}

	public void setUser_pic(String user_pic) {
		this.user_pic = user_pic;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getLight() {
		return light;
	}

	public void setLight(Integer light) {
		this.light = light;
	}

	public Integer getAble() {
		return able;
	}

	public void setAble(Integer able) {
		this.able = able;
	}
}

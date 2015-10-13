package com.xjd.hehe.spider.haha.bean;

import java.util.List;

/**
 * @author elvis.xu
 * @since 2015-10-14 00:46
 */
public class CommentWithPage {
	private Integer count;
	private List<List<Comment>> comments;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<List<Comment>> getComments() {
		return comments;
	}

	public void setComments(List<List<Comment>> comments) {
		this.comments = comments;
	}
}

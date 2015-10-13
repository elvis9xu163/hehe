package com.xjd.hehe.spider.haha.bean;

import java.util.List;

/**
 * @author elvis.xu
 * @since 2015-10-13 21:25
 */
public class JokeWithPage {
	private Object page; // may be String or Integer
	private List<Joke> joke;

	public Object getPage() {
		return page;
	}

	public void setPage(Object page) {
		this.page = page;
	}

	public List<Joke> getJoke() {
		return joke;
	}

	public void setJoke(List<Joke> joke) {
		this.joke = joke;
	}
}

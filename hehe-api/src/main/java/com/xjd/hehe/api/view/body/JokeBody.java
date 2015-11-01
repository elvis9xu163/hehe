package com.xjd.hehe.api.view.body;

import com.xjd.hehe.api.view.ViewBody;
import com.xjd.hehe.api.view.vo.JokeVo;

/**
 * @author elvis.xu
 * @since 2015-10-31 19:03
 */
public class JokeBody extends ViewBody {
	private JokeVo joke;

	public JokeVo getJoke() {
		return joke;
	}

	public void setJoke(JokeVo joke) {
		this.joke = joke;
	}
}

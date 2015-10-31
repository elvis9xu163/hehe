package com.xjd.hehe.api.view.body;

import java.util.List;

import com.xjd.hehe.api.view.ViewBody;
import com.xjd.hehe.api.view.vo.JokeVo;

/**
 * @author elvis.xu
 * @since 2015-10-31 19:03
 */
public class JokeListBody extends ViewBody {
	private String pcon;
	private List<JokeVo> jokes;

	public String getPcon() {
		return pcon;
	}

	public void setPcon(String pcon) {
		this.pcon = pcon;
	}

	public List<JokeVo> getJokes() {
		return jokes;
	}

	public void setJokes(List<JokeVo> jokes) {
		this.jokes = jokes;
	}
}

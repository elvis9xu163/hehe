package com.xjd.hehe.api.view.body;

import com.xjd.hehe.api.view.ViewBody;

/**
 * @author elvis.xu
 * @since 2015-10-29 21:30
 */
public class TokenBody extends ViewBody {
	private String token;
	private String salt;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}

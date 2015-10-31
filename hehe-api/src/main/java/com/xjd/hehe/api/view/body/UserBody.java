package com.xjd.hehe.api.view.body;

import com.xjd.hehe.api.view.ViewBody;
import com.xjd.hehe.api.view.vo.UserVo;

/**
 * @author elvis.xu
 * @since 2015-10-31 19:02
 */
public class UserBody extends ViewBody {
	private UserVo user;

	public UserVo getUser() {
		return user;
	}

	public void setUser(UserVo user) {
		this.user = user;
	}
}

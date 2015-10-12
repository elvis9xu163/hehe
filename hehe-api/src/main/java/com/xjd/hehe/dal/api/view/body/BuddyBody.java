package com.xjd.hehe.dal.api.view.body;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.BuddyVo;

public class BuddyBody extends ViewBody {
	private BuddyVo buddy;

	public BuddyVo getBuddy() {
		return buddy;
	}

	public void setBuddy(BuddyVo buddy) {
		this.buddy = buddy;
	}

}

package com.xjd.hehe.dal.api.view.body;

import java.util.List;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.BuddyVo;

public class BuddyListBody extends ViewBody {
	private List<BuddyVo> buddyList;

	public List<BuddyVo> getBuddyList() {
		return buddyList;
	}

	public void setBuddyList(List<BuddyVo> buddyList) {
		this.buddyList = buddyList;
	}

}

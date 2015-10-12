package com.xjd.hehe.dal.api.view.body;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.DynamicVo;

public class DynamicBody extends ViewBody {
	private DynamicVo dynamic;

	public DynamicVo getDynamic() {
		return dynamic;
	}

	public void setDynamic(DynamicVo dynamic) {
		this.dynamic = dynamic;
	}

}

package com.xjd.hehe.dal.api.view.body;

import java.util.List;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.DynamicVo;

public class DynamicListBody extends ViewBody {
	private List<DynamicVo> dynamicList;

	public List<DynamicVo> getDynamicList() {
		return dynamicList;
	}

	public void setDynamicList(List<DynamicVo> dynamicList) {
		this.dynamicList = dynamicList;
	}

}

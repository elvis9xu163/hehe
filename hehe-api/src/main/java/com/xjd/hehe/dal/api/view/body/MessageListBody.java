package com.xjd.hehe.dal.api.view.body;

import java.util.List;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.MessageVo;

public class MessageListBody extends ViewBody {
	private List<MessageVo> msgList;

	public List<MessageVo> getMsgList() {
		return msgList;
	}

	public void setMsgList(List<MessageVo> msgList) {
		this.msgList = msgList;
	}

}

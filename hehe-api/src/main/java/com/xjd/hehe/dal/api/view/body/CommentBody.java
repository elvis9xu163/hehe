package com.xjd.hehe.dal.api.view.body;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.CommentVo;

public class CommentBody extends ViewBody {
	private CommentVo comment;

	public CommentVo getComment() {
		return comment;
	}

	public void setComment(CommentVo comment) {
		this.comment = comment;
	}

}

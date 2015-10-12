package com.xjd.hehe.dal.api.view.body;

import java.util.List;

import com.xjd.hehe.dal.api.view.ViewBody;
import com.xjd.hehe.dal.api.view.vo.CommentVo;

public class CommentListBody extends ViewBody {
	private List<CommentVo> commentList;

	public List<CommentVo> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentVo> commentList) {
		this.commentList = commentList;
	}

}

package com.xjd.hehe.dal.api.req.dynamic;

import org.hibernate.validator.constraints.NotBlank;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.jkys.social.util.constraint.ObjectId;
import com.jkys.social.util.respcode.RespCode;

public class RemoveReplyReq extends BaseReq {
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String dynamicId;
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String commentId;
	@NotBlank(message = RespCode.RES_0012)
	@ObjectId
	private String replyId;

	public String getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(String dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

}

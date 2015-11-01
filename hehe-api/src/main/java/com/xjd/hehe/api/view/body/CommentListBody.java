package com.xjd.hehe.api.view.body;

import java.util.List;

import com.xjd.hehe.api.view.ViewBody;
import com.xjd.hehe.api.view.vo.CommentVo;

/**
 * @author elvis.xu
 * @since 2015-10-31 19:03
 */
public class CommentListBody extends ViewBody {
	private String pcon;
	private List<CommentVo> hotCmts;
	private List<CommentVo> cmts;

	public String getPcon() {
		return pcon;
	}

	public void setPcon(String pcon) {
		this.pcon = pcon;
	}

	public List<CommentVo> getHotCmts() {
		return hotCmts;
	}

	public void setHotCmts(List<CommentVo> hotCmts) {
		this.hotCmts = hotCmts;
	}

	public List<CommentVo> getCmts() {
		return cmts;
	}

	public void setCmts(List<CommentVo> cmts) {
		this.cmts = cmts;
	}
}

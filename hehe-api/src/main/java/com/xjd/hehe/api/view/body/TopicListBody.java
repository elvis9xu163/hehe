package com.xjd.hehe.api.view.body;

import java.util.List;

import com.xjd.bpg.annotation.GuardProp;
import com.xjd.hehe.api.view.ViewBody;
import com.xjd.hehe.api.view.vo.TopicVo;

/**
 * @author elvis.xu
 * @since 2015-10-31 19:03
 */
public class TopicListBody extends ViewBody {
	@GuardProp("listTopic")
	private String pcon;
	@GuardProp("listTopic")
	private List<TopicVo> hotTopics;
	private List<TopicVo> topics;

	public List<TopicVo> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicVo> topics) {
		this.topics = topics;
	}

	public String getPcon() {
		return pcon;
	}

	public void setPcon(String pcon) {
		this.pcon = pcon;
	}

	public List<TopicVo> getHotTopics() {
		return hotTopics;
	}

	public void setHotTopics(List<TopicVo> hotTopics) {
		this.hotTopics = hotTopics;
	}
}

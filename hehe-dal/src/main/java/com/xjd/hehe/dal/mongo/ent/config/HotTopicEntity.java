package com.xjd.hehe.dal.mongo.ent.config;

import java.util.List;

/**
 * @author elvis.xu
 * @since 2015-10-31 21:46
 */
public class HotTopicEntity extends ConfigEntity {
	public static final String CODE = "HOT_TOPIC";

	public HotTopicEntity() {
		code = CODE;
	}

	private List<String> topics;

	public List<String> getTopics() {
		return topics;
	}

	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
}

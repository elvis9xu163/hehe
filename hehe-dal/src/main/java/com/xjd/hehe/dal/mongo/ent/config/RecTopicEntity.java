package com.xjd.hehe.dal.mongo.ent.config;

import java.util.List;

/**
 * @author elvis.xu
 * @since 2015-10-31 21:46
 */
public class RecTopicEntity extends ConfigEntity {
	public static final String CODE = "REC_TOPIC";

	public RecTopicEntity() {
		code = CODE;
	}

	List<List<String>> pageTopics;

	public List<List<String>> getPageTopics() {
		return pageTopics;
	}

	public void setPageTopics(List<List<String>> pageTopics) {
		this.pageTopics = pageTopics;
	}
}

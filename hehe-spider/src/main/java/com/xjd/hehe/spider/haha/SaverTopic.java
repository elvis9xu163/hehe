package com.xjd.hehe.spider.haha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.TopicDao;
import com.xjd.hehe.dal.mongo.ent.TopicEntity;

@Service
public class SaverTopic {
	@Autowired
	TopicDao topicDao;

	public TopicEntity save(String name, String uid) {
		TopicEntity entity = topicDao.getByName(name);

		if (entity != null) {
			return entity;
		}

		entity = new TopicEntity();
		entity.setUid(uid);
		entity.setName(name);
		topicDao.save(entity);
		return entity;
	}
}

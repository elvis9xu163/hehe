package com.xjd.hehe.spider.haha.saver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.TopicDao;
import com.xjd.hehe.dal.mongo.ent.TopicEntity;

@Service
public class SaverTopic {
	public static Logger log = LoggerFactory.getLogger(SaverTopic.class);
	@Autowired
	TopicDao topicDao;

	public TopicEntity save(String name, String uid) {
		if ("0".equals(name)) {
			return null;
		}

		TopicEntity entity = topicDao.getByName(name);

		if (entity != null) {
			return entity;
		}
		entity = new TopicEntity();
		entity.setUid(uid);
		entity.setName(name);
		topicDao.save(entity);
		log.info("新增Topic: {}, {}", entity.getId(), entity.getName());
		return entity;
	}

	public int incJoke(String id) {
		int rt = topicDao.incNJoke(id);
		log.info("增加Topic的关联joke数: {}, rt={}", id, rt);
		return rt;
	}
}

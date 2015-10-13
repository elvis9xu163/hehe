package com.xjd.hehe.dal.mongo.dao;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.TopicEntity;

@Repository
public class TopicDao extends BaseDao<TopicEntity> {

	@Autowired
	public TopicDao(MongoDao mongoDao) {
		super(TopicEntity.class, mongoDao);
	}

	public TopicEntity getByName(String name) {
		return createQuery().filter("name =", name).get();
	}

	public int incNJoke(String id) {
		return getDatastore().update(createQuery().filter("_id =", new ObjectId(id)), createUpdateOperations().inc("njoke")).getUpdatedCount();
	}
}

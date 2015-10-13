package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.TopicEntity;

@Repository
public class TopicDao extends BaseDao<TopicEntity> {

	@Autowired
	public TopicDao(MongoDao mongoDao) {
		super(TopicEntity.class, mongoDao);
	}

}

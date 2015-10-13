package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.JokeEntity;

@Repository
public class JokeDao extends BaseDao<JokeEntity> {

	@Autowired
	public JokeDao(MongoDao mongoDao) {
		super(JokeEntity.class, mongoDao);
	}

}

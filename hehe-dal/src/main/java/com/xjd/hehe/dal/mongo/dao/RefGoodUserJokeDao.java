package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.RefGoodUserJokeEntity;

@Repository
public class RefGoodUserJokeDao extends BaseDao<RefGoodUserJokeEntity> {

	@Autowired
	public RefGoodUserJokeDao(MongoDao mongoDao) {
		super(RefGoodUserJokeEntity.class, mongoDao);
	}

}

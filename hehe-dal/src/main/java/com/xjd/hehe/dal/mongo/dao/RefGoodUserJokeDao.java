package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.RefGoodUserEntity;

@Repository
public class RefGoodUserJokeDao extends BaseDao<RefGoodUserEntity> {

	@Autowired
	public RefGoodUserJokeDao(MongoDao mongoDao) {
		super(RefGoodUserEntity.class, mongoDao);
	}

}

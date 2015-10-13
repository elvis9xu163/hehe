package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity> {

	@Autowired
	public UserDao(MongoDao mongoDao) {
		super(UserEntity.class, mongoDao);
	}

}

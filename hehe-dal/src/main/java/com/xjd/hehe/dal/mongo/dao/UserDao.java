package com.xjd.hehe.dal.mongo.dao;

import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObjectBuilder;
import com.xjd.hehe.dal.mongo.ent.UserEntity;

@Repository
public class UserDao extends BaseDao<UserEntity> {

	@Autowired
	public UserDao(MongoDao mongoDao) {
		super(UserEntity.class, mongoDao);
	}

	// ========= for spider ==============
	public UserEntity getByRefIdAndRefFrom(String id, Byte from) {
		Query<UserEntity> query = createQuery();
		return query.filter("refs elem", BasicDBObjectBuilder.start("id", id).append("from", from).get()).get();
	}
}

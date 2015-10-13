package com.xjd.hehe.dal.mongo.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;

@Repository
public class MongoDao {
	@Autowired
	protected Datastore datastore;
	@Autowired
	protected MongoClient mongoClient;

	public <T> void save(T entity) {
		datastore.save(entity);
	}

	public <T> T get(Class<T> clazz, String id) {
		return datastore.getByKey(clazz, new Key<T>(clazz, datastore.getCollection(clazz).getName(), new ObjectId(id)));
	}

}

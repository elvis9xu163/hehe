package com.xjd.hehe.dal.mongo.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

public abstract class BaseDao<T> {
	protected Class<T> entityClass;
	protected MongoDao mongoDao;

	public BaseDao(Class<T> entityClass, MongoDao mongoDao) {
		this.entityClass = entityClass;
		this.mongoDao = mongoDao;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public MongoDao getMongoDao() {
		return mongoDao;
	}
	
	public Datastore getDatastore() {
		return getMongoDao().getDatastore();
	}
	
	public Query<T> createQuery() {
		return getDatastore().createQuery(getEntityClass());
	}

	public void save(T entity) {
		getMongoDao().save(entity);
	}

	public T get(String id) {
		return getMongoDao().get(entityClass, id);
	}
}

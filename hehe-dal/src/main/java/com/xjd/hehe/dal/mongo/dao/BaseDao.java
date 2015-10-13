package com.xjd.hehe.dal.mongo.dao;

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

	public void save(T entity) {
		mongoDao.save(entity);
	}

	public T get(String id) {
		return mongoDao.get(entityClass, id);
	}
}

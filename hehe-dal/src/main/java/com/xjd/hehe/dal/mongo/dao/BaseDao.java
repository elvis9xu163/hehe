package com.xjd.hehe.dal.mongo.dao;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.xjd.hehe.dal.mongo.ent.BaseEntity;

public abstract class BaseDao<T extends BaseEntity> {
	protected CacheManager cacheManager;
	protected Class<T> entityClass;
	protected MongoDao mongoDao;

	public BaseDao(Class<T> entityClass, MongoDao mongoDao) {
		this.entityClass = entityClass;
		this.mongoDao = mongoDao;
	}

	public BaseDao(Class<T> entityClass, MongoDao mongoDao, CacheManager cacheManager) {
		this.entityClass = entityClass;
		this.mongoDao = mongoDao;
		this.cacheManager = cacheManager;
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

	public UpdateOperations<T> createUpdateOperations() {
		return getDatastore().createUpdateOperations(getEntityClass());
	}

	public void save(T entity) {
		getMongoDao().save(entity);
	}

	public T get(String id) {
		return getMongoDao().get(entityClass, id);
	}

	protected T getFromCache(String refKey) {
		if (cacheManager != null) {
			Cache keyCache = cacheManager.getCache("key_map");
			String idKey = keyCache.get(refKey, String.class);
			if (idKey != null) {
				Cache entCache = cacheManager.getCache("entity");
				return entCache.get(idKey, entityClass);
			}
		}
		return null;
	}

	protected void putIntoCache(String refKey, T entity) {
		if (cacheManager != null && entity != null) {
			String entId = "ENT:" + entityClass.getSimpleName() + ":" + entity.getId();
			Cache entCache = cacheManager.getCache("entity");
			entCache.put(entId, entity);
			Cache keyCache = cacheManager.getCache("key_map");
			keyCache.put(refKey, entId);
		}
	}
}

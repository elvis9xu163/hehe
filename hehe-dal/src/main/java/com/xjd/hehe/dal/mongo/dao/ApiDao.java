package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.ApiEntity;

/**
 * @author elvis.xu
 * @since 2015-10-25 23:32
 */
@Repository
public class ApiDao extends BaseDao<ApiEntity> {
	@Autowired
	public ApiDao(MongoDao mongoDao, CacheManager cacheManager) {
		super(ApiEntity.class, mongoDao, cacheManager);
	}
}

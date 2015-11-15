package com.xjd.hehe.dal.mongo.dao;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.TokenEntity;
import com.xjd.hehe.utl.enums.TokenStatusEnum;

/**
 * @author elvis.xu
 * @since 2015-10-25 22:48
 */
@Repository
public class TokenDao extends BaseDao<TokenEntity> {
	@Autowired
	public TokenDao(MongoDao mongoDao, CacheManager cacheManager) {
		super(TokenEntity.class, mongoDao, cacheManager);
	}


	public int invalidToken(String uid, String endId) {
		Query<TokenEntity> query = createQuery();
		query.field("status").equal(TokenStatusEnum.NORMAL.getCode()).or(query.criteria("uid").equal(uid), query.criteria("endId").equal(endId));
		TokenEntity tokenEntity = query.get();
		if (tokenEntity == null) return 0;
		if (cacheManager != null) {
			Cache entCache = cacheManager.getCache("entity");
			entCache.evict("ENT:TokenEntity:" + tokenEntity.getId());
		}
		UpdateOperations<TokenEntity> update = createUpdateOperations().set("status", TokenStatusEnum.INVALID.getCode());
		UpdateResults updateResults = getDatastore().update(query, update);
		return updateResults.getUpdatedCount();
	}
}

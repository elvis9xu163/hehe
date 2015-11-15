package com.xjd.hehe.dal.mongo.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.TopicEntity;

@Repository
public class TopicDao extends BaseDao<TopicEntity> {

	@Autowired
	public TopicDao(MongoDao mongoDao, CacheManager cacheManager) {
		super(TopicEntity.class, mongoDao, cacheManager);
	}

	public TopicEntity getByName(String name) {
		return createQuery().filter("name =", name).get();
	}

	@CacheEvict(value="entity", key="'ENT:TopicEntity:' + #id")
	public int incNJoke(String id) {
		return getDatastore().update(createQuery().filter("_id =", new ObjectId(id)), createUpdateOperations().inc("njoke")).getUpdatedCount();
	}

	public List<TopicEntity> getByPage(int offset, int limit, List<String> excludeTids) {
		Query<TopicEntity> query = createQuery();
		if (CollectionUtils.isNotEmpty(excludeTids)) {
			query.field("id").notIn(excludeTids);
		}
		return query.order("-njoke, -utime").offset(offset).limit(limit).asList();
	}
}

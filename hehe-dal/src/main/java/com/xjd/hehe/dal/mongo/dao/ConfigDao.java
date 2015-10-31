package com.xjd.hehe.dal.mongo.dao;

import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.config.ConfigEntity;

/**
 * @author elvis.xu
 * @since 2015-10-31 21:17
 */
@Repository
public class ConfigDao {

	@Autowired
	Datastore datastore;

	public <T extends ConfigEntity> T get(String code, Class<T> clazz) {
		return datastore.createQuery(clazz).field("code").equal(code).get();
	}
}

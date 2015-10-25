package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.TokenEntity;

/**
 * @author elvis.xu
 * @since 2015-10-25 22:48
 */
@Repository
public class TokenDao extends BaseDao<TokenEntity> {
	@Autowired
	public TokenDao(MongoDao mongoDao) {
		super(TokenEntity.class, mongoDao);
	}


}

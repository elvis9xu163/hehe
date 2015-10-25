package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.ReqLogEntity;

/**
 * @author elvis.xu
 * @since 2015-10-25 22:43
 */
@Repository
public class ReqLogDao extends BaseDao<ReqLogEntity> {
	@Autowired
	public ReqLogDao(MongoDao mongoDao) {
		super(ReqLogEntity.class, mongoDao);
	}
}

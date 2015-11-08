package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.RefGoodUserEntity;

@Repository
public class RefGoodUserDao extends BaseDao<RefGoodUserEntity> {

	@Autowired
	public RefGoodUserDao(MongoDao mongoDao) {
		super(RefGoodUserEntity.class, mongoDao);
	}

	public RefGoodUserEntity getByUidAndOidAndOtype(String uid, String oid, byte otype) {
		return createQuery().filter("uid =", uid).filter("oid =", oid).filter("otype", otype).get();
	}
}

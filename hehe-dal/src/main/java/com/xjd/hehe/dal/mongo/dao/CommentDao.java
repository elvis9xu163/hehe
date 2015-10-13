package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.CommentEntity;

@Repository
public class CommentDao extends BaseDao<CommentEntity> {

	@Autowired
	public CommentDao(MongoDao mongoDao) {
		super(CommentEntity.class, mongoDao);
	}

}

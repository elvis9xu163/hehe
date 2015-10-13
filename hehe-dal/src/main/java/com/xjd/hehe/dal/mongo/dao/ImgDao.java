package com.xjd.hehe.dal.mongo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.ImgEntity;

@Repository
public class ImgDao extends BaseDao<ImgEntity> {

	@Autowired
	public ImgDao(MongoDao mongoDao) {
		super(ImgEntity.class, mongoDao);
	}

}

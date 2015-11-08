package com.xjd.hehe.dal.mongo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.ImgEntity;

@Repository
public class ImgDao extends BaseDao<ImgEntity> {

	@Autowired
	public ImgDao(MongoDao mongoDao) {
		super(ImgEntity.class, mongoDao);
	}

	public ImgEntity getByMd5(String md5) {
		return createQuery().filter("md5 =", md5).get();
	}


	public ImgEntity getByUri(String uri) {
		return createQuery().filter("uri =", uri).get();
	}


	// ========= only for spider ============

	public List<ImgEntity> getNoMd5(int limit) {
		return createQuery().field("md5").doesNotExist().limit(limit).asList();
	}

	public ImgEntity getByRefUrl(String url) {
		return createQuery().filter("refUrl =", url).get();
	}

	public int updateMd5(String id, String md5, Long size) {
		Query<ImgEntity> query = createQuery().field("id").equal(new ObjectId(id));
		UpdateOperations<ImgEntity> update = createUpdateOperations().set("md5", md5).set("size", size);
		UpdateResults result = getDatastore().update(query, update);
		return result.getUpdatedCount();
	}

}

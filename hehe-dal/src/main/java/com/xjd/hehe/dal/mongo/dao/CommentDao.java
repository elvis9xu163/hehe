package com.xjd.hehe.dal.mongo.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.CommentEntity;
import com.xjd.hehe.dal.mongo.ent.JokeEntity;

@Repository
public class CommentDao extends BaseDao<CommentEntity> {

	@Autowired
	public CommentDao(MongoDao mongoDao) {
		super(CommentEntity.class, mongoDao);
	}

	public CommentEntity getByRefId(String id) {
		return createQuery().filter("ref.id =", id).get();
	}

	// ======= only for spider ========
	public int incRefGood(String id, int good) {
		Query<CommentEntity> query = createQuery().filter("_id =", new ObjectId(id));
		UpdateOperations<CommentEntity> update = createUpdateOperations().inc("ngood", good).inc("ref.good", good);
		return getDatastore().update(query, update).getUpdatedCount();
	}

}

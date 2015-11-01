package com.xjd.hehe.dal.mongo.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xjd.hehe.dal.mongo.ent.JokeEntity;

@Repository
public class JokeDao extends BaseDao<JokeEntity> {

	@Autowired
	public JokeDao(MongoDao mongoDao) {
		super(JokeEntity.class, mongoDao);
	}


	public List<JokeEntity> getNew(Date time, int limit) {
		Query<JokeEntity> query = createQuery();
		if (time != null) {
			query.field("ctime").lessThan(time);
		}
		return query.order("-ctime").limit(limit).asList();
	}

	// ======= only for spider ========
	public JokeEntity getByRefId(String id) {
		return createQuery().filter("ref.id =", id).get();
	}

	public int incRefGood(String id, int good) {
		Query<JokeEntity> query = createQuery().filter("_id =", new ObjectId(id));
		UpdateOperations<JokeEntity> update = createUpdateOperations().inc("ngood", good).inc("ref.good", good);
		return getDatastore().update(query, update).getUpdatedCount();
	}

	public int incRefBad(String id, int bad) {
		Query<JokeEntity> query = createQuery().filter("_id =", new ObjectId(id));
		UpdateOperations<JokeEntity> update = createUpdateOperations().inc("nbad", bad).inc("ref.bad", bad);
		return getDatastore().update(query, update).getUpdatedCount();
	}

	public int incRefCmt(String id, int cmt) {
		Query<JokeEntity> query = createQuery().filter("_id =", new ObjectId(id));
		UpdateOperations<JokeEntity> update = createUpdateOperations().inc("ncmt", cmt).inc("ref.cmt", cmt);
		return getDatastore().update(query, update).getUpdatedCount();
	}

	public int auditFail(String id) {
		Query<JokeEntity> query = createQuery().filter("_id =", new ObjectId(id));
		UpdateOperations<JokeEntity> update = createUpdateOperations().set("status", 2);
		return getDatastore().update(query, update).getUpdatedCount();
	}

}

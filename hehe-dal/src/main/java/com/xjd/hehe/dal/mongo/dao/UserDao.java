package com.xjd.hehe.dal.mongo.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObjectBuilder;
import com.xjd.hehe.dal.mongo.ent.UserEntity;
import com.xjd.hehe.utl.enums.UserTypeEnum;

@Repository
public class UserDao extends BaseDao<UserEntity> {

	@Autowired
	public UserDao(MongoDao mongoDao) {
		super(UserEntity.class, mongoDao);
	}

	public UserEntity getBy$MailOrName$AndPwd(String uname, String encPwd) {
		Query<UserEntity> query = createQuery();
		query.or(query.criteria("mobile").equal(uname), query.criteria("mail").equal(uname));
		return query.field("pwd").equal(encPwd).get();
	}

	public int regist(String uid, String mail, String mobile, String encPwd) {
		Query<UserEntity> query = createQuery().field("id").equal(new ObjectId(uid));
		UpdateOperations<UserEntity> update = createUpdateOperations().set("pwd", encPwd).set("type", UserTypeEnum.NORMAL.getCode());
		if (mail != null) {
			update.set("mail", mail);
		} else {
			update.unset("mail");
		}
		if (mobile != null) {
			update.set("mobile", mobile);
		} else {
			update.unset("mobile");
		}
		UpdateResults updateResults = getDatastore().update(query, update);
		return updateResults.getUpdatedCount();
	}

	public int updateNickAndAvatar(String uid, String nick, String url) {
		Query<UserEntity> query = createQuery().field("id").equal(new ObjectId(uid));
		UpdateOperations<UserEntity> update = createUpdateOperations().set("name", nick).set("avatar", url);
		UpdateResults updateResults = getDatastore().update(query, update);
		return updateResults.getUpdatedCount();
	}


	// ========= for spider ==============
	public UserEntity getByRefIdAndRefFrom(String id, Byte from) {
		Query<UserEntity> query = createQuery();
		return query.filter("refs elem", BasicDBObjectBuilder.start("id", id).append("from", from).get()).get();
	}


}

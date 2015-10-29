package com.xjd.hehe.dal.mongo.dao;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjd.hehe.dal.mongo.MongoBaseTest;
import com.xjd.hehe.dal.mongo.ent.Test2Entity;
import com.xjd.hehe.dal.mongo.ent.TestEntity;
import com.xjd.hehe.utl.JsonUtil;

public class MongoDaoTest extends MongoBaseTest {
	private static Logger log = LoggerFactory.getLogger(MongoDaoTest.class);

	@Autowired
	MongoDao mongoDao;

	@Test
	public void test() {
		{
			TestEntity testEntity = new TestEntity();
			mongoDao.save(testEntity);
			Assertions.assertThat(testEntity.getId()).isNotNull();
			log.debug("save entity success: {}, {}", TestEntity.class.getSimpleName(), testEntity.getId());

			testEntity = mongoDao.get(TestEntity.class, testEntity.getId());
			Assertions.assertThat(testEntity).isNotNull();
			log.debug("get entity success: {}, {}", TestEntity.class.getSimpleName(), testEntity.getId());
		}
		{
			Test2Entity testEntity = new Test2Entity();
			mongoDao.save(testEntity);
			Assertions.assertThat(testEntity.getId()).isNotNull();
			log.debug("save entity success: {}, {}", Test2Entity.class.getSimpleName(), testEntity.getId());

			testEntity = mongoDao.get(Test2Entity.class, testEntity.getId());
			Assertions.assertThat(testEntity).isNotNull();
			log.debug("get entity success: {}, {}", Test2Entity.class.getSimpleName(), testEntity.getId());
		}

		{
			Test2Entity test2 = mongoDao.get(Test2Entity.class, "code =", Test2Entity.CODE);
			log.debug("test2: {}", JsonUtil.toString(test2));
		}
	}
}

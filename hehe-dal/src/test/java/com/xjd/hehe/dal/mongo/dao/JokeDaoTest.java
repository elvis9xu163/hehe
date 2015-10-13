package com.xjd.hehe.dal.mongo.dao;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjd.hehe.dal.mongo.ent.JokeEntity;

public class JokeDaoTest extends MongoDaoTest {
	private static Logger log = LoggerFactory.getLogger(JokeDaoTest.class);
	@Autowired
	JokeDao jokeDao;

	@Test
	public void test() {
		JokeEntity jokeEntity = new JokeEntity();
		jokeDao.save(jokeEntity);

		Assertions.assertThat(jokeEntity.getId()).isNotNull();
		log.debug("save success: {}, {}", JokeEntity.class.getSimpleName(), jokeEntity.getId());

		jokeEntity = jokeDao.get(jokeEntity.getId());
		Assertions.assertThat(jokeEntity).isNotNull();
		log.debug("get success: {}, {}", JokeEntity.class.getSimpleName(), jokeEntity.getId());
	}

}

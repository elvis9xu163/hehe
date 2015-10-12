package com.xjd.hehe.dal.mongodb;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjd.hehe.dal.BaseTest;

/**
 * @author elvis.xu
 * @since 2015-10-13 00:12
 */
public class MongodbBaseTest extends BaseTest {
	@Autowired
	Datastore datastore;

	@Test
	public void mongodbTestBase() {
		Assertions.assertThat(datastore).isNotNull();
	}
}

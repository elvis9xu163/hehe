package com.xjd.hehe.dal.api;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-*.xml")
public class TestBase {
	@Autowired
	ApplicationContext contxt;

	@Test
	public void baseTest() {
		Assertions.assertThat(contxt).isNotNull();
	}
}

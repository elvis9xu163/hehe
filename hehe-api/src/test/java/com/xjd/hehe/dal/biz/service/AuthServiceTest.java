package com.xjd.hehe.dal.biz.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjd.hehe.dal.api.TestBase;
import com.xjd.hehe.dal.entity.User;
import com.jkys.social.util.JsonUtil;

public class AuthServiceTest extends TestBase {
	@Autowired
	AuthService authService;

	@Test
	public void test() {
		List<Integer> idList = Arrays.asList(112, 113);

		List<User> us = authService.loadUserByIdList(idList);
		for (User u : us) {
			System.out.println(JsonUtil.toString(u));
		}
		
		for (Integer id : idList) {
			authService.getUserById(id);
		}
	}

}

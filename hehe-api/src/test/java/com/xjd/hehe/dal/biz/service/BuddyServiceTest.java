package com.xjd.hehe.dal.biz.service;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjd.hehe.dal.api.TestBase;
import com.xjd.hehe.dal.entity.Buddy;
import com.jkys.social.util.JsonUtil;

public class BuddyServiceTest extends TestBase {
	
	@Autowired
	BuddyService buddyService;

	@Test
	public void test() {
		List<String> idList = Arrays.asList("55ee7966018c5aec85129bdf", "55f119a03ad08cb3f45a1be7");
		
		for (String id : idList) {
			buddyService.getBuddyByBuddyId(id);
		}
		
		List<Buddy> us = buddyService.loadBuddyByIdList(idList);
		for (Buddy u : us) {
			System.out.println(JsonUtil.toString(u));
		}
		
		
	}

}

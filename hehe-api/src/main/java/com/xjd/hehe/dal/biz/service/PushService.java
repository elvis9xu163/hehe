package com.xjd.hehe.dal.biz.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.dao.mongo.BuddyDAO;
import com.xjd.hehe.dal.dao.mongo.MessageDAO;
import com.xjd.hehe.dal.entity.User;
import com.xjd.hehe.dal.itg.PushItg;

@Service
public class PushService {
	private static Logger log = LoggerFactory.getLogger(PushService.class);

	@Autowired
	PushItg pushItg;
	@Autowired
	BuddyDAO buddyDAO;
	@Autowired
	MessageDAO messageDAO;
	@Autowired
	AuthService authService;
	@Autowired
	ThreadPoolTaskExecutor executor;

	/**
	 * 发送最新动态
	 * @param buddy
	 */
	
	public void pushNewDynamic(final List<String> idList) {
		executor.execute(new Runnable() {
			public void run() {
				doPushNewDynamic(idList);
			}
		});
	}
	
	@Profiled
	public void doPushNewDynamic(List<String> idList) {
		List<Integer> ptUserList = new LinkedList<Integer>();
		List<Integer> dtUserList = new LinkedList<Integer>();
		for (String id : idList) {
			Integer userId = buddyDAO.queryUserIdByBuddyId(id);
			if (userId == null) {
				log.warn("用户不存在，无法发送推送: buddyId={}", id);
				continue;
			}
			User user = authService.getUserById(userId);
			Byte app = null;
			if (Byte.valueOf((byte) 1).equals(user.getUtype())) { // doc
				dtUserList.add(userId);
			} else {
				ptUserList.add(userId);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "");
		map.put("content", "");
		map.put("targetType", 0);
		map.put("targetPage", "");
		map.put("type", 101);
		
		if (!ptUserList.isEmpty()) {
			pushItg.sendToUser((byte) 1, "", "", map, ptUserList);
		}
		if (!dtUserList.isEmpty()) {
			pushItg.sendToUser((byte) 2, "", "", map, dtUserList);
		}
	}

	public void pushUnreadMsgCount(final String buddyId) {
		executor.execute(new Runnable() {
			public void run() {
				doPushUnreadMsgCount(buddyId);
			}
		});
	}
	
	@Profiled
	public void doPushUnreadMsgCount(String buddyId) {
		Integer userId = buddyDAO.queryUserIdByBuddyId(buddyId);
		if (userId == null) {
			log.warn("用户不存在，无法发送推送: buddyId={}", buddyId);
			return;
		}
		User user = authService.getUserById(userId);
		Byte app = null;
		if (Byte.valueOf((byte) 1).equals(user.getUtype())) { // doc
			app = (byte) 2;
		} else {
			app = (byte) 1;
		}
		
		Long count = messageDAO.countUnreadForUser(buddyId);
		
		String title = "系统消息";
		String content = "您有" + count + "条未读消息";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("content", content);
		map.put("targetType", 0);
		map.put("targetPage", "");
		map.put("type", 100);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("count", count);
		map.put("data", data);
		
		pushItg.sendToUser(app, title, content, map, Arrays.asList(userId));
	}
}

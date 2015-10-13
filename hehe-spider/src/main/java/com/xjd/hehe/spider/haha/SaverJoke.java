package com.xjd.hehe.spider.haha;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.dao.JokeDao;
import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.dal.mongo.ent.UserEntity;

@Service
public class SaverJoke {
	@Autowired
	ImgDao imgDao;
	@Autowired
	JokeDao jokeDao;

	@Autowired
	SaverUser saverUser;
	@Autowired
	SaverTopic saverTopic;
	@Autowired
	SaverImg saverImg;

	@Autowired
	SpiderJokeDetail spiderJokeDetail;

	public void save(List jokeList) {
		for (Object jokeObj : jokeList) {
			Map joke = (Map) jokeObj;

			long jokeId = ((Integer) joke.get("id")).longValue();
			String content = (String) joke.get("content");

			if (content != null && content.endsWith("...")) { // 有截取
				spiderJokeDetail.grap(jokeId);
				continue;
			}

			// 用户信息
			Integer hid = (Integer) joke.get("uid");
			String hname = (String) joke.get("user_name");
			String havatar = (String) joke.get("user_pic");
			UserEntity userEntity = saverUser.saveUser(hid + "", hname, havatar);

			// Topic信息
			String htopic = (String) joke.get("topic_content");
			saverTopic.save(htopic, userEntity.getId());

			// Img信息
			// http://image.haha.mx/2015/10/13/middle/1980882_99e65f6d05c967401a954e02ef79fe83_1444722018.jpg
			if (joke.get("pic") != null) {
				saverImg.save((Map) joke.get("pic"));
			}

			// Joke 信息

			// Comment 信息

			JokeEntity jokeEntity = new JokeEntity();
		}
	}

	public void auditFail(Long jokeId) {

	}
}

package com.xjd.hehe.biz.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.JokeBo;
import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.utl.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.JokeDao;
import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.utl.enums.JokeStatusEnum;

/**
 * @author elvis.xu
 * @since 2015-10-25 09:40
 */
@Service
public class JokeService {
	@Autowired
	JokeDao jokeDao;
	@Autowired
	UserService userService;
	@Autowired
	TopicService topicService;
	@Autowired
	ResourceService resourceService;

	public JokeBo getJoke(String jid) {
		JokeEntity jokeEntity = jokeDao.get(jid);
		JokeBo jokeBo = BeanTrans.trans(jokeEntity);
		if (jokeBo != null && jokeBo.getStatus() == JokeStatusEnum.AUDIT_FAIL.getCode()) {
			jokeBo.setTxt(null);
			jokeBo.setPics(null);
		}
		if (jokeBo.getPics() != null) {
			for (int i = 0; i < jokeBo.getPics().size(); i++) {
				jokeBo.getPics().set(i, resourceService.transUrlToOutside(jokeBo.getPics().get(i)));
			}
		}
		return jokeBo;
	}

	public void consummate(JokeBo jokeBo, boolean user, boolean topic, boolean pjoke) {
		if (jokeBo == null) return;
		if (user && jokeBo.getUid() != null && jokeBo.getUser() == null) {
			jokeBo.setUser(userService.getUser(jokeBo.getUid()));
		}
		if (topic && jokeBo.getTopics() != null && jokeBo.getTopicList() == null) {
			List<TopicBo> list = new LinkedList<>();
			for (String tid : jokeBo.getTopics()) {
				list.add(topicService.getTopic(tid));
			}
			jokeBo.setTopicList(list);
		}
		if (pjoke && jokeBo.getPjid() != null && jokeBo.getPjoke() == null) {
			JokeBo joke = getJoke(jokeBo.getPjid());
			consummate(joke, true, true, false);
			jokeBo.setPjoke(joke);
		}
	}
}

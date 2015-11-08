package com.xjd.hehe.biz.service;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.JokeBo;
import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.utl.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.CommentDao;
import com.xjd.hehe.dal.mongo.dao.JokeDao;
import com.xjd.hehe.dal.mongo.dao.RefGoodUserDao;
import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.dal.mongo.ent.RefGoodUserEntity;
import com.xjd.hehe.utl.DateUtil;
import com.xjd.hehe.utl.enums.JokeCtypeEnum;
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
	CommentDao cmtDao;
	@Autowired
	UserService userService;
	@Autowired
	TopicService topicService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	RefGoodUserDao refGoodUserJokeDao;

	public JokeBo getJoke(String jid) {
		JokeEntity jokeEntity = jokeDao.get(jid);
		JokeBo jokeBo = BeanTrans.trans(jokeEntity);
		bizProcess(jokeBo);
		return jokeBo;
	}

	public void bizProcess(JokeBo jokeBo) {
		if (jokeBo == null) return;
		if (jokeBo != null && jokeBo.getStatus() == JokeStatusEnum.AUDIT_FAIL.getCode()) {
			jokeBo.setTxt(null);
			jokeBo.setPics(null);
		}
		if (jokeBo.getPics() != null) {
			for (int i = 0; i < jokeBo.getPics().size(); i++) {
				jokeBo.getPics().set(i, resourceService.transUrlToOutside(jokeBo.getPics().get(i)));
			}
		}
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

	public List<JokeBo> listNewJoke(Date time) {
		List<JokeEntity> jokeEntityList = jokeDao.getNew(time, 20);

		List<JokeBo> jokeBoList = BeanTrans.transJoke(jokeEntityList);
		if (jokeBoList != null) {
			for (JokeBo jokeBo : jokeBoList) {
				bizProcess(jokeBo);
				consummate(jokeBo, true, true, true);
			}
		}

		return jokeBoList;
	}

	public JokeBo addJoke(String uid, String txt, String url, String pjid, List<String> topicList) {
		JokeEntity jokeEntity = new JokeEntity();
		jokeEntity.setUid(uid);
		jokeEntity.setTxt(txt);
		jokeEntity.setPics(Arrays.asList(url));
		jokeEntity.setPjid(pjid);
		jokeEntity.setTopics(CollectionUtils.isEmpty(topicList) ? null : topicList);

		byte ctype = 0;
		if (txt != null && url != null) {
			ctype = JokeCtypeEnum.TEXT_PIC.getCode();
		} else if (txt != null) {
			ctype = JokeCtypeEnum.TEXT.getCode();
		} else if (url != null) {
			ctype = JokeCtypeEnum.PIC.getCode();
		}
		jokeEntity.setCtype(ctype);

		jokeDao.save(jokeEntity);

		JokeBo jokeBo = BeanTrans.trans(jokeEntity);
		bizProcess(jokeBo);
		return jokeBo;
	}

	public void like(String uid, String oid, byte otype, boolean like) {
		RefGoodUserEntity refGoodUserEntity = refGoodUserJokeDao.getByUidAndOidAndOtype(uid, oid, otype);

		if (refGoodUserEntity == null) {
			refGoodUserEntity = new RefGoodUserEntity();
			refGoodUserEntity.setUid(uid);
			refGoodUserEntity.setOid(oid);
			refGoodUserEntity.setOtype(otype);
			refGoodUserEntity.setGb(like ? (byte) 1 : (byte) 0);
			refGoodUserEntity.setCtime(DateUtil.now());
			refGoodUserEntity.setUtime(DateUtil.now());
			refGoodUserJokeDao.save(refGoodUserEntity);

			if (otype == 1) { // joke
				if (like) {
					jokeDao.incGood(oid);
				} else {
					jokeDao.incBad(oid);
				}
			} else { // comment
				if (like) {
					cmtDao.incGood(oid);
				}
			}
		}
		// TODO 提示一点
	}
}

package com.xjd.hehe.spider.haha.saver;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.dao.JokeDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;
import com.xjd.hehe.dal.mongo.ent.JokeEntity;
import com.xjd.hehe.dal.mongo.ent.TopicEntity;
import com.xjd.hehe.dal.mongo.ent.UserEntity;
import com.xjd.hehe.spider.haha.bean.Joke;
import com.xjd.hehe.spider.haha.spider.SpiderJokeComment;
import com.xjd.hehe.spider.haha.spider.SpiderJokeDetail;

@Service
public class SaverJoke {
	public static Logger log = LoggerFactory.getLogger(SaverJoke.class);

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
	@Autowired
	SpiderJokeComment spiderJokeComment;

	public void save(List<Joke> jokes, boolean needDetail) {
		for (Joke joke : jokes) {
			try {
				save(joke, needDetail);
			} catch (Exception e) {
				log.error("处理joke失败: jid=" + joke.getId(), e);
			}
		}
	}

	public void save(Joke joke, boolean needDetail) {
		if (joke.getType() != 0 && joke.getType() != 1) {
			log.warn("无法处理的Joke类型: jid={}, type={}", joke.getId(), joke.getType());
			return;
		}

		JokeEntity entity = jokeDao.getByRefId(joke.getId().toString());

		if (entity != null) { // 已经存在, 更新数目
			update(joke, entity);

		} else {
			create(joke, needDetail);
		}
	}

	public void update(Joke joke, JokeEntity entity) {
		int incGood = joke.getGood() - entity.getRef().getGood();
		int incBad = joke.getBad() - entity.getRef().getBad();
		// int incCmt = joke.getComment_num() - entity.getRef().getCmt();
		if (incGood > 0) {
			jokeDao.incRefGood(entity.getId(), incGood);
			log.info("增长哈哈点赞数: {}, {}", entity.getId(), incGood);
		}
		if (incBad > 0) {
			jokeDao.incRefBad(entity.getId(), incBad);
			log.info("增长哈哈鄙视数: {}, {}", entity.getId(), incBad);
		}
		// if (incCmt > 0) {
		// jokeDao.incRefCmt(entity.getId(), incCmt);
		// log.info("增长哈哈评论数: {}, {}", entity.getId(), incCmt);
		getComments(entity);
		// }
	}

	public void create(Joke joke, boolean needDetail) {
		// 不存在则新增
		// 如果内容不全, 获取详请
		if (needDetail && joke.getContent() != null && joke.getContent().endsWith("...")) {
			spiderJokeDetail.grap(joke.getId());
			return;
		}

		// 如果有父Joke
		JokeEntity rootEntity = null;
		if (joke.getRoot() != null) {
			save(joke.getRoot(), true);
			rootEntity = jokeDao.getByRefId(joke.getRoot().getId().toString());
			if (rootEntity == null) {
				log.warn("由于父Joke无法获取, 说以获取失败: jid={}, pjid={}", joke.getId(), joke.getRoot().getId());
				return;
			}
		}

		// 用户信息
		UserEntity userEntity = saverUser.save(joke.getUid().toString(), joke.getUser_name(), joke.getUser_pic());

		// Topic信息, 可为null
		TopicEntity topicEntity = saverTopic.save(joke.getTopic_content(), userEntity.getId());

		// Img信息, 可为null
		ImgEntity imgEntity = saverImg.save(joke.getPic());

		// joke信息
		JokeEntity entity = new JokeEntity();
		entity.setUid(userEntity.getId());
		entity.setCtype(joke.getType() == 1 ? (byte) 2 : (byte) 0);
		entity.setTxt(joke.getContent());
		entity.setPics(imgEntity == null ? null : Arrays.asList(imgEntity.getUri()));
		entity.setTopics(topicEntity == null ? null : Arrays.asList(topicEntity.getId()));
		entity.setNgood(joke.getGood());
		entity.setNbad(joke.getBad());
		// entity.setNcmt(joke.getComment_num());
		entity.setPjid(rootEntity == null ? null : rootEntity.getPjid());
		entity.setFrom((byte) 10);

		JokeEntity.Ref ref = new JokeEntity.Ref();
		ref.setId(joke.getId().toString());
		ref.setUid(joke.getUid().toString());
		ref.setGood(joke.getGood());
		ref.setBad(joke.getBad());
		// ref.setCmt(joke.getComment_num());
		ref.setPtime(joke.getTime());
		entity.setRef(ref);
		jokeDao.save(entity);
		log.info("新增Joke: {}, {}", entity.getId(), ref.getId());

		// topic数量变化
		if (topicEntity != null) {
			saverTopic.incJoke(topicEntity.getId());
		}

		// User数量变化 FIXME

		// Comment信息
		getComments(entity);
	}

	public void getComments(JokeEntity jokeEntity) {
		log.info("准备获取Joke的评论列表: {}", jokeEntity.getId());
		spiderJokeComment.grap(jokeEntity);
	}

	public void auditFail(Long jokeId) {
		jokeDao.auditFail(jokeId.toString());
		log.info("joke审核拒绝: {}", jokeId);
	}

	public void incRefCmt(String id) {
		jokeDao.incRefCmt(id, 1);
		log.info("增长哈哈评论数: {}, {}", id, 1);
	}
}

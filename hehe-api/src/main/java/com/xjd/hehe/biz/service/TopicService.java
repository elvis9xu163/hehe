package com.xjd.hehe.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.utl.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.TopicDao;
import com.xjd.hehe.dal.mongo.ent.TopicEntity;

/**
 * @author elvis.xu
 * @since 2015-10-25 09:41
 */
@Service
public class TopicService {
	@Autowired
	TopicDao topicDao;

	public TopicBo getTopic(String tid) {
		TopicEntity topicEntity = topicDao.get(tid);
		return BeanTrans.trans(topicEntity);
	}

	public List<TopicBo> listTopic(int page, List<String> excludeTids) {
		int limit = 20;
		int offset = limit * page;

		List<TopicEntity> topicEntityList = topicDao.getByPage(offset, limit, excludeTids);
		return BeanTrans.transTopic(topicEntityList);
	}
}

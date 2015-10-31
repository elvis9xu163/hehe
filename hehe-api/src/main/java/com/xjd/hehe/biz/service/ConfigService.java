package com.xjd.hehe.biz.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.BannerBo;
import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.utl.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.ConfigDao;
import com.xjd.hehe.dal.mongo.ent.config.BannerEntity;
import com.xjd.hehe.dal.mongo.ent.config.HotTopicEntity;
import com.xjd.hehe.dal.mongo.ent.config.RecTopicEntity;

/**
 * @author elvis.xu
 * @since 2015-10-31 21:12
 */
@Service
public class ConfigService {
	@Autowired
	ConfigDao configDao;
	@Autowired
	TopicService topicService;

	public List<BannerBo> listBanner() {
		BannerEntity bannerEntity = configDao.get(BannerEntity.CODE, BannerEntity.class);
		List<BannerBo> list = new LinkedList<>();
		if (bannerEntity != null && bannerEntity.getItemList() != null) {
			for (BannerEntity.Item item : bannerEntity.getItemList()) {
				list.add(BeanTrans.trans(item));
			}
		}
		return list;
	}

	public List<TopicBo> listRecTopic() {
		RecTopicEntity recTopicEntity = configDao.get(RecTopicEntity.CODE, RecTopicEntity.class);

		List<TopicBo> list = new LinkedList<>();
		if (recTopicEntity != null && CollectionUtils.isNotEmpty(recTopicEntity.getPageTopics())) {
			int page = new Random().nextInt(recTopicEntity.getPageTopics().size());

			List<String> tids = recTopicEntity.getPageTopics().get(page);
			for (String tid : tids) {
				list.add(topicService.getTopic(tid));
			}
		}
		return list;
	}

	public List<TopicBo> listHotTopic() {
		HotTopicEntity hotTopicEntity = configDao.get(RecTopicEntity.CODE, HotTopicEntity.class);

		List<TopicBo> list = new LinkedList<>();
		if (hotTopicEntity != null && CollectionUtils.isNotEmpty(hotTopicEntity.getTopics())) {
			for (String tid : hotTopicEntity.getTopics()) {
				list.add(topicService.getTopic(tid));
			}
		}
		return list;
	}
}

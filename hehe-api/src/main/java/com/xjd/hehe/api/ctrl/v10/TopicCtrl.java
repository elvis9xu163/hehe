package com.xjd.hehe.api.ctrl.v10;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.TopicListBody;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.service.ConfigService;
import com.xjd.hehe.biz.service.TopicService;
import com.xjd.nhs.annotation.RequestMapping;
import com.xjd.nhs.annotation.RequestParam;

/**
 * @author elvis.xu
 * @since 2015-10-29 21:14
 */
@Controller
@RequestMapping("/api/10")
public class TopicCtrl {
	public static Logger log = LoggerFactory.getLogger(TopicCtrl.class);

	@Autowired
	ConfigService configService;
	@Autowired
	TopicService topicService;

	@RequestMapping(value = "/listRecTopic", method = RequestMapping.Method.ALL)
	public View listRecTopic() {

		List<TopicBo> list = configService.listRecTopic();

		TopicListBody body = new TopicListBody();
		body.setTopics(ViewTrans.transTopic(list));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/listTopic", method = RequestMapping.Method.ALL)
	public View listTopic(@RequestParam("pcon") String pcon) {
		int page = 0;
		if (StringUtils.isNotBlank(pcon)) {
			try {
				page = Integer.parseInt(pcon);
				page++;
			} catch (NumberFormatException e) {
				log.warn("pcon wrong format: " + pcon, e);
			}
		}


		List<TopicBo> hotList = null;
		List<String> excludeTids = null;
		if (page == 0) {
			hotList = configService.listHotTopic();
			if (CollectionUtils.isNotEmpty(hotList)) {
				for (TopicBo bo : hotList) {
					excludeTids.add(bo.getId());
				}
			}
		}

		List<TopicBo> list = topicService.listTopic(page, excludeTids);

		TopicListBody body = new TopicListBody();
		body.setHotTopics(ViewTrans.transTopic(hotList));
		body.setTopics(ViewTrans.transTopic(list));
		body.setPcon(page + "");

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

}

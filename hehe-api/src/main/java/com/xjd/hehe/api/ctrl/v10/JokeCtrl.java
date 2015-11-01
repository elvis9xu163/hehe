package com.xjd.hehe.api.ctrl.v10;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.cmpt.RequestContext;
import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.JidBody;
import com.xjd.hehe.api.view.body.JokeBody;
import com.xjd.hehe.api.view.body.JokeListBody;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.JokeBo;
import com.xjd.hehe.biz.service.JokeService;
import com.xjd.hehe.utl.ValidUtil;
import com.xjd.hehe.utl.exception.BizException;
import com.xjd.hehe.utl.respcode.RespCode;
import com.xjd.nhs.annotation.RequestMapping;
import com.xjd.nhs.annotation.RequestParam;

/**
 * @author elvis.xu
 * @since 2015-10-29 21:14
 */
@Controller
@RequestMapping("/api/10")
public class JokeCtrl {
	public static Logger log = LoggerFactory.getLogger(JokeCtrl.class);

	@Autowired
	JokeService jokeService;

	// FIXME 暂用listNewJoke
	@RequestMapping(value = "/listHotJoke", method = RequestMapping.Method.ALL)
	public View listHotJoke(@RequestParam("pcon") String pcon) {
		return listNewJoke(pcon);
	}

	// FIXME 暂用listNewJoke
	@RequestMapping(value = "/listTextJoke", method = RequestMapping.Method.ALL)
	public View listTextJoke(@RequestParam("pcon") String pcon) {
		return listNewJoke(pcon);
	}

	// FIXME 暂用listNewJoke
	@RequestMapping(value = "/listTopicJoke", method = RequestMapping.Method.ALL)
	public View listTopicJoke(@RequestParam("pcon") String pcon) {
		return listNewJoke(pcon);
	}

	@RequestMapping(value = "/listNewJoke", method = RequestMapping.Method.ALL)
	public View listNewJoke(@RequestParam("pcon") String pcon) {
		Long time = null;
		if (StringUtils.isNotBlank(pcon)) {
			try {
				time = Long.parseLong(pcon);
			} catch (NumberFormatException e) {
				log.warn("pcon wrong format: " + pcon, e);
			}
		}

		Date date = time == null ? null : new Date(time);


		List<JokeBo> boList = jokeService.listNewJoke(date);


		JokeListBody body = new JokeListBody();
		body.setJokes(ViewTrans.transJoke(boList));
		body.setPcon(CollectionUtils.isEmpty(boList) ? null : boList.get(boList.size() - 1).getCtime().getTime() + "");

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/getJoke", method = RequestMapping.Method.ALL)
	public View getJoke(@RequestParam("jid") String jid) {
		ValidUtil.check(ValidUtil.OID, jid);


		JokeBo bo = jokeService.getJoke(jid);

		// FIXME Guard

		JokeBody body = new JokeBody();
		body.setJoke(ViewTrans.trans(bo));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/addJoke", method = RequestMapping.Method.POST)
	public View addJoke(@RequestParam("txt") String txt, @RequestParam("url") String url,
						@RequestParam("pjid") String pjid, @RequestParam("topics") String topics) {
		txt = StringUtils.trimToNull(txt);
		url = StringUtils.trimToNull(url);
		pjid = StringUtils.trimToNull(pjid);
		List<String> topicList = new LinkedList<>();

		if (txt == null && url == null) {
			throw new BizException(RespCode.RES_0120);
		}
		if (StringUtils.isNotBlank(topics)) {
			String[] topicArray = topics.split(",");
			for (String topic : topicArray) {
				topic = StringUtils.trimToNull(topic);
				if (topic != null) {
					topicList.add(topic);
				}
			}
		}


		JokeBo bo = jokeService.addJoke(RequestContext.getUserId(), txt, url, pjid, topicList);

		// FIXME Guard

		JidBody body = new JidBody();
		body.setJid(bo.getId());

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

}

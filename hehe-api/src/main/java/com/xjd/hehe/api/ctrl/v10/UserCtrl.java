package com.xjd.hehe.api.ctrl.v10;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.bpg.Guards;
import com.xjd.hehe.api.cmpt.RequestContext;
import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.JokeListBody;
import com.xjd.hehe.api.view.body.TokenBody;
import com.xjd.hehe.api.view.body.TopicListBody;
import com.xjd.hehe.api.view.body.UserBody;
import com.xjd.hehe.api.view.vo.JokeVo;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.JokeBo;
import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.bo.UserBo;
import com.xjd.hehe.biz.service.UserService;
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
public class UserCtrl {
	public static Logger log = LoggerFactory.getLogger(UserCtrl.class);

	@Autowired
	UserService userService;

	@RequestMapping(value = "/signin", method = RequestMapping.Method.POST)
	public View signin(@RequestParam("uname") String uname, @RequestParam("pwd") String pwd) {
		if (StringUtils.isNotBlank(uname)) {
			if (StringUtils.isBlank(pwd)) {
				throw new BizException(RespCode.RES_0012, new Object[]{"密码"});
			}
			// 手机号或邮箱
			ValidUtil.check(ValidUtil.UNAME, uname);
		}

		TokenBo tokenBo = userService.login(uname, pwd, RequestContext.getEndId());

		TokenBody body = new TokenBody();
		body.setToken(tokenBo.getId());
		body.setSalt(tokenBo.getSalt());

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/signup", method = RequestMapping.Method.POST)
	public View signup(@RequestParam("uname") String uname, @RequestParam("pwd") String pwd) {
		ValidUtil.check(ValidUtil.UNAME, uname);
		ValidUtil.check(ValidUtil.PWD, pwd);

		userService.regist(uname, pwd, RequestContext.getUserId());

		View view = ViewUtil.defaultView();
		return view;
	}

	@RequestMapping("/getUser")
	public View getUser() {

		UserBo userBo = userService.getUser(RequestContext.getUserId());

		UserBody body = new UserBody();
		body.setUser(ViewTrans.trans(userBo));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/modifyUser", method = RequestMapping.Method.POST)
	public View modifyUser(@RequestParam("nick") String nick, @RequestParam("avatar") String avatar) {
		ValidUtil.check(ValidUtil.NICK, nick);

		userService.modifyUser(RequestContext.getUserId(), nick, avatar);

		View view = ViewUtil.defaultView();
		return view;
	}

	@RequestMapping(value = "/listFollowTopic", method = RequestMapping.Method.ALL)
	public View listFollowTopic() {

		List<TopicBo> topicBoList = userService.listFollowTopic(RequestContext.getUserId());

		TopicListBody body = new TopicListBody();
		body.setTopics(ViewTrans.transTopic(topicBoList));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/listFavorJoke", method = RequestMapping.Method.ALL)
	public View listFavorJoke(@RequestParam("pcon") String pcon) {
		int page = 0;
		if (StringUtils.isNotBlank(pcon)) {
			try {
				page = Integer.parseInt(pcon);
				page++;
			} catch (NumberFormatException e) {
				log.warn("pcon wrong format: {}", pcon);
			}
		}

		List<JokeBo> boList = userService.listFavorJoke(RequestContext.getUserId(), page);

		List<JokeVo> voList = ViewTrans.transJoke(boList);

		// FIXME 加强Guard
		if (CollectionUtils.isNotEmpty(voList)) {
			for (JokeVo vo : voList) {
				Guards.guard(voList, "listFollowTopic");
			}
		}

		JokeListBody body = new JokeListBody();
		body.setJokes(voList);
		body.setPcon(page + "");

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}
}

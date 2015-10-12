package com.xjd.hehe.dal.api.ctrlr.v10;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.dal.api.req.buddy.FollowUserReq;
import com.xjd.hehe.dal.api.req.buddy.GetUserInfoReq;
import com.xjd.hehe.dal.api.req.buddy.ListFansReq;
import com.xjd.hehe.dal.api.req.buddy.ListIdolReq;
import com.xjd.hehe.dal.api.req.buddy.ListMsgReq;
import com.xjd.hehe.dal.api.req.buddy.ModifyMoodReq;
import com.xjd.hehe.dal.api.req.buddy.ModifySpaceBgReq;
import com.xjd.hehe.dal.api.util.RequestContext;
import com.xjd.hehe.dal.api.view.View;
import com.xjd.hehe.dal.api.view.ViewUtil;
import com.xjd.hehe.dal.api.view.body.BuddyBody;
import com.xjd.hehe.dal.api.view.body.BuddyListBody;
import com.xjd.hehe.dal.api.view.body.MessageListBody;
import com.xjd.hehe.dal.api.view.vo.BuddyVo;
import com.xjd.hehe.dal.api.view.vo.MessageVo;
import com.xjd.hehe.dal.api.view.vo.trans.VoTrans;
import com.xjd.hehe.dal.biz.bo.BuddyBo;
import com.xjd.hehe.dal.biz.bo.FollowBuddyBo;
import com.xjd.hehe.dal.biz.bo.MessageBo;
import com.xjd.hehe.dal.biz.service.BuddyService;
import com.jkys.social.util.ValidUtil;
import com.xjd.bpg.Guards;
import com.xjd.nhs.annotation.RequestBody;
import com.xjd.nhs.annotation.RequestMapping;

@Controller
@RequestMapping("/api/10")
public class BuddyCtrl {

	@Autowired
	BuddyService buddyService;

//	@Profiled
	@RequestMapping("/getUserInfo")
	public View getUserInfo(@RequestBody GetUserInfoReq req) {
		ValidUtil.valid(req);

		String targetId = StringUtils.isBlank(req.getBuddyId()) ? RequestContext.getUserBuddyId() : req.getBuddyId();

		BuddyBo bo = buddyService.getUserInfo(RequestContext.getUserBuddyId(), targetId);

		BuddyVo vo = VoTrans.transBuddy(bo);
		Guards.guard(vo, "getUserInfo");

		BuddyBody body = new BuddyBody();
		body.setBuddy(vo);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listIdol")
	public View listIdol(@RequestBody ListIdolReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<FollowBuddyBo> boList = buddyService.listIdol(RequestContext.getUserBuddyId(), req.getBuddyId(), baseDate,
				req.getCount());

		List<BuddyVo> voList = VoTrans.transFollowBuddy(boList);
		Guards.guard(voList, "listIdol");

		BuddyListBody body = new BuddyListBody();
		body.setBuddyList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listFans")
	public View listFans(@RequestBody ListFansReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<FollowBuddyBo> boList = buddyService.listFans(RequestContext.getUserBuddyId(), req.getBuddyId(), baseDate,
				req.getCount());

		List<BuddyVo> voList = VoTrans.transFollowBuddy(boList);
		Guards.guard(voList, "listIdol");

		BuddyListBody body = new BuddyListBody();
		body.setBuddyList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/followUser")
	public View followUser(@RequestBody FollowUserReq req) {
		ValidUtil.valid(req);

		buddyService.followUser(RequestContext.checkAndGetUserBuddyId(), req.getBuddyId(), req.getFollow());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/modifyMood")
	public View modifyMood(@RequestBody ModifyMoodReq req) {
		ValidUtil.valid(req);

		buddyService.modifyMood(RequestContext.checkAndGetUserBuddyId(), req.getMood());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/modifySpaceBg")
	public View modifySpaceBg(@RequestBody ModifySpaceBgReq req) {
		ValidUtil.valid(req);

		buddyService.modifySpaceBg(RequestContext.checkAndGetUserBuddyId(), req.getBgImgUrl());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/listMsg")
	public View listMsg(@RequestBody ListMsgReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<MessageBo> boList = buddyService.listMsg(RequestContext.getUserBuddyId(), req.getqType(), baseDate, req.getCount());

		List<MessageVo> voList = VoTrans.transMessage(boList);

		MessageListBody body = new MessageListBody();
		body.setMsgList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

}

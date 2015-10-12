package com.xjd.hehe.dal.api.ctrlr.v10;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.dal.api.req.dynamic.AddCommentReq;
import com.xjd.hehe.dal.api.req.dynamic.AddDynamicReq;
import com.xjd.hehe.dal.api.req.dynamic.AddHitsReq;
import com.xjd.hehe.dal.api.req.dynamic.AddReplyReq;
import com.xjd.hehe.dal.api.req.dynamic.GetDynamicReq;
import com.xjd.hehe.dal.api.req.dynamic.LikeReq;
import com.xjd.hehe.dal.api.req.dynamic.ListCommentReq;
import com.xjd.hehe.dal.api.req.dynamic.ListDynamicForNewReq;
import com.xjd.hehe.dal.api.req.dynamic.ListDynamicForRecommendReq;
import com.xjd.hehe.dal.api.req.dynamic.ListDynamicForUserReq;
import com.xjd.hehe.dal.api.req.dynamic.ListLikerReq;
import com.xjd.hehe.dal.api.req.dynamic.RemoveCommentReq;
import com.xjd.hehe.dal.api.req.dynamic.RemoveDynamicReq;
import com.xjd.hehe.dal.api.req.dynamic.RemoveReplyReq;
import com.xjd.hehe.dal.api.util.RequestContext;
import com.xjd.hehe.dal.api.view.View;
import com.xjd.hehe.dal.api.view.ViewUtil;
import com.xjd.hehe.dal.api.view.body.BuddyListBody;
import com.xjd.hehe.dal.api.view.body.CommentBody;
import com.xjd.hehe.dal.api.view.body.CommentListBody;
import com.xjd.hehe.dal.api.view.body.DynamicBody;
import com.xjd.hehe.dal.api.view.body.DynamicListBody;
import com.xjd.hehe.dal.api.view.body.ReplyBody;
import com.xjd.hehe.dal.api.view.vo.BuddyVo;
import com.xjd.hehe.dal.api.view.vo.CommentVo;
import com.xjd.hehe.dal.api.view.vo.DynamicVo;
import com.xjd.hehe.dal.api.view.vo.ReplyVo;
import com.xjd.hehe.dal.api.view.vo.trans.VoTrans;
import com.xjd.hehe.dal.biz.bo.CommentBo;
import com.xjd.hehe.dal.biz.bo.DynamicBo;
import com.xjd.hehe.dal.biz.bo.LikeBo;
import com.xjd.hehe.dal.biz.bo.ReplyBo;
import com.xjd.hehe.dal.biz.service.DynamicService;
import com.jkys.social.util.ValidUtil;
import com.xjd.bpg.Guards;
import com.xjd.nhs.annotation.RequestBody;
import com.xjd.nhs.annotation.RequestMapping;

@Controller
@RequestMapping("/api/10")
public class DynamicCtrl {

	@Autowired
	DynamicService dynamicService;

//	@Profiled
	@RequestMapping("/addDynamic")
	public View addDynamic(@RequestBody AddDynamicReq req) {
		ValidUtil.valid(req);
		
		if (req.getPicUrls() != null) {
			List<String> images = new LinkedList<String>();
			for (int i = req.getPicUrls().size() - 1; i >= 0; i--) {
				String path = StringUtils.trimToNull(req.getPicUrls().get(i));
				if (path != null) {
					images.add(path);
				}
			}
			req.setPicUrls(images);
		}

		DynamicBo bo = dynamicService.addDynamic(RequestContext.checkAndGetUserBuddyId(), req.getCircleId(), req.getContent(),
				req.getPicUrls());

		DynamicVo vo = VoTrans.transDynamic(bo);
		Guards.guard(vo);

		DynamicBody body = new DynamicBody();
		body.setDynamic(vo);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listDynamicForRecommend")
	public View listDynamicForRecommend(@RequestBody ListDynamicForRecommendReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());
		List<DynamicBo> boList = dynamicService
				.listDynamicForRecommend(RequestContext.getUserBuddyId(), baseDate, req.getCount());
		List<DynamicVo> voList = VoTrans.transDynamic(boList);
		Guards.guard(voList, "listDynamicForRecommend");

		DynamicListBody body = new DynamicListBody();
		body.setDynamicList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

//	@Profiled
	@RequestMapping("/listDynamicForNew")
	public View listDynamicForNew(@RequestBody ListDynamicForNewReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<DynamicBo> boList = dynamicService.listDynamicForNew(RequestContext.checkAndGetUserBuddyId(), baseDate,
				req.getCount());

		List<DynamicVo> voList = VoTrans.transDynamic(boList);
		Guards.guard(voList);

		DynamicListBody body = new DynamicListBody();
		body.setDynamicList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listDynamicForUser")
	public View listDynamicForUser(@RequestBody ListDynamicForUserReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<DynamicBo> boList = dynamicService.listDynamicForUser(RequestContext.getUserBuddyId(), req.getBuddyId(), baseDate,
				req.getCount());

		List<DynamicVo> voList = VoTrans.transDynamic(boList);
		Guards.guard(voList);

		DynamicListBody body = new DynamicListBody();
		body.setDynamicList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/getDynamic")
	public View getDynamic(@RequestBody GetDynamicReq req) {
		ValidUtil.valid(req);

		DynamicBo bo = dynamicService.getDynamic(RequestContext.getUserBuddyId(), req.getDynamicId());

		DynamicVo vo = VoTrans.transDynamic(bo);
		Guards.guard(vo);

		DynamicBody body = new DynamicBody();
		body.setDynamic(vo);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listComment")
	public View listComment(@RequestBody ListCommentReq req) {
		ValidUtil.valid(req);
		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<CommentBo> boList = dynamicService.listComment(req.getDynamicId(), baseDate, req.getCount());

		List<CommentVo> voList = VoTrans.transComment(boList);
		Guards.guard(voList);

		CommentListBody body = new CommentListBody();
		body.setCommentList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/addComment")
	public View addComment(@RequestBody AddCommentReq req) {
		ValidUtil.valid(req);

		CommentBo bo = dynamicService.addComment(RequestContext.checkAndGetUserBuddyId(), req.getDynamicId(), req.getContent());

		CommentVo vo = VoTrans.transComment(bo);
		Guards.guard(vo);

		CommentBody body = new CommentBody();
		body.setComment(vo);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/addReply")
	public View addReply(@RequestBody AddReplyReq req) {
		ValidUtil.valid(req);

		ReplyBo bo = dynamicService.addReply(RequestContext.checkAndGetUserBuddyId(), req.getDynamicId(), req.getCommentId(),
				req.getTargetBuddyId(), req.getContent());

		ReplyVo vo = VoTrans.transReply(bo);
		Guards.guard(vo);

		ReplyBody body = new ReplyBody();
		body.setReply(vo);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listLiker")
	public View listLiker(@RequestBody ListLikerReq req) {
		ValidUtil.valid(req);

		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		List<LikeBo> boList = dynamicService.listLiker(req.getDynamicId(), baseDate, req.getCount());

		List<BuddyVo> voList = VoTrans.transLike(boList);
		Guards.guard(voList);

		BuddyListBody body = new BuddyListBody();
		body.setBuddyList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/like")
	public View like(@RequestBody LikeReq req) {
		ValidUtil.valid(req);

		dynamicService.like(RequestContext.checkAndGetUserBuddyId(), req.getDynamicId(), req.getLike());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/addHits")
	public View addHits(@RequestBody AddHitsReq req) {
		ValidUtil.valid(req);

		dynamicService.addHits(req.getDynamicId());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/removeDynamic")
	public View removeDynamic(@RequestBody RemoveDynamicReq req) {
		ValidUtil.valid(req);

		dynamicService.removeDynamic(RequestContext.checkAndGetUserBuddyId(), req.getDynamicId());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/removeComment")
	public View removeComment(@RequestBody RemoveCommentReq req) {
		ValidUtil.valid(req);

		dynamicService.removeComment(RequestContext.checkAndGetUserBuddyId(), req.getDynamicId(), req.getCommentId());

		View view = ViewUtil.defaultView();

		return view;
	}

//	@Profiled
	@RequestMapping("/removeReply")
	public View removeReply(@RequestBody RemoveReplyReq req) {
		ValidUtil.valid(req);

		dynamicService.removeReply(RequestContext.checkAndGetUserBuddyId(), req.getDynamicId(), req.getCommentId(),
				req.getReplyId());

		View view = ViewUtil.defaultView();

		return view;
	}
}

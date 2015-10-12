package com.xjd.hehe.dal.api.ctrlr.v10;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.dal.api.req.circle.ListExpDoctorReq;
import com.xjd.hehe.dal.api.req.circle.ListExpPatientReq;
import com.xjd.hehe.dal.api.view.View;
import com.xjd.hehe.dal.api.view.ViewUtil;
import com.xjd.hehe.dal.api.view.body.BuddyListBody;
import com.xjd.hehe.dal.api.view.vo.BuddyVo;
import com.xjd.hehe.dal.api.view.vo.trans.VoTrans;
import com.xjd.hehe.dal.biz.bo.CircleBo;
import com.xjd.hehe.dal.biz.service.CircleService;
import com.jkys.social.util.ValidUtil;
import com.xjd.bpg.Guards;
import com.xjd.nhs.annotation.RequestBody;
import com.xjd.nhs.annotation.RequestMapping;

@Controller
@RequestMapping("/api/10")
public class CircleCtrl {

	@Autowired
	CircleService circleService;

//	@Profiled
	@RequestMapping("/listExpPatient")
	public View listExpPatient(@RequestBody ListExpPatientReq req) {
		ValidUtil.valid(req);

		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		CircleBo circle = circleService.listExpPatient(baseDate, req.getCount());

		List<BuddyVo> voList = VoTrans.transFollowBuddy(circle == null ? null : circle.getFollowBuddyList());
		Guards.guard(voList, "listExpPatient");
		
		BuddyListBody body = new BuddyListBody();
		body.setBuddyList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/listExpDoctor")
	public View listExpDoctor(@RequestBody ListExpDoctorReq req) {
		ValidUtil.valid(req);

		Date baseDate = req.getBaseTime() == null ? null : new Date(req.getBaseTime());

		CircleBo circle = circleService.listExpDoctor(baseDate, req.getCount());

		List<BuddyVo> voList = VoTrans.transFollowBuddy(circle == null ? null : circle.getFollowBuddyList());
		Guards.guard(voList, "listExpDoctor");

		BuddyListBody body = new BuddyListBody();
		body.setBuddyList(voList);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}
}

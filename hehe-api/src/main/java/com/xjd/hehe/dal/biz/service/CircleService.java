package com.xjd.hehe.dal.biz.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.biz.bo.BuddyBo;
import com.xjd.hehe.dal.biz.bo.CircleBo;
import com.xjd.hehe.dal.biz.bo.CircleCategoryBo;
import com.xjd.hehe.dal.biz.bo.FollowBuddyBo;
import com.xjd.hehe.dal.biz.bo.FollowCircleBo;
import com.xjd.hehe.dal.biz.bo.trans.BeanTrans;
import com.xjd.hehe.dal.dao.mongo.CircleCategoryDAO;
import com.xjd.hehe.dal.dao.mongo.CircleDAO;
import com.xjd.hehe.dal.entity.Circle;
import com.xjd.hehe.dal.entity.CircleCategory;
import com.jkys.social.util.AppContext;

/**
 * @author elvis.xu
 * @since 2015-9-7
 */
@Service
public class CircleService {
	public static final String EXP_PATIENT_ID = "circle.exp.patient.id";
	public static final String EXP_DOCTOR_ID = "circle.exp.doctor.id";

	@Autowired
	CircleDAO circleDAO;
	@Autowired
	CircleCategoryDAO circleCategoryDAO;
	@Autowired
	BuddyService buddyService;

	@Profiled
	public CircleBo getCircleById(String circleId) {
		Circle entity = circleDAO.getById(circleId);
		return BeanTrans.transCircle(entity);
	}

	@Profiled
	public CircleCategoryBo getCircleCategoryById(String categoryId) {
		if (StringUtils.isBlank(categoryId)) return null;
		CircleCategory entity = circleCategoryDAO.getById(categoryId);
		return BeanTrans.transCircleCategory(entity);
	}

	@Profiled
	public void consummateCircle(CircleBo bo, boolean owner, boolean category, boolean followBuddy) {
		if (bo == null) return;
		if (owner && StringUtils.isNotBlank(bo.getOwnerId())) {
			BuddyBo buddy = buddyService.getBuddyByBuddyId(bo.getOwnerId());
			buddyService.consummateBuddy(buddy, true, false, false, false);
			bo.setOwner(buddy);
		}
		if (category && StringUtils.isNotBlank(bo.getCategoryId())) {
			CircleCategoryBo ccb = getCircleCategoryById(bo.getCategoryId());
			consummateCircleCategory(ccb, false);
			bo.setCategory(ccb);
		}
		if (followBuddy && bo.getFollowBuddyList() != null) {
			List<String> idList = new LinkedList<String>();
			for (FollowBuddyBo fb : bo.getFollowBuddyList()) {
				idList.add(fb.getBuddyId());
			}
			buddyService.loadBuddyByIdList(idList);
			for (FollowBuddyBo fb : bo.getFollowBuddyList()) {
				buddyService.consummateFollowBuddy(fb, true);
			}
		}
	}

	@Profiled
	public void consummateFollowCircle(FollowCircleBo bo, boolean circle) {
		if (bo == null) return;
		if (circle && StringUtils.isNotBlank(bo.getCircleId())) {
			CircleBo cb = getCircleById(bo.getCircleId());
			consummateCircle(cb, false, false, false);
			bo.setCircle(cb);
		}
	}

	@Profiled
	public void consummateCircleCategory(CircleCategoryBo bo, boolean creator) {
		if (bo == null) return;
		if (creator) {
			BuddyBo buddy = buddyService.getBuddyByBuddyId(bo.getCreatorId());
			buddyService.consummateBuddy(buddy, true, false, false, false);
			bo.setCreator(buddy);
		}
	}

	public CircleBo listExpPatient(Date baseDate, Integer count) {
		Circle circle = circleDAO.queryFollowBuddyOrderByCreatedTime(AppContext.getProperty(EXP_PATIENT_ID), baseDate, count);
		CircleBo circleBo = BeanTrans.transCircle(circle);
		consummateCircle(circleBo, false, false, true);
		return circleBo;
	}

	public CircleBo listExpDoctor(Date baseDate, Integer count) {
		Circle circle = circleDAO.queryFollowBuddyOrderByCreatedTime(AppContext.getProperty(EXP_DOCTOR_ID), baseDate, count);
		CircleBo circleBo = BeanTrans.transCircle(circle);
		consummateCircle(circleBo, false, false, true);
		return circleBo;
	}
}

package com.xjd.hehe.dal.biz.service;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.biz.bo.BuddyBo;
import com.xjd.hehe.dal.biz.bo.DynamicBo;
import com.xjd.hehe.dal.biz.bo.FollowBuddyBo;
import com.xjd.hehe.dal.biz.bo.FollowCircleBo;
import com.xjd.hehe.dal.biz.bo.HealthFileSetting;
import com.xjd.hehe.dal.biz.bo.MessageBo;
import com.xjd.hehe.dal.biz.bo.trans.BeanTrans;
import com.xjd.hehe.dal.biz.service.CacheService.GetResult;
import com.xjd.hehe.dal.dao.mongo.BuddyDAO;
import com.xjd.hehe.dal.dao.mongo.FollowDynamicDAO;
import com.xjd.hehe.dal.dao.mongo.MessageDAO;
import com.xjd.hehe.dal.dao.mysql.UserDAO;
import com.xjd.hehe.dal.entity.Buddy;
import com.xjd.hehe.dal.entity.Message;
import com.xjd.hehe.dal.entity.User;
import com.jkys.social.util.AppContext;
import com.jkys.social.util.enums.BoolEnum;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;

/**
 * @author elvis.xu
 * @since 2015-9-6
 */
@Service
public class BuddyService {
	private static Logger log = LoggerFactory.getLogger(BuddyService.class);
	@Autowired
	BuddyDAO buddyDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	FollowDynamicDAO followDynamicDAO;
	@Autowired
	MessageDAO messageDAO;
	@Autowired
	CircleService circleService;
	@Autowired
	AuthService authService;
	@Autowired
	DynamicService dynamicService;
	@Autowired
	CacheService cacheService;
	@Autowired
	BasicService basicService;

	@Profiled
	public BuddyBo getBuddyByBuddyId(String buddyId) {
		if (StringUtils.isBlank(buddyId)) return null;
		Buddy buddy = buddyDAO.getById(buddyId);
		BuddyBo bo = BeanTrans.transBuddy(buddy);
		return bo;
	}

	@Profiled
	public List<Buddy> loadBuddyByIdList(List<String> buddyIdList) {
		if (CollectionUtils.isEmpty(buddyIdList)) return Collections.emptyList();
		GetResult<Buddy, String> rt = cacheService.get(CacheService.CACHE_ENTITY, buddyIdList);

		List<Buddy> allList = new LinkedList<Buddy>();

		if (CollectionUtils.isNotEmpty(rt.getNoHitIdList())) {
			List<Buddy> loadList = buddyDAO.queryByIds(rt.getNoHitIdList());
			for (Buddy u : loadList) {
				cacheService.put(CacheService.CACHE_ENTITY, u.getId(), u);
			}
			allList.addAll(loadList);
		}

		allList.addAll(rt.getResultList());

		if (!allList.isEmpty()) {
			List<Integer> idList = new LinkedList<Integer>();
			for (Buddy b : allList) {
				idList.add(b.getUserId());
			}
			authService.loadUserByIdList(idList);
		}

		return allList;
	}

	@Profiled
	public void consummateBuddy(BuddyBo bo, boolean userInfo, boolean followCircle, boolean following, boolean follower) {
		if (bo == null) return;

		if (userInfo && bo.getUserId() != null && bo.getUserId() > 0) {
			User user = authService.getUserById(bo.getUserId());
			BeanTrans.transBuddy(user, bo);
			HealthFileSetting setting = basicService.getHealthFileSetting();
			if (setting != null && setting.getDiabetes_type() != null) {
				for (HealthFileSetting.Item item : setting.getDiabetes_type()) {
					if (StringUtils.equals(item.getCode(), bo.getDiabetesType() == null ? null : bo.getDiabetesType().byteValue()
							+ "")) {
						bo.setDiabetesTypeName(item.getContent());
						break;
					}
				}
			}
		}

		if (followCircle && CollectionUtils.isNotEmpty(bo.getFollowCircleList())) { // OPT
			for (FollowCircleBo fc : bo.getFollowCircleList()) {
				circleService.consummateFollowCircle(fc, true);
			}
		}

		if (following && CollectionUtils.isNotEmpty(bo.getFollowingList())) { // OPT
			for (FollowBuddyBo fb : bo.getFollowingList()) {
				consummateFollowBuddy(fb, true);
			}
		}

		if (follower && CollectionUtils.isNotEmpty(bo.getFollowerList())) { // OPT
			for (FollowBuddyBo fb : bo.getFollowerList()) {
				consummateFollowBuddy(fb, true);
			}
		}
	}

	@Profiled
	public void consummateFollowBuddy(FollowBuddyBo bo, boolean buddy) {
		if (bo == null) return;
		if (buddy && StringUtils.isNotBlank(bo.getBuddyId())) {
			BuddyBo bb = getBuddyByBuddyId(bo.getBuddyId());
			consummateBuddy(bb, true, false, false, false);
			bo.setBuddy(bb);
		}
	}

	public BuddyBo getUserInfo(String buddyId, String targetId) {
		BuddyBo bo = getBuddyByBuddyId(targetId);
		consummateBuddy(bo, true, false, false, false);
		if (bo != null) {
			boolean idol = false;
			if (StringUtils.isNotBlank(buddyId) && CollectionUtils.isNotEmpty(bo.getFollowerList())) {
				for (FollowBuddyBo fb : bo.getFollowerList()) {
					if (StringUtils.equals(buddyId, fb.getBuddyId())) {
						idol = true;
						break;
					}
				}
			}
			bo.setIdolFlag(idol ? BoolEnum.YES.getCode() : BoolEnum.NO.getCode());

			bo.setIdolCount(bo.getFollowingList() == null ? 0 : bo.getFollowingList().size());
			bo.setFansCount(bo.getFollowerList() == null ? 0 : bo.getFollowerList().size());
			if (StringUtils.equals(buddyId, targetId)) {
				bo.setMsgCount(messageDAO.countForUser(buddyId));
			} else {
				bo.setMsgCount(0L);
			}
		}
		return bo;
	}

	public List<FollowBuddyBo> listIdol(String buddyId, String targetId, Date baseDate, Integer count) {
		BuddyBo bo = getBuddyByBuddyId(targetId);
		if (bo != null && CollectionUtils.isNotEmpty(bo.getFollowingList())) {
			// 筛选
			List<FollowBuddyBo> boList = new LinkedList<FollowBuddyBo>();
			for (int i = bo.getFollowingList().size() - 1; i >= 0 && count > 0; i--) {
				FollowBuddyBo fb = bo.getFollowingList().get(i);
				if (baseDate == null || (fb.getCreatedTime() != null && fb.getCreatedTime().before(baseDate))) {
					boList.add(fb);
					count--;
				}
			}

			bo.setFollowingList(boList);

			// load
			List<String> idList = new LinkedList<String>();
			for (FollowBuddyBo fb : boList) {
				idList.add(fb.getBuddyId());
			}
			loadBuddyByIdList(idList);

			// 处理
			consummateBuddy(bo, false, false, true, false);

			// 关注
			for (FollowBuddyBo fb : bo.getFollowingList()) {
				if (fb.getBuddy() == null) continue;
				boolean idol = false;
				if (StringUtils.isBlank(buddyId)) {
					idol = false;
				} else if (StringUtils.equals(buddyId, targetId)) {
					idol = true;
				} else {
					if (CollectionUtils.isNotEmpty(fb.getBuddy().getFollowerList())) {
						for (FollowBuddyBo fb2 : fb.getBuddy().getFollowerList()) {
							if (StringUtils.equals(buddyId, fb2.getBuddyId())) {
								idol = true;
								break;
							}
						}
					}
				}
				fb.getBuddy().setIdolFlag(idol ? BoolEnum.YES.getCode() : BoolEnum.NO.getCode());
			}
		}
		return bo == null ? null : bo.getFollowingList();
	}

	public List<FollowBuddyBo> listFans(String buddyId, String targetId, Date baseDate, Integer count) {
		BuddyBo bo = getBuddyByBuddyId(targetId);
		if (bo != null && CollectionUtils.isNotEmpty(bo.getFollowerList())) {
			// 筛选
			List<FollowBuddyBo> boList = new LinkedList<FollowBuddyBo>();
			for (int i = bo.getFollowerList().size() - 1; i >= 0 && count > 0; i--) {
				FollowBuddyBo fb = bo.getFollowerList().get(i);
				if (baseDate == null || (baseDate != null && fb.getCreatedTime() != null && fb.getCreatedTime().before(baseDate))) {
					boList.add(fb);
					count--;
				}
			}

			bo.setFollowerList(boList);

			// load
			List<String> idList = new LinkedList<String>();
			for (FollowBuddyBo fb : boList) {
				idList.add(fb.getBuddyId());
			}
			loadBuddyByIdList(idList);

			// 处理
			consummateBuddy(bo, false, false, false, true);

			// 关注
			for (FollowBuddyBo fb : bo.getFollowerList()) {
				if (fb.getBuddy() == null) continue;
				boolean idol = false;
				if (StringUtils.isBlank(buddyId)) {
					idol = false;
				} else {
					if (CollectionUtils.isNotEmpty(fb.getBuddy().getFollowerList())) {
						for (FollowBuddyBo fb2 : fb.getBuddy().getFollowerList()) {
							if (StringUtils.equals(buddyId, fb2.getBuddyId())) {
								idol = true;
								break;
							}
						}
					}
				}
				fb.getBuddy().setIdolFlag(idol ? BoolEnum.YES.getCode() : BoolEnum.NO.getCode());
			}
		}
		return bo == null ? null : bo.getFollowerList();
	}

	public void followUser(String buddyId, String targetId, Byte follow) {
		if (buddyId.equals(targetId)) {
			return; // 自己不需要关注自己
		}

		BuddyBo buddy = getBuddyByBuddyId(buddyId);
		boolean alreadyFollow = false;
		if (buddy.getFollowingList() != null) {
			for (FollowBuddyBo fb : buddy.getFollowingList()) {
				if (StringUtils.equals(fb.getBuddyId(), targetId)) {
					alreadyFollow = true;
					break;
				}
			}
		}
		if (follow == 0) {
			buddyDAO.removeFollow(buddyId, targetId);
			buddyDAO.removeFollower(targetId, buddyId);

		} else if (follow == 1 && !alreadyFollow) {
			if (StringUtils.isNotBlank(AppContext.getTangYouFollowTangYouUpLimit())) {
				int followingSize = buddy.getFollowingList() == null ? 0 : buddy.getFollowingList().size();
				if (followingSize >= Integer.parseInt(AppContext.getTangYouFollowTangYouUpLimit())) {
					log.warn("关注的糖友数量超限: buddyId={}, targetId={}, size={}", buddyId, targetId, followingSize);
					throw new BizException(RespCode.RES_9950);
				}
			}

			if (StringUtils.isNotBlank(AppContext.getTangYouByFollowTangYouUpLimit())) {
				BuddyBo targetBuddy = getBuddyByBuddyId(targetId);
				int followerSize = targetBuddy.getFollowerList() == null ? 0 : targetBuddy.getFollowerList().size();
				if (followerSize >= Integer.parseInt(AppContext.getTangYouByFollowTangYouUpLimit())) {
					log.warn("被关注的糖友数量超限: buddyId={}, targetId={}, size={}", buddyId, targetId, followerSize);
					throw new BizException(RespCode.RES_9950);
				}
			}
			buddyDAO.addFollow(buddyId, targetId);
			buddyDAO.addFollower(targetId, buddyId);
		}
	}

	public void modifyMood(String buddyId, String mood) {
		buddyDAO.modifyMood(buddyId, mood);
	}

	public void modifySpaceBg(String buddyId, String bgImgUrl) {
		buddyDAO.modifySpaceBg(buddyId, bgImgUrl);
	}

	public List<MessageBo> listMsg(String buddyId, Byte qType, Date baseDate, Integer count) {
		List<Message> entityList = messageDAO.queryForUserByPage(buddyId, qType, baseDate, count);
		// List<String> unreadIdList = new LinkedList<String>();
		// if (entityList != null) {
		// for (Message entity : entityList) {
		// if (entity.getStatus() == null || entity.getStatus() == 0) {
		// unreadIdList.add(entity.getId());
		// }
		// }
		// }
		// if (!unreadIdList.isEmpty()) {
		// messageDAO.updateToRead(unreadIdList);
		// }
		// 全部未读消息设置为已读
		messageDAO.updateToReadForUser(buddyId);
		List<MessageBo> boList = BeanTrans.transMessage(entityList);
		if (boList != null) {
			List<String> buddyIdList = new LinkedList<String>();
			List<String> dynamicIdList = new LinkedList<String>();
			for (MessageBo bo : boList) {
				buddyIdList.add(bo.getCreatorId());
				dynamicIdList.add(bo.getTargetId());
			}
			loadBuddyByIdList(buddyIdList);
			dynamicService.loadDynamicByIdList(dynamicIdList, false, false, false);
			for (MessageBo bo : boList) {
				consummateMessage(bo, true, true);
			}
		}
		return boList;
	}

	@Profiled
	public void consummateMessage(MessageBo bo, boolean creator, boolean target) {
		if (bo == null) return;
		if (creator && StringUtils.isNotBlank(bo.getCreatorId()) && bo.getCreator() == null) {
			BuddyBo buddy = getBuddyByBuddyId(bo.getCreatorId());
			consummateBuddy(buddy, true, false, false, false);
			bo.setCreator(buddy);
		}

		if (target && StringUtils.isNotBlank(bo.getTargetId()) && bo.getTarget() == null) {
			if (Integer.valueOf(1).equals(bo.getTargetType())) { // 目前只有动态
				DynamicBo dynamicBo = dynamicService.getDynamicById(bo.getTargetId());
				if (dynamicBo != null && dynamicBo.getStatus() != null
						&& (dynamicBo.getStatus() == 2 || dynamicBo.getStatus() == 3)) {
					dynamicBo = null;
				}
				bo.setTarget(dynamicBo);
			}
		}
	}
}

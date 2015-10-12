package com.xjd.hehe.dal.biz.service;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.biz.bo.BuddyBo;
import com.xjd.hehe.dal.biz.bo.CircleBo;
import com.xjd.hehe.dal.biz.bo.CommentBo;
import com.xjd.hehe.dal.biz.bo.DynamicBo;
import com.xjd.hehe.dal.biz.bo.LikeBo;
import com.xjd.hehe.dal.biz.bo.ReplyBo;
import com.xjd.hehe.dal.biz.bo.trans.BeanTrans;
import com.xjd.hehe.dal.biz.service.CacheService.GetResult;
import com.xjd.hehe.dal.dao.mongo.BuddyDAO;
import com.xjd.hehe.dal.dao.mongo.DynamicDAO;
import com.xjd.hehe.dal.dao.mongo.FollowDynamicDAO;
import com.xjd.hehe.dal.dao.mongo.MessageDAO;
import com.xjd.hehe.dal.entity.Buddy;
import com.xjd.hehe.dal.entity.Comment;
import com.xjd.hehe.dal.entity.Dynamic;
import com.xjd.hehe.dal.entity.FollowDynamic;
import com.xjd.hehe.dal.entity.Like;
import com.xjd.hehe.dal.entity.Message;
import com.xjd.hehe.dal.entity.Message.Arg;
import com.xjd.hehe.dal.entity.Reply;
import com.jkys.social.util.AppContext;
import com.jkys.social.util.DateUtil;
import com.jkys.social.util.enums.BoolEnum;
import com.jkys.social.util.enums.CommentStatusEnum;
import com.jkys.social.util.enums.DynamicStatusEnum;
import com.jkys.social.util.enums.FollowDynamicStatusEnum;
import com.jkys.social.util.enums.LikeStatusEnum;
import com.jkys.social.util.enums.ReplyStatusEnum;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;

/**
 * “动态”服务
 * @author elvis.xu
 * @since 2015-9-6
 */
@Service
public class DynamicService {
	private static Logger log = LoggerFactory.getLogger(DynamicService.class);
	@Autowired
	DynamicDAO dynamicDAO;
	@Autowired
	BuddyDAO buddyDAO;
	@Autowired
	FollowDynamicDAO followDynamicDAO;
	@Autowired
	MessageDAO messageDAO;
	@Autowired
	BuddyService buddyService;
	@Autowired
	CircleService circleService;
	@Autowired
	WordMatchService wordMatchService;
	@Autowired
	PushService pushService;
	@Autowired
	AuthService authService;
	@Autowired
	ThreadPoolTaskExecutor executor;
	@Autowired
	CacheService cacheService;

	@Profiled
	public DynamicBo getDynamicById(String dynamicId) {
		if (StringUtils.isBlank(dynamicId)) return null;
		Dynamic entity = dynamicDAO.getById(dynamicId);
		DynamicBo bo = BeanTrans.transDynamic(entity);
		return bo;
	}

	@Profiled
	public void consummateDynamic(DynamicBo bo, boolean owner, boolean circle, boolean liker) {
		if (bo == null) return;

		if (owner && StringUtils.isNotBlank(bo.getOwnerId())) { // owner
			BuddyBo buddy = buddyService.getBuddyByBuddyId(bo.getOwnerId());
			buddyService.consummateBuddy(buddy, true, false, false, false);
			bo.setOwner(buddy);
		}

		if (circle && StringUtils.isNotBlank(bo.getCircleId())) { // circle
			CircleBo circleBo = circleService.getCircleById(bo.getCircleId());
			bo.setCircle(circleBo);
		}

		if (liker && CollectionUtils.isNotEmpty(bo.getLikeList())) { // liker
			for (LikeBo like : bo.getLikeList()) {
				if (StringUtils.isNotBlank(like.getOwnerId())) {
					BuddyBo buddy = buddyService.getBuddyByBuddyId(like.getOwnerId());
					buddyService.consummateBuddy(buddy, true, false, false, false);
					like.setOwner(buddy);
				}
			}
		}
	}

	@Profiled
	public void loadDynamicByIdList(List<String> idList, boolean owner, boolean circle, boolean liker) {
		if (CollectionUtils.isEmpty(idList)) return;
		GetResult<Dynamic, String> rt = cacheService.get(CacheService.CACHE_ENTITY, idList);

		List<Dynamic> allList = new LinkedList<Dynamic>();

		if (CollectionUtils.isNotEmpty(rt.getNoHitIdList())) {
			List<Dynamic> loadList = dynamicDAO.queryByIds(rt.getNoHitIdList());
			for (Dynamic u : loadList) {
				cacheService.put(CacheService.CACHE_ENTITY, u.getId(), u);
			}
			allList.addAll(loadList);
		}

		allList.addAll(rt.getResultList());

		loadDynamic(allList, owner, circle, liker);
	}

	@Profiled
	public void loadDynamic(List<Dynamic> list, boolean owner, boolean circle, boolean liker) {
		if (list == null || list.isEmpty()) return;

		List<String> buddyIdList = new LinkedList<String>();
		List<Integer> userIdList = new LinkedList<Integer>();

		for (Dynamic bo : list) {
			if (owner && StringUtils.isNotBlank(bo.getOwnerId())) { // owner
				buddyIdList.add(bo.getOwnerId());
			}
			if (liker && CollectionUtils.isNotEmpty(bo.getLikes())) { // liker
				int i = 0; // 只取前10个
				for (Like like : bo.getLikes()) {
					if (StringUtils.isNotBlank(like.getOwnerId())) {
						buddyIdList.add(like.getOwnerId());
						if ((++i) == 10) {
							break;
						}
					}
				}
			}
		}

		if (!buddyIdList.isEmpty()) {
			List<Buddy> buddyList = buddyService.loadBuddyByIdList(buddyIdList);
			if (CollectionUtils.isNotEmpty(buddyList)) {
				for (Buddy buddy : buddyList) {
					if (buddy.getUserId() != null) {
						userIdList.add(buddy.getUserId());
					}
				}
			}
		}

		if (!userIdList.isEmpty()) {
			authService.loadUserByIdList(userIdList);
		}
	}

	@Profiled
	public void processLikeFlag(DynamicBo bo, Dynamic entity, String buddyId) {
		if (bo == null) return;
		boolean like = false;
		if (StringUtils.isNotBlank(buddyId) && entity.getLikes() != null) {
			for (Like likeEntity : entity.getLikes()) {
				if (StringUtils.equals(buddyId, likeEntity.getOwnerId())) {
					like = true;
					break;
				}
			}
		}
		bo.setLikeFlag(like ? BoolEnum.YES.getCode() : BoolEnum.NO.getCode());
	}

	@Profiled
	public void processLikeFlag(DynamicBo bo, String buddyId) {
		if (bo == null) return;
		boolean like = false;
		if (StringUtils.isNotBlank(buddyId) && bo.getLikeList() != null) {
			for (LikeBo likeBo : bo.getLikeList()) {
				if (StringUtils.equals(buddyId, likeBo.getOwnerId())) {
					like = true;
					break;
				}
			}
		}
		bo.setLikeFlag(like ? BoolEnum.YES.getCode() : BoolEnum.NO.getCode());
	}

	@Profiled
	public void consummateComment(CommentBo bo, boolean owner, boolean reply) {
		if (owner && StringUtils.isNotBlank(bo.getOwnerId())) {
			BuddyBo buddy = buddyService.getBuddyByBuddyId(bo.getOwnerId());
			buddyService.consummateBuddy(buddy, true, false, false, false);
			bo.setOwner(buddy);
		}
		if (reply && CollectionUtils.isNotEmpty(bo.getReplyList())) {
			for (ReplyBo replyBo : bo.getReplyList()) {
				consummateReply(replyBo, true);
			}
		}
	}

	@Profiled
	public void loadComment(List<Comment> list, boolean owner, boolean reply) {
		if (list == null || list.isEmpty()) return;

		List<String> buddyIdList = new LinkedList<String>();

		for (Comment c : list) {
			if (owner && StringUtils.isNotBlank(c.getOwnerId())) {
				buddyIdList.add(c.getOwnerId());
			}
			if (reply && CollectionUtils.isNotEmpty(c.getReplies())) {
				for (Reply r : c.getReplies()) {
					buddyIdList.add(r.getOwnerId());
				}
			}
		}

		if (!buddyIdList.isEmpty()) {
			List<Buddy> buddyList = buddyService.loadBuddyByIdList(buddyIdList);
		}
	}

	@Profiled
	public void consummateReply(ReplyBo bo, boolean ownerAndTarget) {
		if (ownerAndTarget) {
			if (StringUtils.isNotBlank(bo.getOwnerId())) {
				BuddyBo buddy = buddyService.getBuddyByBuddyId(bo.getOwnerId());
				buddyService.consummateBuddy(buddy, true, false, false, false);
				bo.setOwner(buddy);
			}
			if (StringUtils.isNotBlank(bo.getTargetBuddyId())) {
				BuddyBo buddy = buddyService.getBuddyByBuddyId(bo.getTargetBuddyId());
				buddyService.consummateBuddy(buddy, true, false, false, false);
				bo.setTargetBuddy(buddy);
			}
		}
	}

	public DynamicBo addDynamic(final String buddyId, String circleId, String content, List<String> images) {
		// == 插入一条动态
		Dynamic dynamicEntity = new Dynamic();
		dynamicEntity.setOwnerId(buddyId);
		dynamicEntity.setContent(content);
		dynamicEntity.setCircleId(circleId);
		dynamicEntity.setImages(images == null ? null : images.toArray(new String[0]));
		dynamicEntity.setStatus(wordMatchService.checkForbiddenWords(content) > 0 ? DynamicStatusEnum.INIT.getCode()
				: DynamicStatusEnum.PASS.getCode());
		dynamicEntity.setCreatedTime(DateUtil.now());
		dynamicEntity.setModifiedTime(DateUtil.now());

		Dynamic.Stat stat = new Dynamic.Stat();
		stat.setViewCount(0);
		stat.setLikeCount(0);
		stat.setCommentCount(0);
		dynamicEntity.setStat(stat);
		final String dynamicId = dynamicDAO.add(dynamicEntity);
		dynamicEntity.setId(dynamicId);

		// == 为每一个关注者插入一个动态关系
		// 自己也添加一条
		addFollowDynamic(buddyId, dynamicId, false);

		// 异步执行，为防止执行失败后续改为消息机制
		executor.execute(new Runnable() {
			@Override
			public void run() {
				Buddy buddyEntity = buddyDAO.getById(buddyId);
				if (CollectionUtils.isNotEmpty(buddyEntity.getFollowers())) {
					final List<String> followerIdList = new LinkedList<String>();
					for (Buddy.FollowBuddy fb : buddyEntity.getFollowers()) {
						addFollowDynamic(fb.getBuddyId(), dynamicId, true);
						followerIdList.add(fb.getBuddyId());
					}
					pushService.pushNewDynamic(followerIdList);

				}
			}
		});

		DynamicBo dynamicBo = BeanTrans.transDynamic(dynamicEntity);
		consummateDynamic(dynamicBo, true, true, false);

		return dynamicBo;
	}

	public void addFollowDynamic(String buddyId, String dynamicId, boolean sendMsg) {
		// == 为每一个关注者插入一个动态关系, 同时发送一条推送消息提示有新动态
		FollowDynamic entity = new FollowDynamic();
		entity.setBuddyId(buddyId);
		entity.setDynamicId(dynamicId);
		entity.setStatus(FollowDynamicStatusEnum.VALID.getCode());
		Date now = DateUtil.now();
		entity.setCreatedTime(now);
		entity.setModifiedTime(now);
		followDynamicDAO.add(entity);
	}

	public List<DynamicBo> listDynamicForRecommend(String buddyId, Date baseDate, Integer count) {
		List<Dynamic> entityList = dynamicDAO.queryForRecommend(baseDate, count);
		loadDynamic(entityList, true, true, true);
		List<DynamicBo> boList = BeanTrans.transDynamic(entityList);
		if (boList != null) {
			for (DynamicBo bo : boList) {
				processLikeFlag(bo, buddyId);
				if (bo.getLikeList() != null && bo.getLikeList().size() > 10) {
					bo.setLikeList(bo.getLikeList().subList(0, 10));
				}
			}
			for (DynamicBo bo : boList) {
				consummateDynamic(bo, true, true, true);
			}
		}
		return boList;
	}

	public List<DynamicBo> listDynamicForNew(String buddyId, Date baseDate, Integer count) {
		List<FollowDynamic> fds = followDynamicDAO.queryForNew(buddyId, baseDate, count);
		if (fds == null) return null;
		List<DynamicBo> boList = null;

		// final List<String> ids = new ArrayList<String>(fds.size());
		// for (FollowDynamic fd : fds) {
		// ids.add(fd.getDynamicId());
		// }
		//
		// List<Dynamic> entityList = dynamicDAO.queryByIds(ids);
		// if (entityList != null) {
		// for (Dynamic entity : entityList) {
		// if (entity.getLikes() != null && entity.getLikes().size() > 10) {
		// entity.setLikes(entity.getLikes().subList(0, 10));
		// }
		// }
		// boList = new ArrayList<DynamicBo>(entityList.size());
		// for (Dynamic entity : entityList) {
		// DynamicBo bo = BeanTrans.transDynamic(entity);
		// processLikeFlag(bo, entity, buddyId);
		// boList.add(bo);
		// }
		// }
		//
		// if (boList != null) {
		// for (DynamicBo dynamicBo : boList) {
		// consummateDynamic(dynamicBo, true, true, true);
		// }
		//
		// Collections.sort(boList, new Comparator<DynamicBo>() {
		// @Override
		// public int compare(DynamicBo o1, DynamicBo o2) {
		// return ids.indexOf(o1.getDynamicId()) - ids.indexOf(o2.getDynamicId());
		// }
		// });
		// }

		List<String> idList = new LinkedList<String>();
		for (FollowDynamic fd : fds) {
			idList.add(fd.getDynamicId());
		}
		loadDynamicByIdList(idList, true, true, true);

		// 使用单条查询
		boList = new LinkedList<DynamicBo>();
		for (FollowDynamic fd : fds) {
			DynamicBo bo = getDynamicById(fd.getDynamicId());
			processLikeFlag(bo, buddyId);
			if (bo.getLikeList() != null && bo.getLikeList().size() > 10) {
				bo.setLikeList(bo.getLikeList().subList(0, 10));
			}
			consummateDynamic(bo, true, true, true);
			boList.add(bo);
		}

		return boList;
	}

	public List<DynamicBo> listDynamicForUser(String buddyId, String targetId, Date baseDate, Integer count) {
		List<Dynamic> entityList = dynamicDAO.queryForUser(targetId, baseDate, count);
		loadDynamic(entityList, true, true, true);
		List<DynamicBo> boList = BeanTrans.transDynamic(entityList);
		if (boList != null) {
			for (DynamicBo bo : boList) {
				processLikeFlag(bo, buddyId);
				if (bo.getLikeList() != null && bo.getLikeList().size() > 10) {
					bo.setLikeList(bo.getLikeList().subList(0, 10));
				}
				consummateDynamic(bo, true, true, true);
			}
		}
		return boList;
	}

	public DynamicBo getDynamic(String buddyId, String dynamicId) {
		loadDynamicByIdList(Arrays.asList(dynamicId), true, true, true);
		DynamicBo bo = getDynamicById(dynamicId);
		if (bo != null) {
			// 增加人气撒
			addHits(dynamicId);
			bo = getDynamicById(dynamicId);
			processLikeFlag(bo, buddyId);
			if (bo.getLikeList() != null && bo.getLikeList().size() > 10) {
				bo.setLikeList(bo.getLikeList().subList(0, 10));
			}
			consummateDynamic(bo, true, true, true);

		}
		return bo;
	}

	public List<CommentBo> listComment(String dynamicId, Date baseDate, Integer count) {
		Dynamic dynamic = dynamicDAO.queryCommentOrderByCreatedTime(dynamicId, baseDate, count);
		List<Comment> entityList = dynamic == null ? null : dynamic.getComments();
		loadComment(entityList, true, true);
		List<CommentBo> boList = BeanTrans.transComment(entityList);
		if (boList != null) {
			for (CommentBo bo : boList) {
				consummateComment(bo, true, true);
			}
		}
		return boList;
	}

	public CommentBo addComment(String buddyId, String dynamicId, String content) {
		Dynamic dynamic = checkAndGetDynamic(dynamicId);
		int commentsSize=0;
		if(dynamic!=null){
			commentsSize=dynamic.getComments()==null?0:dynamic.getComments().size();
		}
		//动态(dynamic)的评论数量上限 1000 wei.tao
		if(commentsSize>Integer.valueOf(AppContext.getDynamicCommentUpLimit())){
			log.warn("动态(dynamic)的评论数量超上限: buddyId={},dynamicId={}, content={}", buddyId,dynamicId,content);
			throw new BizException(RespCode.RES_9950);
		}
		if (dynamic == null) {
			return null; // TODO 提示
		}

		Comment commentEntity = new Comment();
		commentEntity.setDynamicId(dynamicId);
		commentEntity.setOwnerId(buddyId);
		commentEntity.setContent(content);
		commentEntity.setStatus(wordMatchService.checkForbiddenWords(content) > 0 ? CommentStatusEnum.INIT.getCode()
				: CommentStatusEnum.PASS.getCode());
		Date now = DateUtil.now();
		commentEntity.setCreatedTime(now);
		commentEntity.setModifiedTime(now);

		String commentId = dynamicDAO.addComment(commentEntity);
		commentEntity.setId(commentId);

		dynamicDAO.increaseDynamicCommentCount(dynamicId, 1);

		if (!buddyId.equals(dynamic.getOwnerId())) {
			Message msg = new Message();
			msg.setType(1);
			msg.setCreatorId(buddyId);
			msg.setReceiverId(dynamic.getOwnerId());
			msg.setTargetType(1);
			msg.setTargetId(dynamicId);
			msg.setContent(content);
			Arg arg = new Arg();
			arg.setDynamicId(dynamicId);
			arg.setCommentId(commentId);
			msg.setArg(arg);
			msg.setStatus(0);
			msg.setCreatedTime(now);
			msg.setModifiedTime(now);
			messageDAO.add(msg);

			pushService.pushUnreadMsgCount(msg.getReceiverId());
		}

		CommentBo bo = BeanTrans.transComment(commentEntity);
		consummateComment(bo, true, true);

		return bo;
	}

	public ReplyBo addReply(String buddyId, String dynamicId, String commentId, String targetBuddyId, String content) {
		checkComment(dynamicId, commentId);
		//评论的回复数量上限	500 wei.tao
		Comment commentEntity = dynamicDAO.getComment(dynamicId,commentId);
		int repliesSize=0;
		repliesSize=commentEntity.getReplies()==null?0:commentEntity.getReplies().size();
		if(repliesSize>Integer.valueOf(AppContext.getCommentReplyUpLimit())){
			log.warn("评论的回复数量超上限: buddyId={},dynamicId={}, commentId={}, targetBuddyId={}, content={}", buddyId,dynamicId,commentId,targetBuddyId,content);
			throw new BizException(RespCode.RES_9950);
		}
		Reply replyEntity = new Reply();
		replyEntity.setCommentId(commentId);
		replyEntity.setTargetId(targetBuddyId);
		replyEntity.setContent(content);
		replyEntity.setOwnerId(buddyId);
		replyEntity.setStatus(wordMatchService.checkForbiddenWords(content) > 0 ? ReplyStatusEnum.INIT.getCode()
				: ReplyStatusEnum.PASS.getCode());
		Date now = DateUtil.now();
		replyEntity.setCreatedTime(now);
		replyEntity.setModifiedTime(now);

		String replyId = dynamicDAO.addReply(dynamicId, replyEntity);
		replyEntity.setId(replyId);

		dynamicDAO.increaseDynamicCommentCount(dynamicId, 1);

		if (!buddyId.equals(targetBuddyId)) {
			Message msg = new Message();
			msg.setType(2);
			msg.setCreatorId(buddyId);
			msg.setReceiverId(targetBuddyId);
			msg.setTargetType(1);
			msg.setTargetId(dynamicId);
			msg.setContent(content);
			Arg arg = new Arg();
			arg.setDynamicId(dynamicId);
			arg.setCommentId(commentId);
			arg.setReplyId(replyId);
			msg.setArg(arg);
			msg.setStatus(0);
			msg.setCreatedTime(now);
			msg.setModifiedTime(now);
			messageDAO.add(msg);

			pushService.pushUnreadMsgCount(msg.getReceiverId());
		}

		ReplyBo bo = BeanTrans.transReply(replyEntity);
		consummateReply(bo, true);

		return bo;
	}

	public List<LikeBo> listLiker(String dynamicId, Date baseDate, Integer count) {
		Dynamic dynamic = dynamicDAO.queryLikerOrderByCreatedTime(dynamicId, baseDate, count);
		if (dynamic != null) {
			loadDynamic(Arrays.asList(dynamic), false, false, true);
		}
		DynamicBo dynamicBo = BeanTrans.transDynamic(dynamic);
		consummateDynamic(dynamicBo, false, false, true);
		return dynamicBo == null ? null : dynamicBo.getLikeList();
	}

	public void like(String buddyId, String dynamicId, Byte like) {
		Dynamic dynamic = checkAndGetDynamic(dynamicId);
		//动态的点赞数量上限	10000 wei.tao
		int likesSize=0;
		if(dynamic!=null){
			likesSize=dynamic.getLikes()==null?0:dynamic.getLikes().size();
		}
		if(likesSize>Integer.valueOf(AppContext.getDynamicLikeCountUpLimit())){
			log.warn("动态的点赞数超上限: buddyId={},dynamicId={}, like={}", buddyId,dynamicId,like);
			throw new BizException(RespCode.RES_9950);
		}
		Like e = dynamicDAO.queryLikeForDynamicAndBuddy(dynamicId, buddyId);
		if (e == null && like == 1) {
			if (dynamic == null) {
				return; // TODO 提示
			}

			Like likeEntity = new Like();
			likeEntity.setTargetId(dynamicId);
			likeEntity.setOwnerId(buddyId);
			likeEntity.setStatus(LikeStatusEnum.NORMAL.getCode());
			Date now = DateUtil.now();
			likeEntity.setCreatedTime(now);
			likeEntity.setModifiedTime(now);
			dynamicDAO.addLike(likeEntity);

			dynamicDAO.increaseDynamicLikeCount(dynamicId, 1);

			if (!StringUtils.equals(buddyId, dynamic.getOwnerId())) {
				long c = messageDAO.countByTypeAndCreatorIdAndTargetId(3, buddyId, dynamicId);
				if (c == 0) {
					Message msg = new Message();
					msg.setType(3);
					msg.setCreatorId(buddyId);
					msg.setReceiverId(dynamic.getOwnerId());
					msg.setTargetType(1);
					msg.setTargetId(dynamicId);
					msg.setContent(null);
					msg.setStatus(0);
					msg.setCreatedTime(now);
					msg.setModifiedTime(now);
					Arg arg = new Arg();
					arg.setDynamicId(dynamicId);
					msg.setArg(arg);
					messageDAO.add(msg);

					pushService.pushUnreadMsgCount(msg.getReceiverId());
				}
			}

		} else if (e != null && like == 0) {
			dynamicDAO.removeLike(dynamicId, e.getId());

			dynamicDAO.increaseDynamicLikeCount(dynamicId, -1);
		}
	}

	public void addHits(String dynamicId) {
		dynamicDAO.increaseDynamicViewCount(dynamicId, 1);
	}

	public void removeDynamic(String buddyId, String dynamicId) {
		Dynamic dynamic;
		try {
			dynamic = checkAndGetDynamic(dynamicId);
		} catch (BizException e) {
			log.warn("dynamic not exists: {}", dynamicId);
			return;
		}

		if (!StringUtils.equals(buddyId, dynamic.getOwnerId())) {
			log.warn("cannot remove dynamic for current user '{}' is not the owner of the dynamic '{}'", buddyId,
					dynamic.getOwnerId());
			return;
		}

		followDynamicDAO.removeByDynamicId(dynamicId);
		dynamicDAO.remove(dynamicId);
	}

	public void removeComment(String buddyId, String dynamicId, String commentId) {
		Dynamic dynamic = dynamicDAO.getById(dynamicId);
		if (dynamic == null || dynamic.getComments() == null) return;
		Comment comment = null;
		for (Comment cmt : dynamic.getComments()) {
			if (StringUtils.equals(commentId, cmt.getId())) {
				comment = cmt;
				break;
			}
		}
		if (comment == null) return;
		int count = 1;
		if (comment.getReplies() != null) {
			count += comment.getReplies().size();
		}
		if (dynamicDAO.removeComment(dynamicId, commentId) > 0) {
			dynamicDAO.increaseDynamicCommentCount(dynamicId, -count);
		}
	}

	public void removeReply(String buddyId, String dynamicId, String commentId, String replyId) {
		Dynamic dynamic = dynamicDAO.getById(dynamicId);
		if (dynamic == null || dynamic.getComments() == null) return;
		Comment comment = null;
		for (Comment cmt : dynamic.getComments()) {
			if (StringUtils.equals(commentId, cmt.getId())) {
				comment = cmt;
				break;
			}
		}
		if (comment == null || comment.getReplies() == null) return;
		boolean found = false;
		for (Reply reply : comment.getReplies()) {
			if (StringUtils.equals(reply.getId(), replyId)) {
				found = true;
				break;
			}
		}
		if (!found) return;
		if (dynamicDAO.removeReply(dynamicId, commentId, replyId) > 0) {
			dynamicDAO.increaseDynamicCommentCount(dynamicId, -1);
		}
	}

	public Dynamic checkAndGetDynamic(String dynamicId) {
		Dynamic dynamicEntity = dynamicDAO.getById(dynamicId);
		checkDynamic(dynamicEntity);
		return dynamicEntity;
	}

	public void checkDynamic(String dynamicId) {
		Dynamic entity = dynamicDAO.queryDynamicStatusById(dynamicId);
		checkDynamic(entity);
	}

	public void checkDynamic(Dynamic dynamic) {
		if (dynamic == null || (dynamic.getStatus() != null && (dynamic.getStatus() == 2 || dynamic.getStatus() == 3))) {
			throw new BizException(RespCode.RES_0120);
		}
	}

	public void checkComment(String dynamicId, String commentId) {
		checkDynamic(dynamicId);
		Dynamic dynamic = dynamicDAO.queryComment(dynamicId, commentId);
		if (dynamic.getComments() == null || dynamic.getComments().size() == 0) {
			throw new BizException(RespCode.RES_0121);
		}
		checkComment(dynamic.getComments().get(0));
	}

	public void checkComment(Comment comment) {
		if (comment == null || (comment.getStatus() != null && (comment.getStatus() == 2 || comment.getStatus() == 3))) {
			throw new BizException(RespCode.RES_0121);
		}
	}

}

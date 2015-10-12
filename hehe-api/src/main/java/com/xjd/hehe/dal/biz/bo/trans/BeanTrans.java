package com.xjd.hehe.dal.biz.bo.trans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.xjd.hehe.dal.biz.bo.BaseBo;
import com.xjd.hehe.dal.biz.bo.BuddyBo;
import com.xjd.hehe.dal.biz.bo.CircleBo;
import com.xjd.hehe.dal.biz.bo.CircleCategoryBo;
import com.xjd.hehe.dal.biz.bo.CommentBo;
import com.xjd.hehe.dal.biz.bo.DynamicBo;
import com.xjd.hehe.dal.biz.bo.FollowBuddyBo;
import com.xjd.hehe.dal.biz.bo.FollowCircleBo;
import com.xjd.hehe.dal.biz.bo.LikeBo;
import com.xjd.hehe.dal.biz.bo.MessageBo;
import com.xjd.hehe.dal.biz.bo.ReplyBo;
import com.xjd.hehe.dal.entity.Buddy;
import com.xjd.hehe.dal.entity.Buddy.FollowBuddy;
import com.xjd.hehe.dal.entity.Buddy.FollowCircle;
import com.xjd.hehe.dal.entity.Circle;
import com.xjd.hehe.dal.entity.CircleCategory;
import com.xjd.hehe.dal.entity.Comment;
import com.xjd.hehe.dal.entity.Document;
import com.xjd.hehe.dal.entity.Dynamic;
import com.xjd.hehe.dal.entity.Like;
import com.xjd.hehe.dal.entity.Message;
import com.xjd.hehe.dal.entity.Reply;
import com.xjd.hehe.dal.entity.User;
import com.jkys.social.util.enums.BoolEnum;
import com.jkys.social.util.enums.UserTypeEnum;

public abstract class BeanTrans {

	public static List<BuddyBo> transBuddy(List<Buddy> entityList) {
		if (entityList == null) return null;
		List<BuddyBo> boList = new ArrayList<BuddyBo>(entityList.size());
		for (Buddy entity : entityList) {
			boList.add(transBuddy(entity));
		}
		return boList;
	}

	public static BuddyBo transBuddy(Buddy entity) {
		if (entity == null) return null;
		BuddyBo bo = new BuddyBo();
		transBuddy(entity, bo);
		return bo;
	}

	public static void transBuddy(Buddy source, BuddyBo target) {
		if (source == null || target == null) return;
		target.setBuddyId(source.getId());
		target.setUserId(source.getUserId());
		target.setSignature(source.getSignature());
		target.setvFlag(source.getExtended() == null ? null : source.getExtended().getvFlag());

		target.setStatus(source.getStatus());
		target.setCreatedTime(source.getCreatedTime());
		target.setModifiedTime(source.getModifiedTime());

		target.setFollowCircleList(transFollowCircle(source.getFollowCircles()));
		target.setFollowingList(transFollowBuddy(source.getFollowing()));
		target.setFollowerList(transFollowBuddy(source.getFollowers()));

		target.setBgImgUrl(source.getBgImgUrl());
	}

	public static void transBuddy(User source, BuddyBo target) {
		if (source == null || target == null) return;
		UserTypeEnum typeEnum = UserTypeEnum.valueOfCode(source.getUtype());
		if (typeEnum == UserTypeEnum.DOCTOR) {
			target.setUserName(StringUtils.isNotBlank(source.getName()) ? source.getName() : source.getNickname());
		} else {
			target.setUserName(StringUtils.isNotBlank(source.getNickname()) ? source.getNickname() : source.getName());
		}
		target.setUserType(source.getUtype());
		target.setImgUrl(source.getImgUrl());
		target.setDiabetesType(source.getDiabetesType());
		target.setDiabetesTime(source.getDiabetesTime2());
		target.setHospitalId(source.getHospitalId());
		target.setHospital(source.getHospital());
		target.setTitle(source.getTitleName());
	}

	public static List<FollowCircleBo> transFollowCircle(List<FollowCircle> entityList) {
		if (entityList == null) return null;
		List<FollowCircleBo> boList = new ArrayList<FollowCircleBo>(entityList.size());
		for (FollowCircle entity : entityList) {
			boList.add(transFollowCircle(entity));
		}
		return boList;
	}

	public static FollowCircleBo transFollowCircle(FollowCircle entity) {
		FollowCircleBo bo = new FollowCircleBo();
		transFollowCircle(entity, bo);
		return bo;
	}

	public static void transFollowCircle(FollowCircle source, FollowCircleBo target) {
		BeanUtils.copyProperties(source, target);
	}

	public static List<FollowBuddyBo> transFollowBuddy(List<FollowBuddy> entityList) {
		if (entityList == null) return null;
		List<FollowBuddyBo> boList = new ArrayList<FollowBuddyBo>(entityList.size());
		for (FollowBuddy entity : entityList) {
			boList.add(transFollowBuddy(entity));
		}
		return boList;
	}

	public static FollowBuddyBo transFollowBuddy(FollowBuddy entity) {
		FollowBuddyBo bo = new FollowBuddyBo();
		BeanUtils.copyProperties(entity, bo);
		return bo;
	}

	public static void transFollowBuddy(FollowBuddy source, FollowBuddyBo target) {
		BeanUtils.copyProperties(source, target);
	}

	public static List<DynamicBo> transDynamic(List<Dynamic> entityList) {
		if (entityList == null) return null;
		List<DynamicBo> boList = new ArrayList<DynamicBo>(entityList.size());
		for (Dynamic entity : entityList) {
			boList.add(transDynamic(entity));
		}
		return boList;
	}

	public static DynamicBo transDynamic(Dynamic entity) {
		if (entity == null) return null;
		DynamicBo bo = new DynamicBo();
		transDynamic(entity, bo);
		return bo;
	}

	public static void transDynamic(Dynamic source, DynamicBo target) {
		transBase(source, target);

		target.setDynamicId(source.getId());
		target.setOwnerId(source.getOwnerId());
		target.setCircleId(source.getCircleId());
		target.setContent(source.getContent());
		target.setImages(source.getImages() == null ? null : Arrays.asList(source.getImages()));

		if (source.getStat() != null) {
			target.setViewCount(source.getStat().getViewCount());
			target.setLikeCount(source.getStat().getLikeCount());
			target.setCommentCount(source.getStat().getCommentCount());
		}

		target.setCommentList(transComment(source.getComments()));
		target.setLikeList(transLike(source.getLikes()));

		if (source.getRecommend() == null) {
			target.setRecommendFlag(BoolEnum.NO.getCode());
		} else {
			target.setRecommendFlag(BoolEnum.YES.getCode());
			target.setRecBuddyId(source.getRecommend().getRecUser());
			target.setRecTime(source.getRecommend().getRecTime());
			target.setRecLevel(source.getRecommend().getRecLevel());
		}

		target.setLikeFlag(BoolEnum.NO.getCode()); // 默认值
	}

	public static List<LikeBo> transLike(List<Like> entityList) {
		if (entityList == null) return null;
		List<LikeBo> boList = new ArrayList<LikeBo>(entityList.size());
		for (Like entity : entityList) {
			boList.add(transLike(entity));
		}
		return boList;
	}

	public static LikeBo transLike(Like entity) {
		LikeBo bo = new LikeBo();
		transLike(entity, bo);
		return bo;
	}

	public static void transLike(Like source, LikeBo target) {
		transBase(source, target);
		target.setLikeId(source.getId());
		target.setTargetId(source.getTargetId());
		target.setOwnerId(source.getOwnerId());
	}

	public static List<CommentBo> transComment(List<Comment> entityList) {
		if (entityList == null) return null;
		List<CommentBo> boList = new ArrayList<CommentBo>(entityList.size());
		for (Comment entity : entityList) {
			boList.add(transComment(entity));
		}
		return boList;
	}

	public static CommentBo transComment(Comment entity) {
		CommentBo bo = new CommentBo();
		transComment(entity, bo);
		return bo;
	}

	public static void transComment(Comment source, CommentBo target) {
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setReplyList(transReply(source.getReplies()));
	}

	public static List<ReplyBo> transReply(List<Reply> entityList) {
		if (entityList == null) return null;
		List<ReplyBo> boList = new ArrayList<ReplyBo>(entityList.size());
		for (Reply entity : entityList) {
			boList.add(transReply(entity));
		}
		return boList;
	}

	public static ReplyBo transReply(Reply entity) {
		ReplyBo bo = new ReplyBo();
		transReply(entity, bo);
		return bo;
	}

	public static void transReply(Reply source, ReplyBo target) {
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setTargetBuddyId(source.getTargetId());
	}

	public static List<CircleBo> transCircle(List<Circle> entityList) {
		if (entityList == null) return null;
		List<CircleBo> boList = new ArrayList<CircleBo>(entityList.size());
		for (Circle entity : entityList) {
			boList.add(transCircle(entity));
		}
		return boList;
	}

	public static CircleBo transCircle(Circle entity) {
		if (entity == null) return null;
		CircleBo bo = new CircleBo();
		transCircle(entity, bo);
		return bo;
	}

	public static void transCircle(Circle source, CircleBo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setViewCount(source.getStat() == null ? null : source.getStat().getViewCount());
		target.setFollowBuddyList(transFollowBuddy(source.getFollowBuddies()));
	}

	public static List<CircleCategoryBo> transCircleCategory(List<CircleCategory> entityList) {
		if (entityList == null) return null;
		List<CircleCategoryBo> boList = new ArrayList<CircleCategoryBo>(entityList.size());
		for (CircleCategory entity : entityList) {
			boList.add(transCircleCategory(entity));
		}
		return boList;
	}

	public static CircleCategoryBo transCircleCategory(CircleCategory entity) {
		if (entity == null) return null;
		CircleCategoryBo bo = new CircleCategoryBo();
		transCircleCategory(entity, bo);
		return bo;
	}

	public static void transCircleCategory(CircleCategory source, CircleCategoryBo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
	}

	public static List<MessageBo> transMessage(List<Message> entityList) {
		if (entityList == null) return null;
		List<MessageBo> boList = new ArrayList<MessageBo>(entityList.size());
		for (Message entity : entityList) {
			boList.add(transMessage(entity));
		}
		return boList;
	}

	public static MessageBo transMessage(Message entity) {
		if (entity == null) return null;
		MessageBo bo = new MessageBo();
		transMessage(entity, bo);
		return bo;
	}

	public static void transMessage(Message source, MessageBo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
	}

	public static void transBase(Document source, BaseBo target) {
		if (source == null || target == null) return;
		target.setStatus(source.getStatus());
		target.setCreatedTime(source.getCreatedTime());
		target.setModifiedTime(source.getModifiedTime());
	}

}

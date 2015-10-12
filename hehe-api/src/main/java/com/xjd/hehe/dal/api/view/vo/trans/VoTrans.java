package com.xjd.hehe.dal.api.view.vo.trans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.xjd.hehe.dal.api.view.vo.BaseVo;
import com.xjd.hehe.dal.api.view.vo.BuddyVo;
import com.xjd.hehe.dal.api.view.vo.CircleVo;
import com.xjd.hehe.dal.api.view.vo.CommentVo;
import com.xjd.hehe.dal.api.view.vo.DynamicVo;
import com.xjd.hehe.dal.api.view.vo.MessageVo;
import com.xjd.hehe.dal.api.view.vo.ReplyVo;
import com.xjd.hehe.dal.biz.bo.BaseBo;
import com.xjd.hehe.dal.biz.bo.BuddyBo;
import com.xjd.hehe.dal.biz.bo.CircleBo;
import com.xjd.hehe.dal.biz.bo.CommentBo;
import com.xjd.hehe.dal.biz.bo.DynamicBo;
import com.xjd.hehe.dal.biz.bo.FollowBuddyBo;
import com.xjd.hehe.dal.biz.bo.LikeBo;
import com.xjd.hehe.dal.biz.bo.MessageBo;
import com.xjd.hehe.dal.biz.bo.ReplyBo;

public abstract class VoTrans {

	public static List<DynamicVo> transDynamic(List<DynamicBo> sourceList) {
		if (sourceList == null) return null;
		List<DynamicVo> targetList = new ArrayList<DynamicVo>(sourceList.size());
		for (DynamicBo source : sourceList) {
			targetList.add(transDynamic(source));
		}
		return targetList;
	}

	public static DynamicVo transDynamic(DynamicBo source) {
		if (source == null) return null;
		DynamicVo target = new DynamicVo();
		transDynamic(source, target);
		return target;
	}

	public static void transDynamic(DynamicBo source, DynamicVo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setOwner(transBuddy(source.getOwner()));
		target.setLikerList(transLike(source.getLikeList()));
		target.setCircle(transCircle(source.getCircle()));
		target.setRecTime(source.getRecTime() == null ? null : source.getRecTime().getTime());
		
		if (target.getImages() != null) {
			List<String> images = new LinkedList<String>();
			for (int i = target.getImages().size() - 1; i >= 0; i--) {
				String path = StringUtils.trimToNull(target.getImages().get(i));
				if (path != null) {
					images.add(path);
				}
			}
			target.setImages(images);
		}
	}

	public static List<BuddyVo> transBuddy(List<BuddyBo> sourceList) {
		if (sourceList == null) return null;
		List<BuddyVo> targetList = new ArrayList<BuddyVo>(sourceList.size());
		for (BuddyBo source : sourceList) {
			targetList.add(transBuddy(source));
		}
		return targetList;
	}

	public static BuddyVo transBuddy(BuddyBo source) {
		if (source == null) return null;
		BuddyVo target = new BuddyVo();
		transBuddy(source, target);
		return target;
	}

	public static void transBuddy(BuddyBo source, BuddyVo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		if (StringUtils.isBlank(target.getUserName())) {
			target.setUserName("未填写姓名");
		}
		if (target.getDiabetesType() != null && target.getDiabetesType() == 3) {
			target.setDiabetesTime(null);
		}
	}

	public static List<BuddyVo> transFollowBuddy(List<FollowBuddyBo> sourceList) {
		if (sourceList == null) return null;
		List<BuddyVo> targetList = new ArrayList<BuddyVo>(sourceList.size());
		for (FollowBuddyBo source : sourceList) {
			targetList.add(transFollowBuddy(source));
		}
		return targetList;
	}

	public static BuddyVo transFollowBuddy(FollowBuddyBo source) {
		if (source == null) return null;
		BuddyVo target = new BuddyVo();
		transFollowBuddy(source, target);
		return target;
	}

	public static void transFollowBuddy(FollowBuddyBo source, BuddyVo target) {
		if (source == null || target == null) return;
		transBuddy(source.getBuddy(), target);
		target.setEnrollTime(source.getCreatedTime() == null ? null : source.getCreatedTime().getTime());
		target.setFollowTime(source.getCreatedTime() == null ? null : source.getCreatedTime().getTime());
	}

	public static List<BuddyVo> transLike(List<LikeBo> sourceList) {
		if (sourceList == null) return null;
		List<BuddyVo> targetList = new ArrayList<BuddyVo>(sourceList.size());
		for (LikeBo source : sourceList) {
			targetList.add(transLike(source));
		}
		return targetList;
	}

	public static BuddyVo transLike(LikeBo source) {
		if (source == null) return null;
		BuddyVo target = new BuddyVo();
		transLike(source, target);
		return target;
	}

	public static void transLike(LikeBo source, BuddyVo target) {
		if (source == null || target == null) return;
		transBuddy(source.getOwner(), target);
		target.setLikeTime(source.getCreatedTime() == null ? null : source.getCreatedTime().getTime());
	}

	public static CircleVo transCircle(CircleBo source) {
		if (source == null) return null;
		CircleVo target = new CircleVo();
		transCircle(source, target);
		return target;
	}

	public static void transCircle(CircleBo source, CircleVo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
	}

	public static List<CommentVo> transComment(List<CommentBo> sourceList) {
		if (sourceList == null) return null;
		List<CommentVo> targetList = new ArrayList<CommentVo>(sourceList.size());
		for (CommentBo source : sourceList) {
			targetList.add(transComment(source));
		}
		return targetList;
	}

	public static CommentVo transComment(CommentBo source) {
		if (source == null) return null;
		CommentVo target = new CommentVo();
		transComment(source, target);
		return target;
	}

	public static void transComment(CommentBo source, CommentVo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setCommentId(source.getId());
		target.setOwner(transBuddy(source.getOwner()));
		target.setReplyList(transReply(source.getReplyList()));
	}

	public static List<ReplyVo> transReply(List<ReplyBo> sourceList) {
		if (sourceList == null) return null;
		List<ReplyVo> targetList = new ArrayList<ReplyVo>(sourceList.size());
		for (ReplyBo source : sourceList) {
			targetList.add(transReply(source));
		}
		return targetList;
	}

	public static ReplyVo transReply(ReplyBo source) {
		if (source == null) return null;
		ReplyVo target = new ReplyVo();
		transReply(source, target);
		return target;
	}

	public static void transReply(ReplyBo source, ReplyVo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setReplyId(source.getId());
		target.setTargetBuddy(transBuddy(source.getTargetBuddy()));
		target.setOwner(transBuddy(source.getOwner()));
	}

	public static List<MessageVo> transMessage(List<MessageBo> sourceList) {
		if (sourceList == null) return null;
		List<MessageVo> targetList = new ArrayList<MessageVo>(sourceList.size());
		for (MessageBo source : sourceList) {
			targetList.add(transMessage(source));
		}
		return targetList;
	}

	public static MessageVo transMessage(MessageBo source) {
		if (source == null) return null;
		MessageVo target = new MessageVo();
		transMessage(source, target);
		return target;
	}

	public static void transMessage(MessageBo source, MessageVo target) {
		if (source == null || target == null) return;
		transBase(source, target);
		BeanUtils.copyProperties(source, target);
		target.setMsgId(source.getId());
		target.setCreator(transBuddy(source.getCreator()));
		if (Integer.valueOf(1).equals(source.getTargetType())) {
			target.setDynamic(transDynamic((DynamicBo) source.getTarget()));
		}
	}

	public static void transBase(BaseBo source, BaseVo target) {
		target.setStatus(source.getStatus());
		target.setCreatedTime(source.getCreatedTime() == null ? null : source.getCreatedTime().getTime());
		target.setModifiedTime(source.getModifiedTime() == null ? null : source.getModifiedTime().getTime());
	}

}

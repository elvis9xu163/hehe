package com.xjd.hehe.biz.utl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.xjd.hehe.biz.bo.*;
import com.xjd.hehe.dal.mongo.ent.*;
import com.xjd.hehe.dal.mongo.ent.config.BannerEntity;

/**
 * @author elvis.xu
 * @since 2015-10-26 00:16
 */
public abstract class BeanTrans {

	public static List<TokenBo> transToken(List<TokenEntity> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<TokenBo> targetList = new ArrayList<>(sourceList.size());
		for (TokenEntity source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static List<TopicBo> transTopic(List<TopicEntity> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<TopicBo> targetList = new ArrayList<>(sourceList.size());
		for (TopicEntity source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static List<JokeBo> transJoke(List<JokeEntity> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<JokeBo> targetList = new ArrayList<>(sourceList.size());
		for (JokeEntity source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static List<CommentBo> transComment(List<CommentEntity> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<CommentBo> targetList = new ArrayList<>(sourceList.size());
		for (CommentEntity source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static TokenBo trans(TokenEntity source) {
		if (source == null) {
			return null;
		}
		TokenBo target = new TokenBo();
		trans(source, target);
		return target;
	}

	public static void trans(TokenEntity source, TokenBo target) {
		trans((BaseEntity) source, (BaseBo) target);
		BeanUtils.copyProperties(source, target);
	}

	public static UserBo trans(UserEntity source) {
		if (source == null) {
			return null;
		}
		UserBo target = new UserBo();
		trans(source, target);
		return target;
	}

	public static void trans(UserEntity source, UserBo target) {
		trans((BaseEntity) source, (BaseBo) target);
		BeanUtils.copyProperties(source, target);
	}

	public static TopicBo trans(TopicEntity source) {
		if (source == null) {
			return null;
		}
		TopicBo target = new TopicBo();
		trans(source, target);
		return target;
	}

	public static void trans(TopicEntity source, TopicBo target) {
		trans((BaseEntity) source, (BaseBo) target);
		BeanUtils.copyProperties(source, target);
	}

	public static JokeBo trans(JokeEntity source) {
		if (source == null) {
			return null;
		}
		JokeBo target = new JokeBo();
		trans(source, target);
		return target;
	}

	public static void trans(JokeEntity source, JokeBo target) {
		trans((BaseEntity) source, (BaseBo) target);
		BeanUtils.copyProperties(source, target);
	}

	public static void trans(BaseEntity source, BaseBo target) {
		target.setId(source.getId());
		target.setCtime(source.getCtime());
		target.setUtime(source.getUtime());
	}

	public static BannerBo trans(BannerEntity.Item source) {
		if (source == null) {
			return null;
		}
		BannerBo target = new BannerBo();
		trans(source, target);
		return target;
	}

	public static void trans(BannerEntity.Item source, BannerBo target) {
		BeanUtils.copyProperties(source, target);
	}


	public static CommentBo trans(CommentEntity source) {
		if (source == null) {
			return null;
		}
		CommentBo target = new CommentBo();
		trans(source, target);
		return target;
	}

	public static void trans(CommentEntity source, CommentBo target) {
		BeanUtils.copyProperties(source, target);
	}

}

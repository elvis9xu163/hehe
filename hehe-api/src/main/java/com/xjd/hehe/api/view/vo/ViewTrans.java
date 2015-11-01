package com.xjd.hehe.api.view.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.xjd.hehe.biz.bo.*;

/**
 * @author elvis.xu
 * @since 2015-10-31 14:58
 */
public class ViewTrans {

	public static List<TopicVo> transTopic(List<TopicBo> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<TopicVo> targetList = new ArrayList<>(sourceList.size());
		for (TopicBo source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static List<BannerVo> transBanner(List<BannerBo> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<BannerVo> targetList = new ArrayList<>(sourceList.size());
		for (BannerBo source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;

	}

	public static List<JokeVo> transJoke(List<JokeBo> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<JokeVo> targetList = new ArrayList<>(sourceList.size());
		for (JokeBo source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static List<CommentVo> transCmt(List<CommentBo> sourceList) {
		if (sourceList == null) {
			return null;
		}
		List<CommentVo> targetList = new ArrayList<>(sourceList.size());
		for (CommentBo source : sourceList) {
			targetList.add(trans(source));
		}
		return targetList;
	}

	public static UserVo trans(UserBo source) {
		if (source == null) {
			return null;
		}
		UserVo target = new UserVo();
		trans(source, target);
		return target;
	}

	public static void trans(UserBo source, UserVo target) {
		BeanUtils.copyProperties(source, target);
	}

	public static TopicVo trans(TopicBo source) {
		if (source == null) {
			return null;
		}
		TopicVo target = new TopicVo();
		trans(source, target);
		return target;
	}

	public static void trans(TopicBo source, TopicVo target) {
		BeanUtils.copyProperties(source, target);
	}

	public static BannerVo trans(BannerBo source) {
		if (source == null) {
			return null;
		}
		BannerVo target = new BannerVo();
		trans(source, target);
		return target;
	}

	public static void trans(BannerBo source, BannerVo target) {
		BeanUtils.copyProperties(source, target);
	}

	public static JokeVo trans(JokeBo source) {
		if (source == null) {
			return null;
		}
		JokeVo target = new JokeVo();
		trans(source, target);
		return target;
	}

	public static void trans(JokeBo source, JokeVo target) {
		BeanUtils.copyProperties(source, target);
		target.setUser(trans(source.getUser()));
		target.setTopics(transTopic(source.getTopicList()));
		target.setPjoke(trans(source.getPjoke()));
	}

	public static CommentVo trans(CommentBo source) {
		if (source == null) {
			return null;
		}
		CommentVo target = new CommentVo();
		trans(source, target);
		return target;
	}

	public static void trans(CommentBo source, CommentVo target) {
		BeanUtils.copyProperties(source, target);
		target.setUser(trans(source.getUser()));
		target.setPcmt(trans(source.getPcmt()));
	}

}

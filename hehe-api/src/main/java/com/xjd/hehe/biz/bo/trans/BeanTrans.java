package com.xjd.hehe.biz.bo.trans;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.xjd.hehe.biz.bo.BaseBo;
import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.dal.mongo.ent.BaseEntity;
import com.xjd.hehe.dal.mongo.ent.TokenEntity;

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

	public static void trans(BaseEntity source, BaseBo target) {
		target.setId(source.getId());
		target.setCtime(source.getCtime());
		target.setUtime(source.getUtime());
	}
}

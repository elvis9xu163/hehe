package com.xjd.hehe.dal.biz.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.biz.bo.TokenBo;
import com.xjd.hehe.dal.biz.service.CacheService.GetResult;
import com.xjd.hehe.dal.dao.mongo.BuddyDAO;
import com.xjd.hehe.dal.dao.mysql.TokenDAO;
import com.xjd.hehe.dal.dao.mysql.UserDAO;
import com.xjd.hehe.dal.entity.Token;
import com.xjd.hehe.dal.entity.User;
import com.jkys.social.util.DateUtil;

@Service
public class AuthService {

	@Autowired
	TokenDAO tokenDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	BuddyDAO buddyDAO;
	@Autowired
	CacheService cacheService;

	@Profiled
	@Cacheable(value = "temp", key = "#userId", unless = "#result == null")
	public User getUserById(Integer userId) {
		User user = userDAO.selectUserById(userId);
		processUser(user);
		return user;
	}

	@Profiled
	public List<User> loadUserByIdList(List<Integer> userIdList) {
		if (CollectionUtils.isEmpty(userIdList)) return Collections.emptyList();
		GetResult<User, Integer> rt = cacheService.get(CacheService.CACHE_TEMP, userIdList);

		List<User> allList = new LinkedList<User>();

		if (CollectionUtils.isNotEmpty(rt.getNoHitIdList())) {
			List<User> loadList = userDAO.selectUserByIdList(rt.getNoHitIdList());
			for (User u : loadList) {
				processUser(u);
				cacheService.put(CacheService.CACHE_TEMP, u.getId(), u);
			}
			allList.addAll(loadList);
		}

		allList.addAll(rt.getResultList());

		return allList;
	}
	
	public void processUser(User user) {
		if (user != null && Byte.valueOf((byte) 3).equals(user.getDiabetesType())) {
			// 妊娠糖尿病不显示年限
			user.setDiabetesTime(null);
		} else if (user != null && user.getDiabetesTime2() == null && user.getDiabetesTime() != null) {
			if ((user.getDiabetesTime().intValue() + "").length() == 4) {
				Calendar c = Calendar.getInstance();
				c.set(user.getDiabetesTime(), 1, 1, 0, 0, 0);
				user.setDiabetesTime2(c.getTimeInMillis());
			} else {
				user.setDiabetesTime2(user.getDiabetesTime().longValue() * 1000L);
			}
		}
	}

	@Profiled
	@Cacheable(value = "temp", key = "#token", unless = "#result == null")
	public TokenBo getTokenByToken(String token) {
		Token tokenEntity = tokenDAO.selectByToken(token);

		if (tokenEntity == null) {
			return null;
		}
		checkAndInitBuddyId(tokenEntity);

		TokenBo tokenBo = new TokenBo();
		BeanUtils.copyProperties(tokenEntity, tokenBo);
		return tokenBo;
	}

	@Profiled
	public void checkAndInitBuddyId(Token tokenEntity) {
		if (StringUtils.isNotBlank(tokenEntity.getBuddyId())) {
			return;
		}

		String buddyId = buddyDAO.queryBuddyIdByUserId(tokenEntity.getUserId());
		tokenEntity.setBuddyId(buddyId);
		if (buddyId == null) {
			// 初始化Buddy
			buddyId = buddyDAO.initBuddy(tokenEntity.getUserId());
			tokenEntity.setBuddyId(buddyId);

			if (buddyId == null) {
				throw new RuntimeException("cannot init buddy for user: userId=[" + tokenEntity.getUserId() + "]");
			}
		}

		Token upd = new Token();
		upd.setToken(tokenEntity.getToken());
		upd.setBuddyId(tokenEntity.getBuddyId());
		upd.setUpdTime(DateUtil.nowInMilliseconds());
		tokenDAO.updateByTokenSelective(upd);
	}

}

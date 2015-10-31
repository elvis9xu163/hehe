package com.xjd.hehe.biz.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.JokeBo;
import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.biz.bo.TopicBo;
import com.xjd.hehe.biz.bo.UserBo;
import com.xjd.hehe.biz.utl.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.TokenDao;
import com.xjd.hehe.dal.mongo.dao.UserDao;
import com.xjd.hehe.dal.mongo.ent.TokenEntity;
import com.xjd.hehe.dal.mongo.ent.UserEntity;
import com.xjd.hehe.utl.DateUtil;
import com.xjd.hehe.utl.DigestUtil;
import com.xjd.hehe.utl.ValidUtil;
import com.xjd.hehe.utl.enums.TokenStatusEnum;
import com.xjd.hehe.utl.enums.UserStatusEnum;
import com.xjd.hehe.utl.enums.UserTypeEnum;
import com.xjd.hehe.utl.exception.BizException;
import com.xjd.hehe.utl.respcode.RespCode;

/**
 * @author elvis.xu
 * @since 2015-10-25 09:41
 */
@Service
public class UserService {
	@Autowired
	TokenDao tokenDao;
	@Autowired
	UserDao userDao;
	@Autowired
	ResourceService resourceService;
	@Autowired
	TopicService topicService;
	@Autowired
	JokeService jokeService;

	public TokenBo getToken(String token) {
		return BeanTrans.trans(tokenDao.get(token));
	}

	public TokenBo checkAndGetToken(String token) {
		TokenBo tokenBo = getToken(token);
		if (tokenBo == null || tokenBo.getStatus() == null || tokenBo.getStatus() != TokenStatusEnum.NORMAL.getCode()) {
			throw new BizException(RespCode.RES_0101);
		}
		return tokenBo;
	}


	public void regist(String uname, String pwd, String uid) {
		UserEntity userEnt = userDao.get(uid);
		if (userEnt == null || userEnt.getType() != UserTypeEnum.VISITOR.getCode() || userEnt.getStatus() != UserStatusEnum.NORMAL.getCode()) {
			throw new RuntimeException("用户状态异常, 不能注册: " + uid);
			// FIXME 其它人性化的处理
		}

		String mail = null, mobile = null;
		if (ValidUtil.valid(ValidUtil.MAIL, uname)) {
			mail = uname;
		} else {
			mobile = uname;
		}
		String encPwd = DigestUtil.digest(pwd, DigestUtil.SHA1);
		userDao.regist(uid, mail, mobile, encPwd);
	}

	public TokenBo login(String uname, String pwd, String endId) {
		UserEntity userEnt = null;
		boolean bVisitor = StringUtils.isBlank(uname);
		if (bVisitor) {
			userEnt = new UserEntity();
			userEnt.setName(genUname());
			userEnt.setType(UserTypeEnum.VISITOR.getCode());
			userEnt.setTypeTime(DateUtil.now());
			userEnt.setStatus(UserStatusEnum.NORMAL.getCode());
			userDao.save(userEnt);
		} else {
			String encPwd = DigestUtil.digest(pwd, DigestUtil.SHA1);
			userEnt = userDao.getBy$MailOrName$AndPwd(uname, encPwd);
			if (userEnt == null) {
				throw new BizException(RespCode.RES_0103);
			}
		}

		return genToken(userEnt.getId(), endId);
	}

	protected TokenBo genToken(String uid, String endId) {
		if (StringUtils.isBlank(uid)) {
			throw new NullPointerException("uid cannot be null.");
		}
		tokenDao.invalidToken(uid, endId);
		TokenEntity tokenEnt = new TokenEntity();
		tokenEnt.setUid(uid);
		tokenEnt.setSalt(genSalt());
		tokenEnt.setEndId(endId);
		tokenEnt.setStatus(TokenStatusEnum.NORMAL.getCode());
		tokenDao.save(tokenEnt);
		return BeanTrans.trans(tokenEnt);
	}

	protected String genUname() {
		Random random = new Random();
		String name = "";
		for (int i = 0; i < 8; i++) {
			name += random.nextInt(10);
		}
		return name;
	}

	protected char[] saltSeed = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T'};

	protected String genSalt() {
		Random random = new Random();
		String salt = "";
		for (int i = 0; i < 8; i++) {
			salt += saltSeed[random.nextInt(20)];
		}
		return salt;
	}

	public UserBo getUser(String userId) {
		UserEntity userEnt = userDao.get(userId);
		return BeanTrans.trans(userEnt);
	}

	public void modifyUser(String userId, String nick, String avatar) {
		userDao.updateNickAndAvatar(userId, nick, resourceService.transUrlToInternal(avatar));
	}

	public List<TopicBo> listFollowTopic(String uid) {
		UserEntity userEntity = userDao.get(uid);

		List<TopicBo> topicBoList = new LinkedList<>();
		if (userEntity.getTopics() != null) {
			for (int i = userEntity.getTopics().size() - 1; i >= 0; i--) {
				topicBoList.add(topicService.getTopic(userEntity.getTopics().get(i)));
			}
		}
		return topicBoList;
	}


	public List<JokeBo> listFavorJoke(String uid, int page) {
		UserEntity userEntity = userDao.get(uid);

		List<JokeBo> jokeBoList = new LinkedList<>();
		if (userEntity.getFavors() != null) {
			int limit = 20;
			int offset = page * 20;
			for (int i = userEntity.getFavors().size() - 1; i >= 0 && limit > 0; i--, limit--) {
				JokeBo jokeBo = jokeService.getJoke(userEntity.getFavors().get(i));
				jokeService.consummate(jokeBo, true, true, true);
				jokeBoList.add(jokeBo);
			}
		}
		return jokeBoList;

	}
}

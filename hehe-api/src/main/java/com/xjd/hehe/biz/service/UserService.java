package com.xjd.hehe.biz.service;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.biz.bo.trans.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.TokenDao;
import com.xjd.hehe.dal.mongo.dao.UserDao;
import com.xjd.hehe.dal.mongo.ent.UserEntity;
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

	public TokenBo getToken(String token) {
		return BeanTrans.trans(tokenDao.get(token));
	}

	public TokenBo checkAndGetToken(String token) {
		TokenBo tokenBo = getToken(token);
		if (tokenBo == null || tokenBo.getStatus() == null || tokenBo.getStatus() != 0) {
			throw new BizException(RespCode.RES_0101);
		}
		return tokenBo;
	}

	public TokenBo login(String uname, String pwd) {
		boolean bVisitor = StringUtils.isBlank(uname);
		if (bVisitor) {
			UserEntity userEnt = new UserEntity();
			userEnt.setName();
		}
		return null;
	}

	protected int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

	protected String genUname() {
		
	}
}

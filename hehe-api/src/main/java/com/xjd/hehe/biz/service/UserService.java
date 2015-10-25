package com.xjd.hehe.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.biz.bo.trans.BeanTrans;
import com.xjd.hehe.dal.mongo.dao.TokenDao;
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
}

package com.xjd.hehe.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ReqLogDao;
import com.xjd.hehe.dal.mongo.ent.ReqLogEntity;

/**
 * @author elvis.xu
 * @since 2015-10-25 22:50
 */
@Service
public class ReqLogService {
	@Autowired
	ReqLogDao reqLogDao;

	public void log(Integer apiVer, String apiName, String userIp, String endId, String endModel, String endSys, Integer appVer, String userToken) {
		ReqLogEntity reqLogEntity = new ReqLogEntity();
		reqLogEntity.setVer(apiVer);
		reqLogEntity.setApi(apiName);
		reqLogEntity.setIp(userIp);
		reqLogEntity.setEndId(endId);
		reqLogEntity.setEndModel(endModel);
		reqLogEntity.setEndSys(endSys);
		reqLogEntity.setAppVer(appVer);
		reqLogEntity.setToken(userToken);
		reqLogDao.save(reqLogEntity);
	}
}

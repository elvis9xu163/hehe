package com.xjd.hehe.dal.biz.service;

import java.util.Date;

import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.biz.bo.HealthFileSetting;
import com.xjd.hehe.dal.dao.mongo.ComplaintDAO;
import com.xjd.hehe.dal.dao.mysql.SystemSettingDao;
import com.xjd.hehe.dal.entity.Complaint;
import com.xjd.hehe.dal.entity.SystemSetting;
import com.xjd.hehe.dal.entity.query.ComplaintCondition;
import com.jkys.social.util.AppContext;
import com.jkys.social.util.JsonUtil;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;

@Service
public class BasicService {
	private static Logger log = LoggerFactory.getLogger(BasicService.class);
	@Autowired
	ComplaintDAO complaintDAO;
	@Autowired
	SystemSettingDao systemSettingDao;

	@Profiled
	public void report(String buddyId, Byte targetType, String targetId, String content) {
		//动态的举报数量上限	100 wei.tao
		ComplaintCondition complaintCondition=new ComplaintCondition();
		complaintCondition.setTargetId(targetId);
		Long complaintCount=complaintDAO.countComplaintResult(complaintCondition);
		if(complaintCount>Long.valueOf(AppContext.getDynamicComplaintCountUpLimit())){
			log.warn("动态的举报数量超上限: buddyId={}, targetType={}, targetId={}, content={}", buddyId,targetType,targetId,content);
			throw new BizException(RespCode.RES_9950);
		}
		
		Complaint complaint = new Complaint();
		complaint.setOwnerId(buddyId);
		complaint.setType(targetType == null ? null : targetType.intValue());
		complaint.setTargetId(targetId);
		complaint.setContent(content);
		complaint.setStatus(0);
		complaint.setCreatedTime(new Date());
		complaint.setModifiedTime(new Date());
		complaintDAO.add(complaint);
	}

	@Profiled
	@Cacheable(value = "static", key = "'Setting_HealthFileSetting'", unless = "#result == null")
	public HealthFileSetting getHealthFileSetting() {
		HealthFileSetting hfs = null;
		SystemSetting setting = systemSettingDao.selectByCode("health_file_setting");
		if (setting != null && setting.getCode() != null) {
			hfs = JsonUtil.parse(setting.getValue(), HealthFileSetting.class);
		}
		return hfs;
	}
}

package com.xjd.hehe.dal.biz.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.dao.mysql.SystemSettingDao;
import com.xjd.hehe.dal.entity.SystemSetting;
import com.xjd.hehe.dal.entity.setting.ContentUgcWordSetting;
import com.jkys.social.util.DateUtil;
import com.jkys.social.util.JsonUtil;
import com.xjd.ws.Result;
import com.xjd.ws.Result.WordResult;
import com.xjd.ws.WordsMatcher;
import com.xjd.ws.impl.DefaultWordsMatcher;

@Service
public class WordMatchService {
	private static Logger log = LoggerFactory.getLogger(WordMatchService.class);

	@Autowired
	SystemSettingDao systemSettingDao;

	long lastUpdateTime = 0;
	long dataUpdateTime = 0;
	volatile WordsMatcher wordsMatcher;
	Object rebuildLock = new Object();

	/**
	 * @param comment
	 * @return 0 没有违禁词
	 * @throws Exception
	 */
	@Profiled
	public int checkForbiddenWords(String comment) {
		long currentTime = DateUtil.nowInMilliseconds();
		if (currentTime > lastUpdateTime + 600000) {
			SystemSetting systemSetting = systemSettingDao.selectByCode("CONTENT_UGC_WORD");
			if (systemSetting != null && systemSetting.getUtime() > dataUpdateTime) {
				buildWordsMatcher(systemSetting);
			}
			lastUpdateTime = currentTime;
		}

		// 进行检查
		if (wordsMatcher == null) {
			return 0;
		}
		Result rt = wordsMatcher.match(comment);
		List<WordResult> wrtList = rt.wordResults();

		return wrtList.size();
	}

	protected void buildWordsMatcher(SystemSetting model) {
		try {
			synchronized (rebuildLock) {
				if (model.getUtime() > dataUpdateTime) {
					log.info("重新加载违禁词...");
					WordsMatcher tmpWordsMatcher = new DefaultWordsMatcher();
					String value = model.getValue();
					if (StringUtils.isNotBlank(value)) {
						ContentUgcWordSetting setting = JsonUtil.parse(value, ContentUgcWordSetting.class);

						BufferedReader br = new BufferedReader(new StringReader(StringUtils.trimToEmpty(setting.getWord())));
						String line = null;
						while ((line = br.readLine()) != null) {
							line = line.trim();
							tmpWordsMatcher.addWord(line);
						}
						br.close();
					}
					wordsMatcher = tmpWordsMatcher;
					dataUpdateTime = model.getUtime();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

package com.xjd.hehe.spider.haha.proc;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;

/**
 * @author elvis.xu
 * @since 2015-11-08 22:42
 */
@Service
public class ImgMd5Processor {
	public static Logger log = LoggerFactory.getLogger(ImgMd5Processor.class);

	@Autowired
	ImgDao imgDao;

	public void process() {
		while(true) {
			List<ImgEntity> imgs = imgDao.getNoMd5(1000);
			if (imgs == null || imgs.isEmpty()) {
				break;
			}
			for (ImgEntity img : imgs) {
				try {
					String uri = img.getUri();
					String md5 = resolveMd5(uri);
					Long size = getSize(uri);
					imgDao.updateMd5(img.getId(), md5, size);
				} catch (Exception e) {
					log.error("update md5 for img [" + img.getId() + "] failed.", e);
				}
			}
		}
	}

	public String resolveMd5(String uri) {
		int s = uri.indexOf('/') + 1;
		int e = uri.indexOf('_');
		return uri.substring(s, e);
	}

	public Long getSize(String uri) {
		File f = new File("/data/data/www/", uri);
		return f.length();
	}
}

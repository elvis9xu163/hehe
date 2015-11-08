package com.xjd.hehe.biz.service;

import io.netty.handler.codec.http.multipart.FileUpload;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;

/**
 * @author elvis.xu
 * @since 2015-10-31 15:14
 */
@Service
public class ResourceService {
	@Autowired
	ImgDao imgDao;

	public String transUrlToInternal(String url) {
		url = StringUtils.trimToNull(url);
		if (url == null) {
			return null;
		}
		if (url.startsWith("http://139.129.13.123/")) {
			return url.substring("http://139.129.13.123/".length());
		}
		return url;
	}

	public String transUrlToOutside(String url) {
		if (url == null) {
			return null;
		}
		if (!url.startsWith("http")) {
			// 优先使用外部链接
			ImgEntity imgEntity = imgDao.getByUri(url);
			if (imgEntity != null && imgEntity.getRefUrl() != null) {
				return imgEntity.getRefUrl();
			}
			return "http://139.129.13.123/" + url;
		}
		return url;
	}

	public String preupload(String md5, String upfor) {
		ImgEntity imgEntity = imgDao.getByMd5(md5);
		if (imgEntity == null) {
			return null;
		}

		if (imgEntity.getBiz() == null || !imgEntity.getBiz().contains(upfor)) {
			processImgFor(imgEntity.getUri(), upfor);
		}

		if (imgEntity.getRefUrl() != null) {
			return imgEntity.getRefUrl();
		} else {
			return transUrlToOutside(imgEntity.getUri());
		}
	}

	protected void processImgFor(String url, String upfor) {
		// FIXME
	}


	public String upload(FileUpload uploadFile, String upfor) {
		// FIXME 上传和处理
		return null;
	}
}

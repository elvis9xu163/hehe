package com.xjd.hehe.spider.haha;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;

@Service
public class SaverImg {
	private static Logger log = LoggerFactory.getLogger(SaverImg.class);

	@Autowired
	ImgDao imgDao;

	public String save(Map imgMap) {
		if (imgMap == null) {
			return null;
		}
		// http://image.haha.mx/2015/10/13/middle/1980882_99e65f6d05c967401a954e02ef79fe83_1444722018.jpg
		String name = (String) imgMap.get("name");
		String url = "http://image.haha.mx/" + imgMap.get("path") + "big/" + name;

		ImgEntity entity = imgDao.getByRefUrl(url);
		if (entity != null) {
			return entity.getUri();
		}

		String suffix = getSuffix(name);

		try {
			BufferedImage bufImg = ImageIO.read(new URL(url));

			int w = bufImg.getWidth();
			int h = bufImg.getHeight();

			File tmpFile = File.createTempFile("tmp_", "tmp");
			if (!ImageIO.write(bufImg, suffix, tmpFile)) {
				throw new IOException("no appropriate writer is found");
			}

			String newName = "pic/" + md5(tmpFile) + "_" + w + "x" + h + "." + suffix;
			String path = "/data/data/www/" + newName;

			if (!new File(path).exists()) {
				tmpFile.renameTo(new File(path));

				entity = new ImgEntity();
				entity.setUri(newName);
				entity.setH(h);
				entity.setW(w);
				entity.setRefUrl(url);
				imgDao.save(entity);

				log.info("保存图片成功: {}", url);
			}

			return newName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected String getSuffix(String name) {
		if (StringUtils.isBlank(name) || name.lastIndexOf('.') == -1) {
			return "jpg";
		}
		return name.substring(name.lastIndexOf('.') + 1);
	}

	protected String md5(File imgFile) {
		return MD5Utils.md5(imgFile);
	}
}

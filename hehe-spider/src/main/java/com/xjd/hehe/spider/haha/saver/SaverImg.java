package com.xjd.hehe.spider.haha.saver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;
import com.xjd.hehe.spider.haha.MD5Utils;
import com.xjd.hehe.spider.haha.bean.Joke;

@Service
public class SaverImg {
	private static Logger log = LoggerFactory.getLogger(SaverImg.class);

	@Autowired
	ImgDao imgDao;

	public ImgEntity save(Joke.Pic pic) {
		if (pic == null) {
			return null;
		}

		// http://image.haha.mx/2015/10/13/small/1980882_99e65f6d05c967401a954e02ef79fe83_1444722018.jpg
		// http://image.haha.mx/2015/10/13/middle/1980882_99e65f6d05c967401a954e02ef79fe83_1444722018.jpg
		// http://image.haha.mx/2015/10/13/big/1980882_99e65f6d05c967401a954e02ef79fe83_1444722018.jpg
		String refUrlSmall = "http://image.haha.mx/" + pic.getPath() + "small/" + pic.getName();
		String refUrlBig = "http://image.haha.mx/" + pic.getPath() + "big/" + pic.getName();

		ImgEntity entity = imgDao.getByRefUrl(refUrlBig);
		if (entity != null) { // 已经有了
			return entity;
		}

		String suffix = getSuffix(pic.getName());

		// 先下载大图
		File tmpFile = download(refUrlBig, suffix);

		// 获取属性
		BufferedImage bufImg = null;
		try {
			bufImg = ImageIO.read(tmpFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		int w = bufImg.getWidth();
		int h = bufImg.getHeight();
		String md5 = MD5Utils.md5(tmpFile);

		// 存储文件
		String path = "pic/" + md5 + "_" + w + "x" + h + "." + suffix;
		File file = new File("/data/data/www/", path);
		if (!file.exists()) {
			if (tmpFile.renameTo(file)) {
				log.info("保存图片到文件: {} -- {}", file.getAbsolutePath(), refUrlBig);
			} else {
				throw new RuntimeException("保存图片到文件失败: " + file.getAbsolutePath() + "--" + refUrlBig);
			}
		}

		//保存数据
		entity = imgDao.getByRefUrl(refUrlBig); // 再判断一次
		if (entity != null) { // 已经有了
			return entity;
		}
		entity = new ImgEntity();
		entity.setUri(path);
		entity.setH(h);
		entity.setW(w);
		entity.setRefUrl(refUrlBig);
		imgDao.save(entity);
		log.info("新增IMG: {}, {}", entity.getId(), entity.getUri());

		return entity;
	}

	public File download(String url, String suffix) {
		Request request = Request.Get(url);
		Response res = null;
		try {
			request.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
			res = request.execute();
			InputStream in = res.returnContent().asStream();
			File f = File.createTempFile("haha_", "." + suffix);
			FileOutputStream out = new FileOutputStream(f);
			StreamUtils.copy(in, out);
			in.close();
			out.close();
			log.info("下载图片到临时文件: {} -- {}", f.getAbsolutePath(), url);
			return f;

		} catch (IOException e) {
			throw new RuntimeException("下载图片出错: " + url, e);
		} finally {
			if (res != null) {
				res.discardContent();
			}
		}
	}


	protected String getSuffix(String name) {
		if (StringUtils.isBlank(name) || name.lastIndexOf('.') == -1) {
			return "jpg";
		}
		return name.substring(name.lastIndexOf('.') + 1);
	}

}

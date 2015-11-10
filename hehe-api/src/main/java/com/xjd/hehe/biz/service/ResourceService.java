package com.xjd.hehe.biz.service;

import io.netty.handler.codec.http.multipart.FileUpload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;
import com.xjd.hehe.utl.enums.ImgBizEnum;

/**
 * @author elvis.xu
 * @since 2015-10-31 15:14
 */
@Service
public class ResourceService {
	private static Logger log = LoggerFactory.getLogger(ResourceService.class);

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
			try {
				processImgFor(imgEntity.getUri(), upfor);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (imgEntity.getRefUrl() != null) {
			return imgEntity.getRefUrl();
		} else {
			return transUrlToOutside(imgEntity.getUri());
		}
	}
	
	public String upload(FileUpload uploadFile, String upfor) {
		File file = getFile(uploadFile);
		
		return null;
	}

	protected void processImgFor(String url, String upfor) throws IOException {
		File f = new File("/data/data/www/", url);
		if (!f.isFile()) {
			log.error("Img file not exists: {}", f.getAbsolutePath());
			return;
		}
		processImgFor(f, upfor);
	}
	
	protected File getFile(FileUpload upfile) throws IOException {
		File f = null;
		try {
			f = upfile.getFile();
		} catch (IOException e) {
			f = File.createTempFile("hehe", "up");
			if (!upfile.renameTo(f)) {
				throw new RuntimeException("Cannot rename to FileUpload.");
			}
		}
		return f;
	}

	protected void processImgFor(File file, String upfor) throws IOException {
		int[] wh = getWidthAndHeight(file);
		if (ImgBizEnum.valueOfCode(upfor) == ImgBizEnum.AVATAR) { // 100x100 150x150[原图]
			
		} else if (ImgBizEnum.valueOfCode(upfor) == ImgBizEnum.JOKE) {

		}
	}

	protected String[] spliteFilename(String filename) {
		String[] parts = new String[2];
		int i = 0;
		if ((i = filename.lastIndexOf('.')) != -1) {
			parts[0] = filename.substring(0, i);
			parts[1] = filename.substring(i);
		} else {
			parts[0] = filename;
			parts[1] = "";
		}
		return parts;
	}
	
	public int[] getWidthAndHeight(File file) throws IOException {
		int[] wh = new int[2];
		BufferedImage image = ImageIO.read(file);
		wh[0] = image.getWidth();
		wh[1] = image.getHeight();
		return wh;
	}

	public void scaleImage(File source, File target, int w, int h, int maxW, int maxH) throws IOException {
		Builder<File> builder = Thumbnails.of(source);
		if (h <= maxH && w <= maxW) {
			builder.scale(1.0D).toFile(target);
		} else {
			double rw = w * 1.0 / maxW;
			double rh = h * 1.0 / maxH;
			if (rw >= rh) {
				builder.scale(1 / rw);
			} else {
				builder.scale(1 / rh);
			}
			builder.toFile(target);
		}
	}
}

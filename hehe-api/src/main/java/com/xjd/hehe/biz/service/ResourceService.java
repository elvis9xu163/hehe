package com.xjd.hehe.biz.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.netty.handler.codec.http.multipart.FileUpload;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xjd.hehe.dal.mongo.dao.ImgDao;
import com.xjd.hehe.dal.mongo.ent.ImgEntity;
import com.xjd.hehe.utl.DigestUtil;
import com.xjd.hehe.utl.enums.ImgBizEnum;

/**
 * @author elvis.xu
 * @since 2015-10-31 15:14
 */
@Service
public class ResourceService {
	private static Logger log = LoggerFactory.getLogger(ResourceService.class);

	String imgRoot = "/data/data/www/";
	String imgHttp = "http://114.215.91.205/";

	@Autowired
	ImgDao imgDao;

	public String transUrlToInternal(String url) {
		url = StringUtils.trimToNull(url);
		if (url == null) {
			return null;
		}
		if (url.startsWith(imgHttp)) {
			return url.substring(imgHttp.length());
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
			return imgHttp + url;
		}
		return url;
	}

	public String preupload(String md5, String upfor) throws IOException {
		ImgEntity imgEntity = imgDao.getByMd5(md5);
		if (imgEntity == null) {
			return null;
		}

		processImg(imgEntity, upfor);

		if (imgEntity.getRefUrl() != null) {
			return imgEntity.getRefUrl();
		} else {
			return transUrlToOutside(imgEntity.getUri());
		}
	}

	public String upload(FileUpload uploadFile, String upfor) throws IOException {
		File f = getFile(uploadFile);
		return uploadImg(f, upfor);
	}

	protected File getFile(FileUpload upfile) throws IOException {
		String[] nameparts = spliteFilename(upfile.getFilename());
		File f = File.createTempFile("hehe", nameparts[1]);
		if (!upfile.renameTo(f)) {
			throw new RuntimeException("Cannot rename to FileUpload.");
		}
		return f;
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

//	public void scaleImage(File source, File target, int w, int h, int maxW, int maxH) throws IOException {
//		Builder<File> builder = Thumbnails.of(source);
//		if (h <= maxH && w <= maxW) {
//			builder.scale(1.0D).toFile(target);
//		} else {
//			double rw = w * 1.0 / maxW;
//			double rh = h * 1.0 / maxH;
//			if (rw >= rh) {
//				builder.scale(1 / rw);
//			} else {
//				builder.scale(1 / rh);
//			}
//			builder.toFile(target);
//		}
//	}

	public void scaleImage(File source, File target, int w, int h, int maxW, int maxH, boolean overWH, double minRwh, double maxRwh) throws IOException {
		Builder<File> builder = Thumbnails.of(source);
		if (h <= maxH && w <= maxW) {
			if (source.equals(target)) {
				return;
			}
			builder.scale(1.0D).toFile(target);
		} else {
			double rwh = w * 1.0 / h;
			if (minRwh > 0 && rwh < minRwh) {
				builder.crop(Positions.TOP_CENTER).size(w, (int) (w / minRwh)).toFile(target);
				scaleImage(target, target, w, (int) (w / minRwh), maxW, maxH, overWH, minRwh, maxRwh);
			} else if (maxRwh > 0 && rwh > maxRwh) {
				builder.crop(Positions.CENTER_LEFT).size((int) (h * maxRwh), h).toFile(target);
				scaleImage(target, target, (int) (h * maxRwh), h, maxW, maxH, overWH, minRwh, maxRwh);
			}
			if (overWH && (w <= maxW || h <= maxH)) {
				if (source.equals(target)) {
					return;
				}
				builder.scale(1.0D).toFile(target);
			} else {
				double rw = w * 1.0 / maxW;
				double rh = h * 1.0 / maxH;
				if (overWH) {
					builder.scale(1 / Math.min(rw, rh));
				} else {
					builder.scale(1 / Math.max(rw, rh));
				}
				builder.toFile(target);
			}
		}
	}

	public void copyAsRenameTo(File source, File target) {
		try {
			FileInputStream in = new FileInputStream(source);
			FileOutputStream out = new FileOutputStream(target);
			byte[] buf = new byte[1024 * 2];
			int c = -1;
			while ((c = in.read(buf)) != -1) {
				if (c > 0) {
					out.write(buf, 0, c);
				}
			}
			in.close();
			out.close();
			source.delete();
		} catch (IOException e) {
			throw new RuntimeException("复制文件失败: '" + source.getAbsolutePath() + "' --> '" + target.getAbsolutePath() + "'", e);
		}
	}

	public String uploadImg(File file, String upfor) throws IOException {
		String md5 = DigestUtil.digest(file, DigestUtil.MD5);
		// 判断图片是否存在
		ImgEntity img = imgDao.getByMd5(md5);

		if (img == null) {
			// 移动原图到目录，并计算好相关参数
			String[] nameparts = spliteFilename(file.getAbsolutePath());
			int[] wh = getWidthAndHeight(file);
			String path = "pic/" + md5 + "_" + wh[0] + "x" + wh[1] + nameparts[1];
			File sfile = new File(imgRoot, path);
			copyAsRenameTo(file, sfile);

			img = new ImgEntity();
			img.setUri(path);
			img.setW(wh[0]);
			img.setH(wh[1]);
			img.setMd5(md5);
			img.setSize(sfile.length());
			img.setFrom((byte) 0);
			imgDao.save(img);
		}

		// 处理图片
		processImg(img, upfor);

		return transUrlToOutside(img.getUri());
	}

	public void processImg(ImgEntity img, String upfor) throws IOException {
		if (img.getBiz() != null && img.getBiz().contains(upfor)) {
			return;
		}
		if (ImgBizEnum.valueOfCode(upfor) == ImgBizEnum.AVATAR) { // 100x100 150x150(原图)
			// 100x100
			scaleImage(new File(imgRoot, img.getUri()), new File(imgRoot, compName(img.getUri(), "sm")), img.getW(), img.getH(), 100, 100, false, -1, -1);

		} else if (ImgBizEnum.valueOfCode(upfor) == ImgBizEnum.JOKE) { // [原图] 324x576(取最大,长宽比失衡) 540x960(取最小)
			scaleImage(new File(imgRoot, img.getUri()), new File(imgRoot, compName(img.getUri(), "sm")), img.getW(), img.getH(), 324, 576, false, 0.25, 3);
			scaleImage(new File(imgRoot, img.getUri()), new File(imgRoot, compName(img.getUri(), "md")), img.getW(), img.getH(), 540, 960, true, -1, -1);
		}
		String biz = StringUtils.trimToNull(img.getBiz());
		if (biz == null) {
			biz = upfor;
		} else {
			biz += "," + upfor;
		}
		imgDao.updateBiz(img.getId(), biz);
	}

	protected String compName(String name, String addon) {
		String[] nameparts = spliteFilename(name);
		return nameparts[0] + "_" + addon + nameparts[1];
	}
}

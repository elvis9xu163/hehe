package com.xjd.hehe.dal.biz.service;

import io.netty.handler.codec.http.multipart.FileUpload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;

import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Service;

import com.jkys.social.util.AppContext;

@Service
public class ResourceService {
	public static final int MAX_WIDTH = 540;
	public static final int MAX_HEIGHT = 540;

	@Profiled
	public String uploadImage(FileUpload upFile) throws IOException {
		String prefix = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String suffix = "";
		int i = 0;
		if ((i = upFile.getFilename().lastIndexOf('.')) != -1) {
			suffix = upFile.getFilename().substring(i);
		}
		String rootDir = AppContext.getUploadDirRoot();
		String tmpDir = AppContext.getUploadDirTmp();
		String formalDir = AppContext.getUploadDirFormal();

		File file = new File(tmpDir, prefix + suffix);

		upFile.renameTo(file);

		BufferedImage image = ImageIO.read(file);
		int h = image.getHeight();
		int w = image.getWidth();
		prefix += "_" + w + "x" + h;
		File sourceFile = new File(tmpDir, prefix + suffix);
		file.renameTo(sourceFile);

		Builder<File> builder = Thumbnails.of(sourceFile);
		File smallFile = new File(tmpDir, prefix + "_small" + suffix);
		if (h <= MAX_HEIGHT && w <= MAX_WIDTH) {
			builder.scale(1.0D).toFile(smallFile);
		} else {
			double rw = w * 1.0 / MAX_WIDTH;
			double rh = h * 1.0 / MAX_HEIGHT;
			if (rw >= rh) {
				builder.scale(1 / rw);
			} else {
				builder.scale(1 / rh);
			}
			builder.toFile(smallFile);
		}

		smallFile.renameTo(new File(rootDir + formalDir, smallFile.getName()));
		sourceFile.renameTo(new File(rootDir + formalDir, sourceFile.getName()));

		return formalDir + prefix + suffix;
	}

}

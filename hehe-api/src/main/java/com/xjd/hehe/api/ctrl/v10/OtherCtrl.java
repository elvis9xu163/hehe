package com.xjd.hehe.api.ctrl.v10;

import java.util.List;

import io.netty.handler.codec.http.multipart.FileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.BannerListBody;
import com.xjd.hehe.api.view.body.UrlBody;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.BannerBo;
import com.xjd.hehe.biz.service.ConfigService;
import com.xjd.hehe.biz.service.ResourceService;
import com.xjd.hehe.utl.ValidUtil;
import com.xjd.nhs.annotation.RequestMapping;
import com.xjd.nhs.annotation.RequestParam;

/**
 * @author elvis.xu
 * @since 2015-10-31 20:58
 */
@Controller
@RequestMapping("/api/10")
public class OtherCtrl {
	@Autowired
	ConfigService configService;
	@Autowired
	ResourceService resourceService;

	@RequestMapping(value = "/listBanner", method = RequestMapping.Method.ALL)
	public View listBanner() {

		List<BannerBo> list = configService.listBanner();

		BannerListBody body = new BannerListBody();
		body.setBanners(ViewTrans.transBanner(list));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/preupload", method = RequestMapping.Method.ALL)
	public View preupload(@RequestParam("md5") String md5, @RequestParam("resType") String resType, @RequestParam("upfor") String upfor) {
		ValidUtil.check(ValidUtil.MD5, md5, ValidUtil.RESTYPE, resType, ValidUtil.UPFOR, upfor);

		byte upforB = Byte.parseByte(upfor);
		String upforS = upforB == 1 ? "[AVATAR]" : "[JOKE]";

		String url = resourceService.preupload(md5, upforS);

		UrlBody body = new UrlBody();
		body.setUrl(url);

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}

	@RequestMapping(value = "/upload", method = RequestMapping.Method.POST, supportMultipart = true)
	public View upload(@RequestParam("md5") String md5, @RequestParam("resType") String resType, @RequestParam("upfor") String upfor, @RequestParam("file")FileUpload uploadFile) {
		ValidUtil.check(ValidUtil.MD5, md5, ValidUtil.RESTYPE, resType, ValidUtil.UPFOR, upfor);

		byte upforB = Byte.parseByte(upfor);
		String upforS = upforB == 1 ? "[AVATAR]" : "[JOKE]";

		String url = resourceService.preupload(md5, upforS);

		if (url == null) {
			url = resourceService.upload(uploadFile, upforS);
		}

		UrlBody body = new UrlBody();
		body.setUrl(url);

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}



}

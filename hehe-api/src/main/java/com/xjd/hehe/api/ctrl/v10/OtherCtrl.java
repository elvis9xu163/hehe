package com.xjd.hehe.api.ctrl.v10;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.BannerListBody;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.BannerBo;
import com.xjd.hehe.biz.service.ConfigService;
import com.xjd.nhs.annotation.RequestMapping;

/**
 * @author elvis.xu
 * @since 2015-10-31 20:58
 */
@Controller
@RequestMapping("/api/10")
public class OtherCtrl {
	@Autowired
	ConfigService configService;

	@RequestMapping(value = "/listBanner", method = RequestMapping.Method.ALL)
	public View listBanner() {

		List<BannerBo> list = configService.listBanner();

		BannerListBody body = new BannerListBody();
		body.setBanners(ViewTrans.transBanner(list));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}
}

package com.xjd.hehe.api.ctrl.v10;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.cmpt.RequestContext;
import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.BannerListBody;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.BannerBo;
import com.xjd.hehe.biz.service.CommentService;
import com.xjd.hehe.biz.service.ConfigService;
import com.xjd.hehe.biz.service.JokeService;
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
	JokeService jokeService;
	@Autowired
	CommentService commentService;

	@RequestMapping(value = "/listBanner", method = RequestMapping.Method.ALL)
	public View listBanner() {

		List<BannerBo> list = configService.listBanner();

		BannerListBody body = new BannerListBody();
		body.setBanners(ViewTrans.transBanner(list));

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}


	@RequestMapping(value = "/like", method = RequestMapping.Method.POST)
	public View like(@RequestParam("oid") String oid, @RequestParam("otype") String otype) {
		ValidUtil.check(ValidUtil.OID, oid, ValidUtil.OTYPE, otype);

		byte otypeB = Byte.parseByte(otype);

		jokeService.like(RequestContext.getUserId(), oid, otypeB);

		View view = ViewUtil.defaultView();
		return view;
	}

	@RequestMapping(value = "/unlike", method = RequestMapping.Method.POST)
	public View unlike(@RequestParam("oid") String oid, @RequestParam("otype") String otype) {
		ValidUtil.check(ValidUtil.OID, oid, ValidUtil.OTYPE, otype);

		byte otypeB = Byte.parseByte(otype);

		jokeService.unlike(RequestContext.getUserId(), oid, otypeB);

		View view = ViewUtil.defaultView();
		return view;
	}
}

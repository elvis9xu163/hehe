package com.xjd.hehe.api.ctrl.v10;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.cmpt.RequestContext;
import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.CidBody;
import com.xjd.hehe.api.view.body.CommentListBody;
import com.xjd.hehe.api.view.vo.ViewTrans;
import com.xjd.hehe.biz.bo.CommentBo;
import com.xjd.hehe.biz.service.CommentService;
import com.xjd.hehe.utl.ValidUtil;
import com.xjd.hehe.utl.exception.BizException;
import com.xjd.hehe.utl.respcode.RespCode;
import com.xjd.nhs.annotation.RequestMapping;
import com.xjd.nhs.annotation.RequestParam;

/**
 * @author elvis.xu
 * @since 2015-10-29 21:14
 */
@Controller
@RequestMapping("/api/10")
public class CmtCtrl {
	public static Logger log = LoggerFactory.getLogger(CmtCtrl.class);

	@Autowired
	CommentService commentService;

	@RequestMapping(value = "/listCmt", method = RequestMapping.Method.ALL)
	public View listCmt(@RequestParam("jid") String jid, @RequestParam("pcon") String pcon) {
		ValidUtil.check(ValidUtil.OID, jid);

		Long time = null;
		if (StringUtils.isNotBlank(pcon)) {
			try {
				time = Long.parseLong(pcon);
			} catch (NumberFormatException e) {
				log.warn("pcon wrong format: " + pcon, e);
			}
		}
		Date date = time == null ? null : new Date(time);

		List<CommentBo> hotBoList = null;
		if (date == null) {
			hotBoList = commentService.listHotCmt(jid);
		}

		List<CommentBo> boList = commentService.listCmt(jid, date);


		CommentListBody body = new CommentListBody();
		body.setHotCmts(ViewTrans.transCmt(hotBoList));
		body.setCmts(ViewTrans.transCmt(boList));
		body.setPcon(CollectionUtils.isEmpty(boList) ? null : boList.get(boList.size() - 1).getCtime().getTime() + "");

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}


	@RequestMapping(value = "/addCmt", method = RequestMapping.Method.POST)
	public View addCmt(@RequestParam("jid") String jid, @RequestParam("cid") String cid, @RequestParam("txt") String txt) {
		txt = StringUtils.trimToNull(txt);

		if (txt == null) {
			throw new BizException(RespCode.RES_0120);
		}

		CommentBo bo = commentService.addCmt(RequestContext.getUserId(), txt, jid, cid);

		// FIXME Guard

		CidBody body = new CidBody();
		body.setCid(bo.getId());

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}
}

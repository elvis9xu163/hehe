package com.xjd.hehe.dal.api.ctrlr.v10;

import io.netty.handler.codec.http.multipart.FileUpload;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.dal.api.req.basic.ReportReq;
import com.xjd.hehe.dal.api.util.RequestContext;
import com.xjd.hehe.dal.api.view.View;
import com.xjd.hehe.dal.api.view.ViewUtil;
import com.xjd.hehe.dal.api.view.body.UrlBody;
import com.xjd.hehe.dal.biz.service.BasicService;
import com.xjd.hehe.dal.biz.service.ResourceService;
import com.jkys.social.util.ValidUtil;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;
import com.xjd.nhs.HttpRequest;
import com.xjd.nhs.annotation.RequestBody;
import com.xjd.nhs.annotation.RequestMapping;
import com.xjd.nhs.annotation.RequestMapping.Method;

@Controller
@RequestMapping("/api/10")
public class BasicCtrl {

	@Autowired
	ResourceService resourceService;
	@Autowired
	BasicService basicService;

//	@Profiled
	@RequestMapping(value = "/upload", method = Method.POST, supportMultipart = true)
	public View upload(HttpRequest request) throws IOException {
		// 参数校验
		FileUpload upFile = null;
		List<FileUpload> upFiles = request.getUploadedFiles();
		if (upFiles != null && !upFiles.isEmpty()) {
			for (FileUpload uf : upFiles) {
				if ("file".equals(uf.getName())) {
					upFile = uf;
				}
			}
		}

		if (upFile == null) {
			throw new BizException(RespCode.RES_0020);
		}

		// 业务
		String url = resourceService.uploadImage(upFile);

		// 结果封装
		UrlBody body = new UrlBody();
		body.setUrl(url);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

//	@Profiled
	@RequestMapping("/report")
	public View report(@RequestBody ReportReq req) {
		ValidUtil.valid(req);

		basicService.report(RequestContext.checkAndGetUserBuddyId(), req.getTargetType(), req.getTargetId(), req.getContent());

		View view = ViewUtil.defaultView();

		return view;
	}
}

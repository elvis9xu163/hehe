package com.xjd.hehe.dal.api.ctrlr.v10;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;

import com.xjd.hehe.dal.api.req.Test1Req;
import com.xjd.hehe.dal.api.view.View;
import com.xjd.hehe.dal.api.view.ViewUtil;
import com.xjd.hehe.dal.api.view.body.Test1Body;
import com.xjd.hehe.dal.api.view.vo.Test1Vo;
import com.xjd.nhs.HttpRequest;
import com.xjd.nhs.annotation.RequestBody;
import com.xjd.nhs.annotation.RequestMapping;

/**
 * @author elvis.xu
 * @since 2015-08-27 22:43
 */
@Controller
@RequestMapping(value = "/api/10")
public class TestCtrl {

	@RequestMapping(value = "/test1")
	public View test1(@RequestBody Test1Req test1) {
		// 参数校验
		if (test1 == null) {
			// 参数校验
		}

		// 业务调用

		// 结果封装
		Test1Vo res = new Test1Vo();

		Test1Body body = new Test1Body();
		body.setResult(res);

		View view = ViewUtil.defaultView();
		view.setBody(body);

		return view;
	}

	@RequestMapping(value = "/test2", supportMultipart = true)
	public View test2(HttpRequest request) {
		try {
			request.getUploadedFiles().get(0).renameTo(new File("D:/tmp/tmp.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ViewUtil.defaultView();
	}

	@RequestMapping("/test3")
	public View test3() {
		return ViewUtil.defaultView();
	}
}

package com.xjd.hehe.api.ctrl.v10;

import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.view.View;
import com.xjd.nhs.annotation.RequestMapping;

/**
 * @author elvis.xu
 * @since 2015-10-24 22:02
 */
@Controller
@RequestMapping("/api/10")
public class TestCtrl {

	@RequestMapping("/test1")
	public View test1() {
		return null;
	}
}

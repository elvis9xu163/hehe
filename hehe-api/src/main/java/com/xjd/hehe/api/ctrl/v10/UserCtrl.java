package com.xjd.hehe.api.ctrl.v10;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.api.view.body.TokenBody;
import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.biz.service.UserService;
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
public class UserCtrl {
	@Autowired
	UserService userService;

	@RequestMapping("/signin")
	public View signin(@RequestParam("uname") String uname, @RequestParam("pwd") String pwd) {
		if (StringUtils.isNotBlank(uname)) {
			if (StringUtils.isBlank(pwd)) {
				throw new BizException(RespCode.RES_0012, new Object[]{"密码"});
			}
			// 手机号或邮箱
			ValidUtil.check(ValidUtil.UNAME, uname);
		}

		TokenBo tokenBo = userService.login(uname, pwd);

		TokenBody body = new TokenBody();
		body.setToken(tokenBo.getId());
		body.setSalt(tokenBo.getSalt());

		View view = ViewUtil.defaultView();
		view.setBody(body);
		return view;
	}
}

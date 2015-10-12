package com.xjd.hehe.dal.api.ctrlr.v10;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xjd.hehe.dal.api.TestBase;
import com.xjd.hehe.dal.api.req.Test1Req;

public class TestCtrlTest extends TestBase {
	@Autowired
	TestCtrl testCtrl;

	@Test
	public void test() throws Throwable {

		System.out.println(AopUtils.isAopProxy(testCtrl));
		System.out.println(AopUtils.getTargetClass(testCtrl));

		for (Method m : TestCtrl.class.getDeclaredMethods()) {
			if (m.getName().equals("test1")) {
				Method pm = AopUtils.getMostSpecificMethod(m, testCtrl.getClass());
				System.out.println(pm);
				AopUtils.invokeJoinpointUsingReflection(testCtrl, pm, new Object[] { new Test1Req() });
			}
		}

	}
}

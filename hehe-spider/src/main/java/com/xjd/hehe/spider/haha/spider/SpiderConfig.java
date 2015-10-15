package com.xjd.hehe.spider.haha.spider;

import com.xjd.hehe.utl.AppContext;

/**
 * @author elvis.xu
 * @since 2015-10-15 23:53
 */
public class SpiderConfig {
	public static int getMaxPage() {
		return Integer.parseInt(AppContext.getProperty("haha.grab.maxPage"));
	}
}

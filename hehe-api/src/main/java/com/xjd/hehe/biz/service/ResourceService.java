package com.xjd.hehe.biz.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author elvis.xu
 * @since 2015-10-31 15:14
 */
@Service
public class ResourceService {

	public String transUrlToInternal(String url) {
		url = StringUtils.trimToNull(url);
		if (url == null) {
			return null;
		}
		if (url.startsWith("http://139.129.13.123/")) {
			return url.substring("http://139.129.13.123".length());
		}
		return url;
	}

	public String transUrlToOutside(String url) {
		if (url == null) {
			return null;
		}
		if (!url.startsWith("http")) {
			return "http://139.129.13.123" + url;
		}
		return url;
	}
}

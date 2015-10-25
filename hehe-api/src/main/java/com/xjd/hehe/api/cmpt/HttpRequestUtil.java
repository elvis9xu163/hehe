package com.xjd.hehe.api.cmpt;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.xjd.nhs.HttpRequest;

/**
 * Http请求工具类
 * @author elvis.xu
 * @since 2015-02-16 15:16
 */
public abstract class HttpRequestUtil {

	/**
	 * 获取客户端真实IP(有代理的请况)
	 * @param request
	 * @return
	 */
	public static String getRealRemoteAddr(HttpRequest request) {
		String ip = request.getHeaders().get("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeaders().get("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeaders().get("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (request.getRemoteAddress() != null && request.getRemoteAddress() instanceof InetSocketAddress) {
				InetAddress ia = ((InetSocketAddress) request.getRemoteAddress()).getAddress();
				if (ia != null) {
					ip = ia.getHostAddress();
				}
			}
		}
		return ip;
	}
}

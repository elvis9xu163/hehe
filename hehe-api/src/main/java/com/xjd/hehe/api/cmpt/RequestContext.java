package com.xjd.hehe.api.cmpt;

import java.util.HashMap;
import java.util.Map;


/**
 * 请求上下文
 *
 * @author elvis.xu
 * @since 2014-12-1
 */
public class RequestContext {

	private static final String KEY_USER_IP = "user.ip";
	private static final String KEY_USER_ID = "user.id";
	private static final String KEY_USER_TOKEN = "user.token";
	private static final String KEY_API_NAME = "service.name";
	private static final String KEY_API_VER = "service.ver";
	private static final String KEY_APP_AGENT = "app.agent";
	private static final String KEY_END_MODEL = "end.model";
	private static final String KEY_END_SYS = "end.sys";
	private static final String KEY_END_ID = "end.id";
	private static final String KEY_APP_VER = "app.ver";

	protected static ThreadLocal<Map<String, Object>> requestThreadLocal = new ThreadLocal<Map<String, Object>>();

	private RequestContext() {
	}

	public static Map<String, Object> get() {
		Map<String, Object> map = requestThreadLocal.get();
		if (map == null) {
			map = new HashMap<String, Object>();
			requestThreadLocal.set(map);
		}
		return map;
	}

	public static void clear() {
		requestThreadLocal.remove();
	}

	public static void put(String key, Object val) {
		get().put(key, val);
	}

	public static Object get(String key) {
		return get().get(key);
	}

	public static String getAsString(String key) {
		return (String) get(key);
	}

	public static Integer getAsInteger(String key) {
		return (Integer) get(key);
	}

	public static void putUserId(String val) {
		put(KEY_USER_ID, val);
	}


	public static String getUserId() {
		return getAsString(KEY_USER_ID);
	}

	public static void putUserIp(String val) {
		put(KEY_USER_IP, val);
	}

	public static String getUserIp() {
		return getAsString(KEY_USER_IP);
	}

	public static void putUserToken(String val) {
		put(KEY_USER_TOKEN, val);
	}

	public static String getUserToken() {
		return getAsString(KEY_USER_TOKEN);
	}

	public static void putApiName(String val) {
		put(KEY_API_NAME, val);
	}

	public static String getApiName() {
		return getAsString(KEY_API_NAME);
	}

	public static void putApiVer(Integer val) {
		put(KEY_API_VER, val);
	}

	public static Integer getApiVer() {
		return getAsInteger(KEY_API_VER);
	}

	public static void putAppAgent(String val) {
		put(KEY_APP_AGENT, val);
	}

	public static String getAppAgent() {
		return getAsString(KEY_APP_AGENT);
	}

	public static void putEndModel(String val) {
		put(KEY_END_MODEL, val);
	}

	public static String getEndModel() {
		return getAsString(KEY_END_MODEL);
	}

	public static void putEndSys(String val) {
		put(KEY_END_SYS, val);
	}

	public static String getEndSys() {
		return getAsString(KEY_END_SYS);
	}

	public static void putEndId(String val) {
		put(KEY_END_ID, val);
	}

	public static String getEndId() {
		return getAsString(KEY_END_ID);
	}

	public static void putAppVer(Integer val) {
		put(KEY_APP_VER, val);
	}

	public static Integer getAppVer() {
		return getAsInteger(KEY_APP_VER);
	}

}

package com.xjd.hehe.dal.itg;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkys.social.util.AppContext;

/**
 * 推送服务
 * 
 * @author elvis.xu
 * @since 2015-6-9
 */
@Service
public class PushItg {
	private static Logger log = LoggerFactory.getLogger(PushItg.class);
	
	public static final String PUSH_API_SETMSG = "push.api.setMsg";
	public static final String PUSH_API_ADDUSER = "push.api.addUser";
	public static final String PUSH_API_SENDMSG = "push.api.sendMsg";

	static final String CODE_SUCCESS = "0000";
	static ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	AppContext sysConfig;

	public void sendToUser(Byte app, String title, String content, Map args, List<Integer> userIdList) {
		Long msgId = setMsg(null, app, (byte) 1, title, content, args);
		addUser(msgId, userIdList);
		sendMsg(msgId);
	}
	
	/**
	 * <pre>
	 * 原子接口--设置消息任务
	 * </pre>
	 * @param msgId
	 * @param app
	 * @param targetType
	 * @param title
	 * @param content
	 * @param args
	 * @return
	 */
	public Long setMsg(Long msgId, Byte app, Byte targetType, String title, String content, Map args) {
		Map param = new HashMap();
		param.put("msgId", msgId);
		param.put("pushApp", app);
		param.put("targetType", targetType);
		param.put("msgType", 1);
		param.put("priority", 10);
		param.put("msgTitle", title);
		param.put("msgText", content);
		param.put("msgUrl", null);
		param.put("msgInfo", args);

		Map res = sendData(sysConfig.getProperty(PUSH_API_SETMSG), toJson(param));
		return ((Integer) res.get("msgId")).longValue();
	}

	/**
	 * <pre>
	 * 原子接口--添加用户
	 * </pre>
	 * @param msgId
	 * @param userIdList
	 */
	public void addUser(Long msgId, List<Integer> userIdList) {
		Map param = new HashMap();
		param.put("msgId", msgId);
		param.put("userIdList", userIdList);

		sendData(sysConfig.getProperty(PUSH_API_ADDUSER), toJson(param));
	}

	/**
	 * <pre>
	 * 原子接口--确认发送消息
	 * </pre>
	 * @param msgId
	 */
	public void sendMsg(Long msgId) {
		Map param = new HashMap();
		param.put("msgId", msgId);
		param.put("mode", 1);
		param.put("time", null);

		try {
			sendData(sysConfig.getProperty(PUSH_API_SENDMSG), toJson(param));
		} catch (Exception e) {
			log.error("推送信息失败, msgId=" + msgId, e);
		}
	}

	protected String toJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	protected <T> T fromJson(String json, Class<T> clazz) {
		try {
			return objectMapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected Map sendData(String api, String data) {
		// 创建默认的客户端实例
		HttpClient httpClient = new DefaultHttpClient();

		// 创建get请求实例
		HttpPost post = new HttpPost(api);
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf8");
		HttpEntity entity = new ByteArrayEntity(data.getBytes(Charset.forName("utf8")));
		post.setEntity(entity);

		try {
			log.info("请求推送API开始.......: api={}, entity={}", api, data);
			long start = System.currentTimeMillis();

			HttpResponse response = httpClient.execute(post);

			long cost = System.currentTimeMillis() - start;
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String res = EntityUtils.toString(response.getEntity(), Charset.forName("utf8"));
				log.info("请求推送API结束[{}]: api={}, entity={}, result={}", StringUtils.leftPad(cost + "", 5), api, data, res);

				try {
					Map result = fromJson(StringUtils.trim(res), Map.class);
					if (CODE_SUCCESS.equals(result.get("code"))) {
						return result;
					} else {
						log.warn("请求推送API返回结果非成功: api={}, entity={}, result={}", api, data, res);
						throw new RuntimeException("请求推送API返回结果非成功");
					}
				} catch (Exception e) {
					if (e instanceof RuntimeException) {
						throw (RuntimeException) e;
					}
					log.warn("请求推送API返回结果无法解析: api=" + api + ", entity=" + data + ", result=" + res, e);
					throw new RuntimeException("请求推送API返回结果无法解析", e);
				}

			} else {
				log.warn("请求推送API失败[{}]: api={}, entity={}, result={}", StringUtils.leftPad(cost + "", 5), api, data,
						response.getStatusLine());
				throw new RuntimeException("请求推送API失败");
			}

		} catch (IOException e) {
			log.error("请求推送异常: api=" + api + ", entity=" + data, e);
			throw new RuntimeException("请求推送异常", e);

		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
}

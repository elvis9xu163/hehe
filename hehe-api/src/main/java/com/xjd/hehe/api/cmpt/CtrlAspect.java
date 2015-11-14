package com.xjd.hehe.api.cmpt;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.handler.codec.http.multipart.FileUpload;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.xjd.hehe.api.view.View;
import com.xjd.hehe.api.view.ViewUtil;
import com.xjd.hehe.biz.bo.TokenBo;
import com.xjd.hehe.biz.service.ApiService;
import com.xjd.hehe.biz.service.ReqLogService;
import com.xjd.hehe.biz.service.UserService;
import com.xjd.hehe.utl.AppContext;
import com.xjd.hehe.utl.DateUtil;
import com.xjd.hehe.utl.JsonUtil;
import com.xjd.hehe.utl.ValidUtil;
import com.xjd.hehe.utl.exception.BizException;
import com.xjd.hehe.utl.respcode.RespCode;
import com.xjd.nhs.HttpRequest;
import com.xjd.nhs.context.RequestHolder;

@Component
@Aspect
@Order(1)
public class CtrlAspect {
	private static final Logger log = LoggerFactory.getLogger(CtrlAspect.class);

	@Autowired
	ReqLogService reqLogService;
	@Autowired
	ApiService apiService;
	@Autowired
	UserService userService;

	@Around("within(com.xjd.hehe.api.ctrl.*.*) && @annotation(com.xjd.nhs.annotation.RequestMapping)")
	protected Object aroudAdivce(ProceedingJoinPoint jp) throws Throwable {
		HttpRequest request = RequestHolder.get();
//		Log4JStopWatch stopWatch = new Log4JStopWatch();
		try {
			// 请求数据解析
			String[] param = request.getRequestUri().split("/");
			String ver = param[param.length - 2];
			String api = param[param.length - 1];
			String userIp = HttpRequestUtil.getRealRemoteAddr(request);
			String appAgent = request.getHeaders().get("appAgent");
			if (appAgent != null) {
				appAgent = URLDecoder.decode(appAgent, "UTF-8");
			}
			RequestContext.putApiName(api);
			RequestContext.putApiVer(Integer.valueOf(ver));
			RequestContext.putUserIp(userIp);
			RequestContext.putAppAgent(appAgent);

			String paramString = paramString(request);
			String fixLogString = "appAgent=" + appAgent + ", ver=" + ver + ", api=" + api + ", ip=" + userIp
					+ ", param=" + paramString;

			log.debug("请求开始.......: {}", fixLogString);
			logHeaders(request);

			View rt = null;
			long start = System.currentTimeMillis();
			try {
				Throwable t = null;
				// == 版本信息 == //
				try {
					parseAppAgent(appAgent);
				} catch (Exception e) {
					t = e;
				}

				// == TOKEN == //
				try {
					String token = getParameter(request, "token");
					RequestContext.putUserToken(token);
					if (token != null) {
						TokenBo tokenBo = userService.checkAndGetToken(token);
						RequestContext.putUserId(tokenBo.getUid());
					}
				} catch (Exception e) {
					if (t == null) {
						t = e;
					}
				}

				// == 接口请求日志 == //
				reqLogService.log(RequestContext.getApiVer(), RequestContext.getApiName(), RequestContext.getUserIp(), RequestContext.getEndId(), RequestContext.getEndModel(), RequestContext.getEndSys(), RequestContext.getAppVer(), RequestContext.getUserToken());

				if (t != null) { // 日志完了可以抛出异常了
					throw t;
				}

				// == 其它公共参数校验 == //
				String time = getParameter(request, "time");
				ValidUtil.check(ValidUtil.TIME, time);
				// FIXME sign校验

				// == FIXME 版本升级控制 == //

				// == TODO API状态控制 == //
				if (AppContext.isApi()) {
					if ("[preupload][upload]".contains(RequestContext.getApiName())) {
						throw new BizException(RespCode.RES_9970);
					}
				} else {
					if (!"[preupload][upload]".contains(RequestContext.getApiName())) {
						throw new BizException(RespCode.RES_9970);
					}
				}


				rt = (View) jp.proceed();
			} catch (Throwable t) {
				if (t instanceof BizException) {
					BizException be = (BizException) t;
					rt = ViewUtil.defaultView(be.getCode(), be.getArgs(), be.getMsg(), be.getOriginalCode(), be.getOrginalMsg());
					if (be.getCause() != null) {
						log.error("请求异常: " + fixLogString, t);
						// TODO 给管理员发邮件
					} else {
						log.warn("请求异常: {}, businessException={}", fixLogString, t);
					}
				} else {
					rt = ViewUtil.defaultView(RespCode.RES_9999);
					log.error("请求异常: " + fixLogString, t);
					// TODO 给管理员发邮件
				}
			} finally {
				RequestContext.clear();
			}
			long cost = System.currentTimeMillis() - start;

			if (rt == null) {
				log.info("请求结束[{}]: {}, result={}", StringUtils.leftPad(cost + "", 5), fixLogString, null);
				return null;
			}
			rt = ViewUtil.builder(rt).service(api).version(ver).timestamp(DateUtil.now()).build();
			if (log.isDebugEnabled()) {
				log.debug("请求结束[{}]: {}, result={}", StringUtils.leftPad(cost + "", 5), fixLogString,
						JsonUtil.toStringIncludeIgnoredProperties(rt, DateUtil.PATTERN_YEAR2MILLISECOND));
			} else if (log.isInfoEnabled()) {
				log.info("请求结束[{}]: {}, result={}", StringUtils.leftPad(cost + "", 5), fixLogString,
						JsonUtil.toStringIncludeIgnoredProperties(rt, DateUtil.PATTERN_YEAR2MILLISECOND));
			}

			return rt;
		} finally {
//			stopWatch.stop("api_" + request.getRequestUri());
		}
	}

	protected String paramString(HttpRequest request) {
		if (request.isCustomBody()) {
			if (request.getBody() != null && request.getBody().length > 0) {
				return new String(request.getBody(), Charset.forName("UTF-8"));
			} else {
				return "";
			}
		} else {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			for (Map.Entry<String, List<String>> entry : request.getParameters().entrySet()) {
				Object val = unlist(entry.getValue());
				if (AppContext.isEnvProduct() && AppContext.isPwdField(entry.getKey())) {
					paramMap.put(entry.getKey(), AppContext.getPwdMask());
				} else {
					paramMap.put(entry.getKey(), val);
				}
			}
			if (request.getUploadedFiles() != null) {
				for (FileUpload fu : request.getUploadedFiles()) {
					paramMap.put(fu.getName(), "[FILE]:" + fu.getFilename());
				}
			}
			return JsonUtil.toString(paramMap, DateUtil.PATTERN_YEAR2MILLISECOND);
		}
	}

	protected void logHeaders(HttpRequest request) {
		if (log.isTraceEnabled()) {
			log.trace("-----------Headers-----------");
			if (request.getHeaders() != null) {
				for (String name : request.getHeaders().names()) {
					log.trace("{}={}", name, request.getHeaders().get(name));
				}
			}
		}
	}

	protected void parseAppAgent(String appAgent) {
		if (appAgent == null) {
			throw new BizException(RespCode.RES_9970);
		}
		String[] agentParts = StringUtils.split(appAgent, ';');
		if (agentParts.length != 2) {
			throw new BizException(RespCode.RES_9970);
		}
		String[] endParts = StringUtils.split(agentParts[0].trim(), '/');
		if (endParts.length != 3) {
			throw new BizException(RespCode.RES_9970);
		}
		String[] appParts = StringUtils.split(agentParts[1].trim(), '/');
		if (appParts.length != 2 || !"hehe".equals(appParts[0])) {
			throw new BizException(RespCode.RES_9970);
		}

		RequestContext.putEndModel(endParts[0]);
		RequestContext.putEndSys(endParts[1]);
		RequestContext.putEndId(endParts[2]);
		try {
			RequestContext.putAppVer(Integer.valueOf(appParts[1]));
		} catch (NumberFormatException e) {
			throw new BizException(RespCode.RES_9970, e);
		}
	}

	protected String getParameter(HttpRequest request, String key) {
		List<String> vals = request.getParameters().get(key);
		if (vals == null || vals.isEmpty()) {
			return null;
		}
		if (vals.size() > 1) {
			throw new BizException(RespCode.RES_0010, new Object[]{key});
		}
		return vals.get(0);
	}

	protected Object unlist(List<String> list) {
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return list;
	}

}

package com.xjd.hehe.dal.api.cmpnt;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.xjd.hehe.dal.api.req.BaseReq;
import com.xjd.hehe.dal.api.util.HttpRequestUtil;
import com.xjd.hehe.dal.api.util.RequestContext;
import com.xjd.hehe.dal.api.view.View;
import com.xjd.hehe.dal.api.view.ViewUtil;
import com.xjd.hehe.dal.biz.bo.TokenBo;
import com.xjd.hehe.dal.biz.service.AuthService;
import com.jkys.social.util.AppContext;
import com.jkys.social.util.DateUtil;
import com.jkys.social.util.JsonUtil;
import com.jkys.social.util.enums.AppTypeEnum;
import com.jkys.social.util.exception.BizException;
import com.jkys.social.util.respcode.RespCode;
import com.xjd.nhs.HttpRequest;
import com.xjd.nhs.context.RequestHolder;

@Component
@Aspect
@Order(1)
public class CtrlAspect {
	private static final Logger log = LoggerFactory.getLogger(CtrlAspect.class);

	@Autowired
	AuthService authService;

	@Around("within(com.jkys.social.api.ctrlr.*.*) && @annotation(com.xjd.nhs.annotation.RequestMapping)")
	protected Object aroudAdivce(ProceedingJoinPoint jp) throws Throwable {
		HttpRequest request = RequestHolder.get();
//		Log4JStopWatch stopWatch = new Log4JStopWatch();
		try {
			String[] param = request.getRequestUri().split("/");
			String version = param[param.length - 2];
			String service = param[param.length - 1];
			String userIp = HttpRequestUtil.getRealRemoteAddr(request);
			String appAgent = request.getHeaders().get("appAgent");
			if (appAgent != null) {
				appAgent = URLDecoder.decode(appAgent, "UTF-8");
			}
			RequestContext.putServiceName(service);
			RequestContext.putServiceVersion(version);
			RequestContext.putUserIp(userIp);
			RequestContext.putAppAgent(appAgent);

			String paramString = paramString(request);
			String fixLogString = "appAgent=" + appAgent + ", version=" + version + ", service=" + service + ", ip=" + userIp
					+ ", param=" + paramString;
			log.debug("请求开始.......: {}", fixLogString);
			logHeaders(request);

			View rt = null;
			long start = System.currentTimeMillis();
			try {
				// TODO LOG日志

				// chr控制
				String chr = (String) getParameter("chr", jp, request);
				chr = StringUtils.trimToNull(chr);
				if (chr == null || (!"clt".equals(chr) && !"con".equals(chr))) {
					throw new BizException(RespCode.RES_9970);
				}

				// == 版本信息 == //
				parseAppAgent(appAgent);
				// TODO 版本控制

				// == TOKEN和用户 == //
				String token = (String) getParameter("token", jp, request);
				token = StringUtils.trimToNull(token);
				if (token != null && !"anonymous".equals(token)) {
					TokenBo tokenBo = authService.getTokenByToken(token);
					if (tokenBo == null) {
						throw new BizException(RespCode.RES_0101);
					}
					RequestContext.putUserId(tokenBo.getUserId());
					RequestContext.putUserBuddyId(tokenBo.getBuddyId());
				}

				// == 公用参数校验 == //
				Object timestampObj = getParameter("timestamp", jp, request);
				Long timestamp;
				try {
					timestamp = timestampObj == null ? null : timestampObj instanceof Long ? (Long) timestampObj : Long
							.valueOf((String) timestampObj);
				} catch (Exception e) {
					throw new BizException(RespCode.RES_0010, new Object[] { "timestamp", timestampObj });
				}
				if (timestamp == null) {
					throw new BizException(RespCode.RES_0012, new Object[] { "timestamp" });
				}
				rt = (View) jp.proceed();
			} catch (Throwable t) {
				if (t instanceof BizException) {
					BizException be = (BizException) t;
					rt = ViewUtil.defaultView(be.getCode(), be.getArgs(), be.getMsg(), be.getOriginalCode(), be.getOrginalMsg());
					if (be.getCause() != null) {
						log.error("请求异常: " + fixLogString, t);
					} else {
						log.warn("请求异常: {}, businessException={}", fixLogString, t);
					}
				} else {
					rt = ViewUtil.defaultView(RespCode.RES_9999);
					log.error("请求异常: " + fixLogString, t);
				}
			} finally {
				RequestContext.clear();
			}
			long cost = System.currentTimeMillis() - start;

			if (rt == null) {
				log.info("请求结束[{}]: {}, result={}", StringUtils.leftPad(cost + "", 5), fixLogString, null);
				return null;
			}
			rt = ViewUtil.builder(rt).service(service).version(version).timestamp(DateUtil.now()).build();
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
			return JsonUtil.toString(paramMap, DateUtil.PATTERN_YEAR2MILLISECOND);
		}
	}

	protected Object unlist(List<String> list) {
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return list;
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
			if (AppContext.isEnvProduct()) {
				throw new BizException(RespCode.RES_9970);
			}
		}
		if (StringUtils.indexOfIgnoreCase(appAgent, "IOS") != -1) {
			RequestContext.putAppType(AppTypeEnum.IOS.getCode());
		} else if (StringUtils.indexOfIgnoreCase(appAgent, "Android") != -1) {
			RequestContext.putAppType(AppTypeEnum.ANDROID.getCode());
		} else {
			if (AppContext.isEnvProduct()) {
				throw new BizException(RespCode.RES_9970);
			}
		}

		int i;
		if ((i = StringUtils.lastIndexOf(appAgent, '/')) != -1) {
			String ver = appAgent.substring(i + 1);
			RequestContext.putAppVersion(ver);
		} else {
			if (AppContext.isEnvProduct()) {
				throw new BizException(RespCode.RES_9971);
			}
		}
	}

	protected Object getParameter(String name, ProceedingJoinPoint jp, HttpRequest request) {
		List<String> list = request.getParameters().get(name);
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				return list;
			} else {
				return list.get(0);
			}
		}
		BaseReq baseReq = null;
		if (jp.getArgs() != null) {
			for (Object arg : jp.getArgs()) {
				if (arg instanceof BaseReq) {
					baseReq = (BaseReq) arg;
					break;
				}
			}
		}
		if (baseReq != null) {
			Field f = ReflectionUtils.findField(baseReq.getClass(), name);
			if (f != null) {
				f.setAccessible(true);
				return ReflectionUtils.getField(f, baseReq);
			}
		}
		return null;
	}

}

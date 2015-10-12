package com.xjd.hehe.dal.api.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * 
 * @author elvis.xu
 * @since 2015-6-5
 */
@Component
public class I18NUtil {
	private static Logger log = LoggerFactory.getLogger(I18NUtil.class);

	public static final String DEFAULT_MESSAGE = "Unknown Code";
	protected static I18NUtil instance;

	@Autowired
	MessageSource messageSource;

	public I18NUtil() {
		instance = this;
	}

	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		String s = instance.messageSource.getMessage(code, args, defaultMessage, locale);
		if (s == null) {
			log.warn("cannot find message for code [{}], use default message.", code);
			s = DEFAULT_MESSAGE;
		}
		return s;
	}

	public static String getMessage(String code, Object[] args, String defaultMessage) {
		return getMessage(code, args, defaultMessage, Locale.getDefault());
	}
}

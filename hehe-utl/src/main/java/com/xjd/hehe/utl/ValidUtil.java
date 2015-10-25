package com.xjd.hehe.utl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.MapUtils;

import com.xjd.hehe.utl.exception.BizException;
import com.xjd.hehe.utl.respcode.RespCode;
import com.xjd.hehe.utl.valid.ValidBean;
import com.xjd.hehe.utl.valid.ValidProps;

public abstract class ValidUtil implements ValidProps {
	public static final String VALID_METHOD = "validate";
	public static Validator validator;
	protected static ThreadLocal<ValidBean> beanThreadLocal = new ThreadLocal<ValidBean>();
	protected static Map<String, Method> setterMap = new HashMap<String, Method>();
	static {
		ValidatorFactory vfactory = Validation.buildDefaultValidatorFactory();
		validator = vfactory.getValidator();
		Method[] methods = ValidBean.class.getMethods();
		for (Method m : methods) {
			String mn = m.getName();
			if (mn.startsWith("set") && mn.length() > 3 && m.getReturnType().equals(Void.TYPE) && m.getParameterTypes().length == 1
					&& m.getParameterTypes()[0].equals(String.class)) { // ...
				setterMap.put(mn.substring(3, 4).toLowerCase() + mn.substring(4), m);
			}
		}
	}

	public static void check(Object obj) {
		Set<ConstraintViolation<Object>> set = validator.validate(obj);
		if (set != null && !set.isEmpty()) {
			for (ConstraintViolation<Object> c : set) {
				throw new BizException(c.getMessage(), new Object[] { c.getPropertyPath(), c.getInvalidValue() });
			}
		}
		Method m = null;
		try {
			m = obj.getClass().getMethod(VALID_METHOD, null);
		} catch (NoSuchMethodException e) {
			// do nothing
		} catch (SecurityException e) {
			throw new RuntimeException("cannot access '" + VALID_METHOD + "' method for class '" + obj.getClass() + "'");
		}
		if (m != null) {
			try {
				m.invoke(obj, null);
			} catch (Exception e) {
				if (e instanceof InvocationTargetException && e.getCause() instanceof BizException) {
					throw (BizException) e.getCause();
				} else {
					throw new RuntimeException("invoke method '" + VALID_METHOD + "' method for class '" + obj.getClass()
							+ "' fail", e);
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void check(String... array) {
		Map map = MapUtils.putAll(new HashMap(), array);
		check(map);
	}

	public static void check(Map<String, String> map) {
		Set<ConstraintViolation<ValidBean>> set = validOneByOne(map);
		if (!set.isEmpty()) {
			@SuppressWarnings("rawtypes")
			ConstraintViolation[] cv = set.toArray(new ConstraintViolation[set.size()]);
			throw new BizException(cv[0].getMessage(), new Object[]{cv[0].getPropertyPath(), cv[0].getInvalidValue()});
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean valid(String... array) {
		Map map = MapUtils.putAll(new HashMap(), array);
		return valid(map);
	}

	public static boolean valid(Map<String, String> map) {
		Set<ConstraintViolation<ValidBean>> set = validOneByOne(map);
		if (!set.isEmpty()) {
			return false;
		}
		return true;
	}

	public static Set<ConstraintViolation<ValidBean>> validOneByOne(Map<String, String> map) {
		ValidBean bean = getValidBean();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String prop = entry.getKey();
			Method m = setterMap.get(prop);
			try {
				m.invoke(bean, entry.getValue());
			} catch (Exception e) {
				throw new BizException(RespCode.RES_9999);
			}
			Set<ConstraintViolation<ValidBean>> set = validator.validateProperty(bean, prop);
			if (!set.isEmpty()) {
				return set;
			}
		}
		return Collections.<ConstraintViolation<ValidBean>>emptySet();
	}

	public static Set<ConstraintViolation<ValidBean>> validAll(Map<String, String> map) {
		Set<ConstraintViolation<ValidBean>> allSet = new TreeSet<ConstraintViolation<ValidBean>>();
		ValidBean bean = getValidBean();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String prop = entry.getKey();
			Method m = setterMap.get(prop);
			try {
				m.invoke(bean, entry.getValue());
			} catch (Exception e) {
				throw new BizException(RespCode.RES_9999);
			}
			Set<ConstraintViolation<ValidBean>> set = validator.validateProperty(bean, prop);
			allSet.addAll(set);
		}
		return allSet;
	}

	protected static ValidBean getValidBean() {
		ValidBean bean = beanThreadLocal.get();
		if (bean == null) {
			bean = new ValidBean();
			beanThreadLocal.set(bean);
		}
		return bean;
	}
}

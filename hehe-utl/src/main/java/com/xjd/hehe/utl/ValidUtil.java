package com.xjd.hehe.utl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.xjd.hehe.utl.exception.BizException;

public abstract class ValidUtil {
	public static final String VALID_METHOD = "validate";
	public static Validator validator;
	static {
		ValidatorFactory vfactory = Validation.buildDefaultValidatorFactory();
		validator = vfactory.getValidator();
	}

	public static void valid(Object obj) {
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
}

package com.xjd.hehe.utl.constraint;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;

public abstract class EnumValid {

	public static final String VALID_METHOD_NAME = "validCode";

	public static boolean valid(Class<? extends Enum> enumClass, Object value) {
		if (value == null) {
			return false;
		}

		Method validMethod = null;
		for (Method m : enumClass.getDeclaredMethods()) {
			if (VALID_METHOD_NAME.equals(m.getName()) && ClassUtils.isAssignable(m.getReturnType(), boolean.class)) {
				Class<?>[] paramTypes = m.getParameterTypes();
				if (paramTypes.length == 1 && ClassUtils.isAssignable(value.getClass(), paramTypes[0])) {
					validMethod = m;
					break;
				}
			}
		}

		if (validMethod == null) {
			throw new RuntimeException(String.format("No valid method '%s(%s)' for enum '%s'.", VALID_METHOD_NAME, value
					.getClass().getName(), enumClass.getName()));
		}

		try {
			return (Boolean) validMethod.invoke(enumClass, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

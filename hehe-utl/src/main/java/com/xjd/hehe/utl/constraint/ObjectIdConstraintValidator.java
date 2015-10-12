package com.xjd.hehe.utl.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * 
 * </pre>
 * 
 * @author elvis.xu
 * @since 2014-9-18
 */
public class ObjectIdConstraintValidator implements ConstraintValidator<ObjectId, Object> {

	@Override
	public void initialize(ObjectId constraintAnnotation) {

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) return true;
		if (!(value instanceof String)) return false;
		String hexString = (String) value;
		int len = hexString.length();
		if (len != 24) {
			return false;
		}

		for (int i = 0; i < len; i++) {
			char c = hexString.charAt(i);
			if (c >= '0' && c <= '9') {
				continue;
			}
			if (c >= 'a' && c <= 'f') {
				continue;
			}
			if (c >= 'A' && c <= 'F') {
				continue;
			}

			return false;
		}

		return true;
	}

}

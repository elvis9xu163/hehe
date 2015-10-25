package com.xjd.hehe.utl.valid.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

public class TimestampConstraintValidator implements ConstraintValidator<Timestamp, String> {

	@Override
	public void initialize(Timestamp constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		try {
			java.util.Date rt = new java.util.Date(Long.parseLong(value));

		} catch (Exception e) {
			return false;
		}

		return true;
	}

}

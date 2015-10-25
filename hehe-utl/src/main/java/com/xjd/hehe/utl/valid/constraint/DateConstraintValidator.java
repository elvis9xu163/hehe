package com.xjd.hehe.utl.valid.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.xjd.hehe.utl.DateUtil;


public class DateConstraintValidator implements ConstraintValidator<Date, String> {

	private String pattern;

	@Override
	public void initialize(Date constraintAnnotation) {
		Date.DatePattern pat = constraintAnnotation.pattern();
		pattern = pat.getValue();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.isEmpty(value)) {
			return true;
		}

		try {
			java.util.Date rt = DateUtil.parse(value, pattern);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

}

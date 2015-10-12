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
public class EnumConstraintValidator implements ConstraintValidator<Enum, Object> {

	private Class enumClass;

	@Override
	public void initialize(Enum constraintAnnotation) {
		this.enumClass = constraintAnnotation.enumClass();
		if (enumClass == null) {
			throw new IllegalArgumentException("To use constraint Enum, 'enumClass' must be set.");
		}
		if (!java.lang.Enum.class.isAssignableFrom(enumClass)) {
			throw new IllegalArgumentException("To use constraint Enum, 'enumClass' must be class of enums.");
		}
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return EnumValid.valid(enumClass, value);
	}

}

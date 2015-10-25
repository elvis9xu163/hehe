package com.xjd.hehe.utl.valid.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.xjd.hehe.utl.respcode.RespCode;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {TimestampConstraintValidator.class})
public @interface Timestamp {

	String message() default RespCode.RES_0010;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}

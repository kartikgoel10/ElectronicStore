package com.electronic.store.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD ,ElementType.FIELD})
@Constraint(validatedBy = ImageNameValidator.class)	
public @interface ImageNameValid {

	String message() default "Invalid image name";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}

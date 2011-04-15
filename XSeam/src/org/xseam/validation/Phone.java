package org.xseam.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * Annotation that takes two parameters for telephone number validation
 * 
 * @author Rafael Benevides
 * 
 */
@Retention(RUNTIME)
@Target( { METHOD, FIELD })
@ValidatorClass(PhoneValidator.class)
public @interface Phone {
	String message() default "{xseam.validation.phone.message}";

	String regexPattern() default "{xseam.validation.phone.regexPattern}";
}

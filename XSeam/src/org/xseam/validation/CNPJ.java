package org.xseam.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * CNPJ Number Validator annotation
 * 
 * @author Rafael Benevides
 * 
 */
@Retention(RUNTIME)
@Target( { METHOD, FIELD })
@ValidatorClass(CNPJValidator.class)
public @interface CNPJ {
	String message() default "{xseam.validation.cnpj.message}";
}

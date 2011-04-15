package org.xseam.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

/**
 * CPF validator annotation
 * 
 * @author Rafael Benevides
 * 
 */
@Retention(RUNTIME)
@Target( { METHOD, FIELD })
@ValidatorClass(CPFValidator.class)
public @interface CPF {

	String message() default "{xseam.validation.cpf.message}";

}

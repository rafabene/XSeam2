package org.xseam.validation;

import java.lang.annotation.Annotation;

import org.hibernate.validator.Validator;

/**
 * This is a Base class to use with all XSeam custom validators.
 * It returns valid if null object is passed or an empty String is passed
 * 
 * @author Rafael Benevides
 *
 * @param <A>
 */
public abstract class AbstractStringValidator<A extends Annotation> implements Validator<A> {

    public void initialize(A Annotation) {
    }

    /**
     * This method check for empty values and delegates calls to
     * boolean validate(java.lang.String value)
     */
    public boolean isValid(Object value) {
        if (isEmpty(value)) {
            return true;
        }

        if (!(value instanceof String)) {
            return false;
        }

        return validate(value.toString());
    }

    private boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }

        if (value instanceof String && (value.toString()).trim().length() == 0) {
            return true;
        }

        return false;
    }

    /**
     * Must be implemented on sub classes
     * @param value
     * @return
     */
    protected abstract boolean validate(String value);
}
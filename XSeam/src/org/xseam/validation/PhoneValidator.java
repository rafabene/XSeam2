package org.xseam.validation;

import java.util.regex.Pattern;

/**
 * Validate phone number using the specified regex pattern in @Phone annotation
 * 
 * @author Rafael Benevides
 *
 */
public class PhoneValidator extends AbstractStringValidator<Phone> {

	private static final long serialVersionUID = 1L;

	private Pattern pattern;

	@Override
	public void initialize(Phone value) {
		pattern = Pattern.compile(value.regexPattern());
	}

	@Override
	public boolean validate(String value) {
		return pattern.matcher(value).matches();
	}
	
}
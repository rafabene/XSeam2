package org.xseam.validation;

import java.util.regex.Pattern;

/**
 * Validator class that validates CPF number. It can be with or without mask.
 * 
 * @author Rafael Benevides
 * 
 */
public class CPFValidator extends AbstractStringValidator<CPF> {

	private static final long serialVersionUID = 1L;

	private Pattern pattern;

	@Override
	public void initialize(CPF value) {
		pattern = Pattern.compile("[0-9]{11}");
	}

	@Override
	public boolean validate(String value) {
		boolean valid;
		int soma1, soma2, d1, d2;

		value = value.trim().replace(".", "").replace("-", "");

		valid = pattern.matcher(value).matches();

		if (valid) {
			soma1 = soma2 = 0;
			for (int i = 0; i < 9; i++) {
				d1 = value.charAt(i) - '0';
				soma1 += d1 * (10 - i);
				soma2 += d1 * (11 - i);
			}

			d1 = soma1 % 11;
			if (d1 < 2) {
				d1 = 0;
			} else {
				d1 = 11 - d1;
			}

			soma2 += d1 * 2;
			d2 = soma2 % 11;
			if (d2 < 2) {
				d2 = 0;
			} else {
				d2 = 11 - d2;
			}

			valid = (value.charAt(9) - '0') == d1
					&& (value.charAt(10) - '0') == d2;
		}
		return valid;
	}
}
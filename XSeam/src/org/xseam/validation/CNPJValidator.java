package org.xseam.validation;

import java.util.regex.Pattern;

/**
 * This class validates CNPJ Number with or without mask
 * 
 * @author Rafael Benevides
 * 
 */
public class CNPJValidator extends AbstractStringValidator<CNPJ> {

	private Pattern pattern;

	@Override
	public void initialize(CNPJ Annotation) {
		pattern = Pattern.compile("[0-9]{14}");
	}

	@Override
	public boolean validate(String value) {
		boolean valid;
		int soma1, soma2, d1, d2, i, j, k, c;

		value = value.trim().replace(".", "").replace("-", "").replace("/", "");

		valid = pattern.matcher(value).matches();

		if (valid) {
			soma2 = soma1 = 0;
			for (i = 11, j = 2, k = 3; i >= 0; i--) {
				c = value.charAt(i) - '0';
				soma1 += c * j;
				soma2 += c * k;
				j = (j + 1) % 10;
				if (j == 0) {
					j = 2;
				}
				k = (k + 1) % 10;
				if (k == 0) {
					k = 2;
				}
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

			valid = value.charAt(12) - '0' == d1
					&& value.charAt(13) - '0' == d2;
		}
		return valid;
	}
}

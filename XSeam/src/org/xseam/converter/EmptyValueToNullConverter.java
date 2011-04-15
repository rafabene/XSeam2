package org.xseam.converter;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

/**
 * Defines Seam Component Name for this converter
 * 
 * @author Rafael Benevides
 *
 */
@Converter
@Name("org.xseam.emptyValueToNullConverter")
@BypassInterceptors
public class EmptyValueToNullConverter extends AbstractConverter {

	private static final long serialVersionUID = 1L;

	/**
	 * Returns the String valor if it isn't null
	 */
	@Override
	public Object getAsObject(String valor) {
		return valor;
	}
}
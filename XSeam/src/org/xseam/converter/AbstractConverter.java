package org.xseam.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang.StringUtils;

/**
 * This is a Base Class for Converter that automatically converts all empty
 * String to null and abstract all other params
 * 
 * @author Rafael Benevides
 * 
 */
public abstract class AbstractConverter implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	private static final String noSelectionValue = "org.jboss.seam.ui.NoSelectionConverter.noSelectionValue";

	/**
	 * Returns null object if an empty String was passed and call
	 * getAsObject(java.lang.String valor) on this class
	 */
	public Object getAsObject(FacesContext ctx, UIComponent ui, String valor) {
		if (StringUtils.isEmpty(valor)) {
			return null;
		}

		if (valor != null && valor.equals(noSelectionValue)) {
			return null;
		}

		return getAsObject(valor);
	}

	/**
	 * Must be implemented on subclasses
	 * 
	 * @param valor
	 * @return
	 */
	protected abstract Object getAsObject(String valor);

	/**
	 * Calls getAsString(valor) on this class
	 */
	public String getAsString(FacesContext ctx, UIComponent ui, Object valor) {
		return getAsString(valor);
	}

	/**
	 * Returns Object toString()
	 * 
	 * @param valor
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String getAsString(Object valor) {
		return valor.toString();
	}

}

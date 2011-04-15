package org.xseam.domain;

import java.io.Serializable;

public interface Domain extends Serializable{

	/**
	 * Gets the value of the codigo property.
	 * 
	 */
	public Object getId();

	/**
	 * Gets the value of the descricao property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getDescription();

}

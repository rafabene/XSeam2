package org.xseam.facelets;

import org.apache.commons.lang.StringUtils;

/**
 * This class is used inside Facelets Components as functions
 * 
 * @author Rafael Benevides
 *
 */
public class FaceletsFunctions {

	/**
	 * This function analyse the Value passed and if it is null, returns de Default Value
	 * 
	 * @param valueToTest
	 * @param defaultValue Default value to return 
	 * @return the defaultValue if valueToTest is null
	 */
    public static Object getDefaultValue(Object valueToTest, Object defaultValue) {
        Object retorno = (valueToTest == null ? defaultValue : valueToTest);
        return retorno;
    }

    /**
     * This class concats two Strings
     * 
     * @param a 
     * @param b
     * @return (a + b) 
     */
    public static String concat(String a, String b) {
        return StringUtils.join(new String[] { a, b }, null);
    }
    
    public static Object getValueIfNotEmpty(Object valueToTest, Object valueToReturn){
        Object retorno = (valueToTest != null ? valueToReturn : null);
        return retorno;
    }

}

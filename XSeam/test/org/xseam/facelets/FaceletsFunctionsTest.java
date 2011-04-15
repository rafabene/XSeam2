package org.xseam.facelets;

import org.testng.annotations.Test;


public class FaceletsFunctionsTest {

    @Test
    public void getDefaultValueNull() throws Exception {
        String valor = (String) FaceletsFunctions.getDefaultValue(null, "ABC");
        assert valor.equals("ABC") : "Default String [ABC] would be returned, but was returned : " + valor;
    }
    
    @Test
    public void getDefaultValueNonNull() throws Exception {
        String valor = (String) FaceletsFunctions.getDefaultValue("XYZ", "ABC");
        assert valor.equals("XYZ") : "String [XYZ] would be returned, but was returned: " + valor;
    }
    
    @Test
    public void concat() throws Exception {
        String valor = FaceletsFunctions.concat("ABC","XYZ");
        assert valor.equals("ABCXYZ") : "Would be returned [ABCXYZ], but was returned " + valor;
    }
    
    @Test
    public void concatNull() throws Exception {
        String valor = FaceletsFunctions.concat(null,"XYZ");
        assert valor.equals("XYZ") : "Would be returned [XYZ], but was returned " + valor;
    }
    
    @Test
    public void valueIfNotEmptyPassedValue() throws Exception{
        String clientOrder =  "name";
        Object valorRetornado = FaceletsFunctions.getValueIfNotEmpty(clientOrder, true); 
        assert valorRetornado != null && valorRetornado.getClass().equals(Boolean.class) : "Boolean value (true) was excepcted, but " + valorRetornado + " was returned"; 
    }

    @Test
    public void valueIfNotEmptyPassedNullValue() throws Exception{
        String clientOrder = null;
        Object valorRetornado = FaceletsFunctions.getValueIfNotEmpty(clientOrder, true); 
        assert valorRetornado == null  : "Null value was excepected, but " + valorRetornado + " was returned"; 
        
    }
}

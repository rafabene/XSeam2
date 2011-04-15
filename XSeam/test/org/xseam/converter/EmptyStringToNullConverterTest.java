package org.xseam.converter;

import org.testng.annotations.Test;
import org.xseam.converter.EmptyValueToNullConverter;

public class EmptyStringToNullConverterTest {

        @Test
        public void nullString() throws Exception{
            EmptyValueToNullConverter esnc = new EmptyValueToNullConverter();
            assert esnc.getAsObject(null,null,"") == null: "Empty String was not converted to null";
        }
        
        @Test
        public void nonNullString() throws Exception{
            EmptyValueToNullConverter esnc = new EmptyValueToNullConverter();
            assert esnc.getAsObject("ASX").equals("ASX"): "Error converting non empty String";
        }

}

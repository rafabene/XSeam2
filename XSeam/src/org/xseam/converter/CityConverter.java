package org.xseam.converter;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.xseam.domain.City;
import org.xseam.domain.CityHelpper;

@Converter
@BypassInterceptors
@Name("org.xseam.cityConverter")
public class CityConverter extends AbstractConverter {


    private static final long serialVersionUID = 1L;

    @Override
    public String getAsString(Object value) {
        if (value instanceof City) {
            City cidade = (City) value;
            return String.valueOf(cidade.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(String value) {
        int cityId = Integer.parseInt(value);
        return  CityHelpper.getCity(cityId);
    }

}

package org.xseam.framework;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.xseam.domain.City;
import org.xseam.domain.CityHelpper;
import org.xseam.domain.State;

@Name("org.xseam.selecItemsCity")
@Scope(ScopeType.CONVERSATION)
@BypassInterceptors
@AutoCreate
public class SelectItemsCity implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<City> cities = new ArrayList<City>();

    public List<City> getCities(State selectedState) {
        updateCities(selectedState);
        return cities;
    }

    private void updateCities(State selectedState) {
        if (selectedState != null) {
            cities = CityHelpper.getCities(selectedState);
        } else {
            cities.clear();
        }
    }

}

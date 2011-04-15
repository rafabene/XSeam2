package org.xseam.converter;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.faces.Converter;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.xseam.domain.State;
import org.xseam.domain.StateHelpper;

@Converter
@BypassInterceptors
@Name("org.xseam.stateConverter")
public class StateConverter extends AbstractConverter {

    private static final long serialVersionUID = 1L;
    
    @Override
    public String getAsString(Object value) {
        if (value instanceof State) {
            State state = (State) value;
            return String.valueOf(state.getId());
        }
        return null;
    }

    @Override
    public Object getAsObject(String value) {
        return StateHelpper.getState(value);
    }

}

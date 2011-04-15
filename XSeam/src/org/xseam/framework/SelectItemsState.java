package org.xseam.framework;

import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Unwrap;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.xseam.domain.State;
import org.xseam.domain.StateHelpper;

@Name("org.xseam.selecItemsStates")
@Scope(ScopeType.APPLICATION)
@BypassInterceptors
@AutoCreate
public class SelectItemsState {
    
    @Unwrap
    public List<State> getUfs(){
        return StateHelpper.getStates();
    }

}

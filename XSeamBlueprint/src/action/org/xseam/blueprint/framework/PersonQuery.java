package org.xseam.blueprint.framework;

import org.jboss.seam.annotations.Name;
import org.xseam.blueprint.model.Person;
import org.xseam.framework.XSeamEntityQuery;

@Name("personQuery")
public class PersonQuery extends XSeamEntityQuery<Person> {

    private static final long serialVersionUID = 1L;
    

    @Override
    public Integer getMaxResults() {
        return 20;
    }
}

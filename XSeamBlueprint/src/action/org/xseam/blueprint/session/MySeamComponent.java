package org.xseam.blueprint.session;

import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.framework.EntityController;
import org.xseam.blueprint.model.Person;

@Name("mySeamComponent")
public class MySeamComponent extends EntityController {

    
    private static final long serialVersionUID = 1L;
    
    @Out
    private List<Person> people;
    
    @Factory(value="people")
    public void peopleFactory(){
        people = new ArrayList<Person>();
        Person p1 = new Person();
        p1.setId(1L);
        p1.setName("Rafael");
        Person p2 = new Person();
        p2.setId(2L);
        p2.setName("Gabriel");
        Person p3 = new Person();
        p3.setId(3L);
        p3.setName("João Paulo");
        
        people.add(p1);
        people.add(p2);
        people.add(p3);
    }
    
}

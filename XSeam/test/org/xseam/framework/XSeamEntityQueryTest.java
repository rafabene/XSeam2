package org.xseam.framework;

import org.jboss.seam.annotations.Name;
import org.testng.annotations.Test;
import org.xseam.model.BaseEntity;

public class XSeamEntityQueryTest{

    
    @Test
    public void persistenceClass() {
        T1 t1 = new T1();
        assert t1.getPersistenceClass().equals(Entity1.class) : "No correct persistenceClass returned";
        T2 t2 = new T2();
        assert t2.getPersistenceClass().equals(Entity2.class) : "No correct persistenceClass returned";
        assert ! t1.getPersistenceClass().equals(t2.getPersistenceClass()) : "Same persistenceClass returned for diferent classes";
    }
    
    
    private static class Entity1 extends BaseEntity{
        private static final long serialVersionUID = 1L;
    }
     
    private static class Entity2 extends BaseEntity{
        private static final long serialVersionUID = 1L;
        
    }
    
    @Name("t1")
    public static class T1  extends XSeamEntityQuery<Entity1>{
        private static final long serialVersionUID = 1L;
        
    }


    public static class T2  extends XSeamEntityQuery<Entity2>{
        private static final long serialVersionUID = 1L;
        
    }

}

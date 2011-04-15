package org.xseam.framework;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Interpolator;
import org.jboss.seam.core.SeamResourceBundle;
import org.jboss.seam.framework.EntityQuery;
import org.xseam.model.AbstractBaseEntity;

/**
 * This class extends Jboss Seam EntityQuery and automatically reads the passed
 * entity annotations to mount restrictions based on @QueryFilter. This feature
 * can be disabled by overwriting useQueryFilterAnnotation() method on
 * subclasses and returning false.
 * 
 * It also creates the EjbQL based on Entity class Name and reads order and
 * firstResult from Request parameter to do server side pagination and
 * ordenation
 * 
 * @author Rafael Benevides
 * 
 * @param <T>
 */
public abstract class XSeamEntityQuery<T extends AbstractBaseEntity> extends EntityQuery<T> {

    private static final long serialVersionUID = 1L;
    private static final MessageFormat MF_LIKE_START = new MessageFormat("lower({0}) like lower( concat(#'{'{1}'}','''%''') )");
    private static final MessageFormat MF_EQUALS = new MessageFormat("{0} = #'{'{1}'}'");

    private Class<? extends AbstractBaseEntity> persistenceClazz = null;
    private boolean restrictionsCreated = false;
    private boolean useQueryFilterAnnotation = true;

    @RequestParameter
    private Integer firstResult;

    @RequestParameter
    private String order;

    /**
     * Mounts EjbQL based on PersistenceClass name
     */
    @Override
    public String getEjbql() {
        return "Select e from " + getPersistenceClass().getName() + " e";
    }

    /**
     * Returns firstResult from Request Parameter
     */
    @Override
    public Integer getFirstResult() {
        return firstResult;
    }

    /**
     * Returns order (is not null) from Request Parameter
     */
    public String getOrder() {
        return StringUtils.isEmpty(order) ? null : order;
    }

    /**
     * Return the persistence Class that was defined in this subclass
     * declaration
     * 
     * @return the PersistenceClass
     */
    @SuppressWarnings("unchecked")
    protected Class<? extends AbstractBaseEntity> getPersistenceClass() {
        if (persistenceClazz == null) {
            persistenceClazz = (Class<? extends AbstractBaseEntity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return persistenceClazz;
    }

    /**
     * Overwrites super class method to also reads EntityClass annotation
     */
    @Create
    @Override
    public void validate() {
        super.validate();
        if (!restrictionsCreated && useQueryFilterAnnotation()) {
            createRestrictionsByEntityAnnotation();
        }
    }

    private void createRestrictionsByEntityAnnotation() {
        Field[] atributtes = getPersistenceClass().getDeclaredFields();
        for (Field field : atributtes) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            boolean isJPATransient = field.isAnnotationPresent(Transient.class);
            boolean isTransient = Modifier.isTransient(field.getModifiers());
            boolean isCollection = field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class) ;
            if (!isJPATransient && !isStatic && !isTransient && !isCollection) {
                insertDefaultRestriction(field);
            }
        }
        restrictionsCreated = true;
    }

    private void insertDefaultRestriction(Field field) {
        String restriction = null;
        Object[] params = { field.getName(), getEntityComponentName() + "." + field.getName() };
        if (field.getType().equals(String.class)) {
            restriction = MF_LIKE_START.format(params);
        } else {
            restriction = MF_EQUALS.format(params);
        }

        // if we got a restriction
        if (restriction != null) {
            debug("creating restriction: {0}", restriction);
            getRestrictions().add(createValueExpression(restriction));
        }
    }

    private String getEntityComponentName() {
        Class<? extends AbstractBaseEntity> entityClass = getPersistenceClass();
        if (entityClass.isAnnotationPresent(Name.class)) {
            return entityClass.getAnnotation(Name.class).value();
        }
        throw new IllegalStateException(getPersistenceClass() + " does not have a @Name annotation");
    }

    /**
     * Returns the value that determines if EntityClassAnnotation must be used
     * to create restrictions
     * 
     * @return
     */
    public boolean useQueryFilterAnnotation() {
        return useQueryFilterAnnotation;
    }

    public void setUseQueryFilterAnnotation(boolean useQueryFilterAnnotation) {
        this.useQueryFilterAnnotation = useQueryFilterAnnotation;
    }

    public String getSummaryMessage() {
        String message = SeamResourceBundle.getBundle().getString("xseam.datatable.pageCount");
        return Interpolator.instance().interpolate(message, getActualPage(), this.getPageCount());
    }

    /**
     * Return Actual page (if getMaxResults was defined). This information is
     * based on getFirstResult
     * 
     * @return actual page
     * 
     * @see XSeamEntityQuery.getFirstResult
     */
    public Integer getActualPage() {
        if (getMaxResults() == null) {
            return null;
        } else {
            int currenteRecord = (getFirstResult() != null ? getFirstResult() + 1 : 1);
            int resultsPerPAge = getMaxResults();
            int currentPage = currenteRecord / resultsPerPAge;

            return currenteRecord % resultsPerPAge == 0 ? currentPage : currentPage + 1;
        }
    }

}

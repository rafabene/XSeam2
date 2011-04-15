package org.xseam.framework;

import java.util.ResourceBundle;

import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.core.Expressions;
import org.jboss.seam.core.SeamResourceBundle;
import org.jboss.seam.faces.Redirect;
import org.jboss.seam.framework.EntityHome;

/**
 * This class extends Jboss Seam EntityHome reading rid (Register ID) from
 * request parameter. It also cleans instance variable after remove and new
 * action. The new action newRegister was create do permit new Register criation
 * on Crud screen.
 * 
 * @author Rafael Benevides
 * 
 * @param <T>
 */
public abstract class XSeamEntityHome<T> extends EntityHome<T> {

	private static ResourceBundle bundle = SeamResourceBundle.getBundle();
	private static final long serialVersionUID = 1L;

	@RequestParameter
	private Long rid;

	private Long lastRecordRemoved;

	@Override
	/**
	 * Returns rid from Request Parameter if it was passed, or managed object id if rid is null
	 */
	public Object getId() {
		if (rid == null) {
			return super.getId();
		} else {
			return rid;
		}
	}

	/**
	 * Overwrites remove on super class to clear instance variable.
	 */
	@Override
	public String remove() {
		String s = super.remove();
		this.clearInstance();
		return s;
	}

	/**
	 * Remove selected element from DataTable
	 */
	public void removeFromDataTable() {
		if (rid != null && rid.equals(lastRecordRemoved)) {
			// Already processed
			return;
		}
		Redirect.instance().captureCurrentView();
		setId(rid);
		remove();
		lastRecordRemoved = rid;
		Redirect.instance().returnToCapturedView();
	}

	/**
	 * New action that clear the instance to reuse the same crud screen to
	 * create new Register
	 */
	public String newRegister() {
		this.clearInstance();
		return "new";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Expressions.ValueExpression getCreatedMessage() {
		return createValueExpression(bundle
				.getString("xseam.framework.createdMessage"));
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Expressions.ValueExpression getUpdatedMessage() {
		return createValueExpression(bundle
				.getString("xseam.framework.updatedMessage"));
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Expressions.ValueExpression getDeletedMessage() {
		return createValueExpression(bundle
				.getString("xseam.framework.deletedMessage"));
	}

}

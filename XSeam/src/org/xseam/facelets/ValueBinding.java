package org.xseam.facelets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import com.sun.facelets.FaceletContext;
import com.sun.facelets.FaceletException;
import com.sun.facelets.FaceletHandler;
import com.sun.facelets.el.VariableMapperWrapper;
import com.sun.facelets.tag.Tag;
import com.sun.facelets.tag.TagAttribute;
import com.sun.facelets.tag.TagConfig;
import com.sun.facelets.tag.TagHandler;
import com.sun.facelets.tag.jsf.ComponentConfig;
import com.sun.facelets.tag.jsf.ComponentHandler;
import com.sun.facelets.tag.ui.ComponentRef;
import com.sun.facelets.tag.ui.ComponentRefHandler;


public class ValueBinding extends TagHandler {

	/** The name of the new variable that this tag defines. */
	private final static Pattern METHOD_PATTERN = Pattern
			.compile("(\\w+)\\s*=\\s*(.+?)\\s*;\\s*");
	private final TagAttribute rendererType;
	private final TagAttribute componentType;
	private final TagAttribute methodBindings;
	private ComponentHandler componentHandler;

	/** * @param config */
	public ValueBinding(TagConfig config) {
		super(config);
		rendererType = getAttribute("rendererType");
		componentType = getAttribute("componentType");
		methodBindings = getAttribute("methodBindings");
		componentHandler = new ComponentRefHandler(new ComponentConfig() {
			/** * @see com.sun.facelets.tag.TagConfig#getNextHandler() */
			public FaceletHandler getNextHandler() {
				return ValueBinding.this.nextHandler;
			}

			public Tag getTag() {
				return ValueBinding.this.tag;
			}

			public String getTagId() {
				return ValueBinding.this.tagId;
			}

			/**
			 * * @see
			 * com.sun.facelets.tag.jsf.ComponentConfig#getComponentType()
			 */
			public String getComponentType() {
				return (componentType == null) ? ComponentRef.COMPONENT_TYPE
						: componentType.getValue();
			}

			/**
			 * * @see com.sun.facelets.tag.jsf.ComponentConfig#getRendererType()
			 */
			public String getRendererType() {
				return (rendererType == null) ? null : rendererType.getValue();
			}
		});
	}

	/**
	 * * @see
	 * com.sun.facelets.FaceletHandler#apply(com.sun.facelets.FaceletContext,
	 * javax.faces.component.UIComponent)
	 */
	public void apply(FaceletContext ctx, UIComponent parent)
			throws IOException, FacesException, FaceletException, ELException {
		VariableMapper origVarMap = ctx.getVariableMapper();
		try {
			VariableMapperWrapper variableMap = new VariableMapperWrapper(
					origVarMap);
			ctx.setVariableMapper(variableMap);
			if (methodBindings != null) {
				String value = (String) methodBindings.getValue(ctx);
				Matcher match = METHOD_PATTERN.matcher(value);
				while (match.find()) {
					String var = match.group(1);
					ValueExpression currentExpression = origVarMap
							.resolveVariable(var);
					if (currentExpression != null) {
						try {
							FunctionMethodData methodData = new FunctionMethodData(
									var, match.group(2).split("\\s+"));
							MethodExpression mexpr = buildMethodExpression(ctx,
									currentExpression.getExpressionString(),
									methodData);
							variableMap.setVariable(var,
									new MethodValueExpression(
											currentExpression, mexpr));
						} catch (Exception ex) {
							throw new FacesException(ex);
						}
					}
				}
			}
			componentHandler.apply(ctx, parent);
		} finally {
			ctx.setVariableMapper(origVarMap);
		}
	}

	private MethodExpression buildMethodExpression(FaceletContext ctx,
			String expression, FunctionMethodData methodData)
			throws NoSuchMethodException, ClassNotFoundException {
		return ctx.getExpressionFactory().createMethodExpression(ctx,
				expression, methodData.getReturnType(),
				methodData.getArguments());
	}

	@SuppressWarnings("rawtypes")
	private class FunctionMethodData {
		private String variable;
		private Class returnType;
		private Class[] arguments;

		FunctionMethodData(String variable, String[] types)
				throws ClassNotFoundException {
			this.variable = variable;
			if ("null".equals(types[0]) || "void".equals(types[0]))
				returnType = null;
			else
				returnType = Class.forName(types[0]);
			arguments = new Class[types.length - 1];
			for (int i = 0; i < arguments.length; i++)
				arguments[i] = Class.forName(types[i + 1]);
		}

		public Class[] getArguments() {
			return this.arguments;
		}

		
		@SuppressWarnings("unused")
		public void setArguments(Class[] arguments) {
			this.arguments = arguments;
		}

		public Class getReturnType() {
			return this.returnType;
		}

		@SuppressWarnings("unused")
		public void setReturnType(Class returnType) {
			this.returnType = returnType;
		}

		@SuppressWarnings("unused")
		public String getVariable() {
			return this.variable;
		}

		@SuppressWarnings("unused")
		public void setVariable(String variable) {
			this.variable = variable;
		}
	}

}

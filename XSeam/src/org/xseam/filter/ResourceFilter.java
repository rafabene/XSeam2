package org.xseam.filter;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.Filter;
import org.jboss.seam.web.AbstractFilter;

/**
 * This filter is responsible for return classpath resources. It is
 * automatically mapped to URL specified at PATH_FILTER. Only the allowed
 * extensions for images are granted for security reasons.
 * 
 * @author Rafael Benevides
 * 
 */
@Filter
@Scope(ScopeType.APPLICATION)
@Name("org.xseam.filter.resourceFilter")
@BypassInterceptors
@Install(precedence = Install.FRAMEWORK)
public class ResourceFilter extends AbstractFilter {

	static final String PATH_FILTER = "/xseam/resource/*";

	/**
	 * Adjust URL Filter to my resources
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		setUrlPattern(PATH_FILTER);
	}

	/**
	 * get Resource from classpath if it was allowed,
	 * If resource isn't allowed, an 403 (Forbidden) error is returned.
	 * If resource was not found, an 404 (Not Found) error is returned.
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (isMappedToCurrentRequestPath(request)) {
			String resource = getRequestedResource(req);
			if (allowedResource(resource)) {
				sendResource(resource, resp);
			} else {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		} else {
			chain.doFilter(request, response);
		}

	}

	/**
	 * Check resource extension to see if it is allowed
	 * @param resource
	 * @return
	 */
	private boolean allowedResource(String resource) {
		if (resource.toLowerCase().endsWith(".gif"))
			return true;
		if (resource.toLowerCase().endsWith(".png"))
			return true;
		return false;
	}

	/**
	 * Send the requested resource via HttpServletResponse
	 * 
	 * @param resource
	 * @param resp
	 * @throws IOException
	 */
	private void sendResource(String resource, HttpServletResponse resp)
			throws IOException {
		InputStream is = this.getClass().getResourceAsStream(resource);
		if (is == null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			int b = 0;
			while ((b = is.read()) != -1) {
				resp.getOutputStream().write(b);
			}
		}
		if (is != null) {
			is.close();
		}
		resp.getOutputStream().close();
	}

	/**
	 * Load requested resource from classpath
	 * 
	 * @param req
	 * @return
	 */
	private String getRequestedResource(HttpServletRequest req) {
		String pathRequest = req.getRequestURI();
		String pathContext = getServletContext().getContextPath();
		int pathContextLengh = pathContext.length();

		return pathRequest.substring(pathContextLengh + PATH_FILTER.length()
				- 2);
	}
}

package com.app.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.utils.Constants;

@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			if (request instanceof HttpServletRequest) {
				HttpServletRequest req = (HttpServletRequest) request;

				String uri = req.getRequestURI();

				final String contexPath = req.getContextPath();

				if (allwaysAllow(uri)) {
					chain.doFilter(request, response);
				}

				if (!isAllowed(req)) {
					if (!defaultAllowed(uri)) {
						((HttpServletResponse) response)
								.sendRedirect(contexPath + Constants.loginPage);
					} else {
						chain.doFilter(request, response);
					}
				} else {
					if (!defaultAllowed(uri)) {
						chain.doFilter(request, response);
					} else {
						((HttpServletResponse) response)
								.sendRedirect(contexPath + Constants.mainPage);
					}
				}
			}
		} catch (Exception e) {
			// Meter al logger
		}
	}

	private boolean isAllowed(HttpServletRequest request) {
		if (request.getSession().getAttribute(Constants.LOGGED) == null
				|| (Boolean) request.getSession()
						.getAttribute(Constants.LOGGED) == false) {
			return false;
		}
		return true;
	}

	public boolean defaultAllowed(String uri) {
		if (uri.indexOf(Constants.loginPage) > 0) {
			return true;
		}
		return false;
	}

	public boolean allwaysAllow(String uri) {
		if (uri.endsWith(".css.view") || uri.endsWith(".js.view")) {
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}

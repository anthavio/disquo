package com.anthavio.disquo.browser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author martin.vanek
 *
 */
public class AppKeysInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private DisquoSessionData disqus;

	/**
	 * Keep user on appkeys page until he enters app keys and initializes Disqus driver
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (disqus.getDriver() == null) {
			if (request.getRequestURI().indexOf("appkeys") == -1) {
				response.sendRedirect("appkeys");
			}
		}
		//request.getSession().getId()
		return true;
	}
	/*
		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
				ModelAndView modelAndView) throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
				throws Exception {
			// TODO Auto-generated method stub

		}
	*/
}

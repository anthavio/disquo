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
	 * 
	 * @param handler is the @Controller that will 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String appKeysPath = request.getContextPath() + "/disqus/appkeys";
		if (disqus.getDriver() == null) {
			if (request.getRequestURI().indexOf(appKeysPath) == -1) {
				response.sendRedirect(appKeysPath);
				return false; //false = stop processing and do NOT continute to controller
			}
		}
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

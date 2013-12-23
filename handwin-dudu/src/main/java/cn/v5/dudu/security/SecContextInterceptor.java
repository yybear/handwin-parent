package cn.v5.dudu.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.WebUtils;

import cn.v5.dudu.util.Constants;

/** 
 * @author qgan
 * @version 2013年12月18日 下午3:17:36
 */
public class SecContextInterceptor implements HandlerInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(SecContextInterceptor.class);
	private String redirectUrl = "/login.jsp?callback=";
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		LOG.debug("preHandle");
		HttpSession session = request.getSession(false);
		Cookie cookie = WebUtils.getCookie(request, Constants.TOKEN_KEY);
		if(session == null || cookie == null || session.getAttribute(cookie.getValue()) == null) {
			String url = request.getContextPath() + redirectUrl + ServletUriComponentsBuilder.fromRequest(request).build().encode();
			LOG.debug("redirectUrl is {}", url);
			response.sendRedirect(url);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}

package cn.v5.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.v5.ex.web.HttpServerException;
import cn.v5.ex.web.UnauthorizedException;

import com.google.common.collect.Maps;

/** 
 * @author qgan
 * @version 2014年2月20日 下午4:28:23
 */
public class HttpServerExceptionResolver extends SimpleMappingExceptionResolver {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		MappingJacksonJsonView view = new MappingJacksonJsonView();
		ModelAndView modelAndView = new ModelAndView();

		response.setContentType("application/json");
		if(ex instanceof UnauthorizedException) {
			String msg = "Unauthorized";
			if(StringUtils.isNotBlank(ex.getMessage())) {
				msg = ex.getMessage();
			}
			response.setStatus(401);
			response.setHeader("WWW-Authenticate", "Basic realm=\""+msg+"\"");
		} else if(ex instanceof HttpServerException) {
			HttpServerException e = (HttpServerException) ex;
			int status = e.getStatus();
			int errorCode = e.getErrno();
			String msg = e.getMessage();
			
			response.setStatus(status);
			Map attributes = Maps.newHashMap();
			attributes.put("result_msg", msg);
			attributes.put("result_code", errorCode);
			view.setAttributesMap(attributes);
			modelAndView.setView(view);
		} else {
			response.setStatus(500);
	        
		}
		return modelAndView;
	}
}
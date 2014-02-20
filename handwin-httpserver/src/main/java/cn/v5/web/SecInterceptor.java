package cn.v5.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import cn.v5.ex.web.UnauthorizedException;
import cn.v5.framework.Constants;
import cn.v5.framework.support.spring.ConfigurableInterceptor;

/** 
 * @author qgan
 * @version 2014年2月20日 上午11:05:26
 */
public class SecInterceptor extends ConfigurableInterceptor {
	private static final Logger log = LoggerFactory.getLogger(SecInterceptor.class);
	
	@Autowired
    private RedisTemplate<String, String> template;
	
	@Override
	public boolean internalPreHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String clientSession = request.getHeader(Constants.CLIENT_SESSION);
		log.debug("user client session is {}", clientSession);
		if(StringUtils.isBlank(clientSession))
			throw new UnauthorizedException();
		
		String userName = template.boundValueOps(Constants.USER_SESSION + clientSession).get();
		if(StringUtils.isBlank(userName)) {
			throw new UnauthorizedException();
		}
		
		return true;
	}
    
}

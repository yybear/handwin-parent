package cn.v5.dudu.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import cn.v5.dudu.util.Constants;
import cn.v5.framework.jdbc.JdbcDao;
import cn.v5.framework.jdbc.Record;
import cn.v5.framework.web.Page;

/** 
 * @author qgan
 * @version 2013年12月18日 下午2:36:27
 */
@Controller
public class AdminController {
	private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private JdbcDao dao;
	
	@RequestMapping(value="/console", method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		long start = Long.valueOf(request.getParameter("s") == null? "1": request.getParameter("s"));
		long limit = 20;
		
		Page<Record> feedbacks = dao.paginate(start, limit, "select * ", "from t_feed_back where status=0 and reply_at is null order by create_at desc");
		return new ModelAndView("console:index", "page", feedbacks);
	}
	
	@RequestMapping(value="/console/replied", method=RequestMethod.GET)
	public ModelAndView replied(HttpServletRequest request) {
		long start = Long.valueOf(request.getParameter("s") == null? "1": request.getParameter("s"));
		long limit = 20;
		
		Page<Record> feedbacks = dao.paginate(start, limit, "select * ", "from t_feed_back where status=0 and reply_at is not null order by reply_at desc");
		return new ModelAndView("console:reply", "page", feedbacks);
	}
	
	@RequestMapping("/console/feedback/remove/{fdId}")
	public void removeFd(@PathVariable("fdId")long feedbackId, HttpServletRequest request, HttpServletResponse response) {
		Record record = new Record();
		record.set("id", feedbackId);
		record.set("status", 1);
		dao.update("t_feed_back", record);
	}
	
	@RequestMapping(value="/console/feedback/reply", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reply(long fid, String content, HttpServletRequest request) {
		
		Record record = new Record();
		record.set("id", fid);
		record.set("reply_content", content);
		record.set("reply_at", new Date());
		
		HttpSession session = request.getSession(false);
		Cookie cookie = WebUtils.getCookie(request, Constants.TOKEN_KEY);
		Record user = (Record)session.getAttribute(cookie.getValue());
		record.set("reply_uid", user.getLong("id"));
		dao.update("t_feed_back", record);
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("errno", 0);
		return res;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public void login(String account, String passwd, HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("account is {}, passwd is {}", account, passwd);
		Record user = dao.findOne("select * from t_user where account=? and passwd=?", account, passwd);
		if(null != user) { // 用户登陆成功
			LOG.debug("login success");
			String token = DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
			Cookie cookie = new Cookie(Constants.TOKEN_KEY, token);
			response.addCookie(cookie);
			
			HttpSession session = request.getSession();
			session.setAttribute(token, user); // 放入session
			
			String url = request.getParameter("callback");
			if(StringUtils.isBlank(url) || "null".equals(url)) {
				url = request.getContextPath() + "/console"; // 默认控制台首页
			}
			LOG.debug("callback url is {}", url);
			try {
				response.sendRedirect(url);
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		} else {
			LOG.debug("login fail");
			try {
				response.sendRedirect(request.getContextPath() + "/login.jsp?r=1");
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		LOG.debug("logout");
		Cookie cookie = WebUtils.getCookie(request, Constants.TOKEN_KEY);
		if(cookie != null) {
			cookie.setMaxAge(0);
		}
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			try {
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		return;
	}
}

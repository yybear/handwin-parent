package cn.v5.dudu.web;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;

import cn.v5.dudu.util.Constants;
import cn.v5.dudu.util.Constants.Feedback_Status;
import cn.v5.framework.ex.CodeException;
import cn.v5.framework.jdbc.JdbcDao;
import cn.v5.framework.jdbc.Record;
import cn.v5.framework.web.Page;

/** 
 * @author qgan
 * @version 2013年12月21日 下午4:04:16
 */
@Controller
public class FeedbackController {
	private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private JdbcDao dao;
	
	@RequestMapping(value="/feedback/add", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(String account, String content, String code, HttpServletRequest request) {
		// 不正确不保存
		HttpSession session = request.getSession(false);
		String cd = (String)session.getAttribute("code");
		if(StringUtils.isEmpty(cd) || !cd.equalsIgnoreCase(code)) {
			throw new RuntimeException("验证码错误");
		}
			
		Record record = new Record();
		record.set("account", account);
		record.set("content", content);
		record.set("create_at", new Date());
		record.set("status", Feedback_Status.CREATED);
		dao.save("t_feed_back", record);
		
		Map<String, Object> res = Maps.newHashMap();
		res.put("errno", Constants.SUCCESS);
		return res;
	}
	
	@RequestMapping(value="/feedback/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request) {
		long start = Long.valueOf(request.getParameter("s") == null? "1": request.getParameter("s"));
		long limit = 20;
		
		Page<Record> feedbacks = dao.paginate(start, limit, "select * ", "from t_feed_back where status=0 order by create_at desc");
		Map<String, Object> res = Maps.newHashMap();
		return res;
	}
}

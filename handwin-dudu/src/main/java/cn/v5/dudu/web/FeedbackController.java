package cn.v5.dudu.web;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import cn.v5.dudu.util.Constants;
import cn.v5.dudu.util.Constants.Feedback_Status;
import cn.v5.framework.jdbc.JdbcDao;
import cn.v5.framework.jdbc.Record;

/** 
 * @author qgan
 * @version 2013年12月21日 下午4:04:16
 */
@Controller
public class FeedbackController {
	private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private JdbcDao dao;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(String account, String content, String code) {
		// TODO: 判断code, 不正确不保存
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
}

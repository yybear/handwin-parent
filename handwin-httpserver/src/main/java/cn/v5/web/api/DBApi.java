package cn.v5.web.api;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.v5.framework.jdbc.JdbcDao;
import cn.v5.framework.jdbc.Record;
import cn.v5.model.SendMsg;
import cn.v5.service.impl.MsgService;
import cn.v5.web.vo.DataView;

/** 
 * @author qgan
 * @version 2013年12月16日 下午7:01:14
 */

@Controller
@RequestMapping("/api/db")
public class DBApi {
	private static final Logger LOG = LoggerFactory.getLogger(DBApi.class);
	
	@Autowired
	private MsgService msgService;
	
	@RequestMapping(value="/getMembersByGroupId/{groupId}", method=RequestMethod.GET)
	@ResponseBody
	public DataView getMembersByGroupId(@PathVariable("groupId") String groupId) {
        //List<GroupMembers> membersList =  GroupMembers.find("groupId = ? and status = 0",groupId).fetch();
		LOG.debug(groupId);
		
		
		/*Record record = new Record();
		record.set("sender", "sender");
		record.set("receiver", "receiver");
		record.set("receiver_type", "1");
		record.set("receive_time", new Date());
		record.set("send_time", new Date());
		record.set("content", "content".getBytes());
		dao.save("t_send_msg", record);*/
		
		
		/*Record record2 = new Record();
		record2.set("id", 2);
		record2.set("sender", "ganqing");
		dao.update("t_send_msg", record2);
		Record record = dao.findById("t_send_msg", 2);*/
		
		Record record = new Record();
		record.set("sender", "sender");
		record.set("receiver", "receiver");
		record.set("receiver_type", "1");
		record.set("receive_time", new Date());
		record.set("send_time", new Date());
		record.set("content", "content".getBytes());
		
		List<Record> records = msgService.saveAndGetAll(record);
		DataView view = new DataView(0, records); 
		return view;
    }
	
	@RequestMapping(value="/sendMsg", method=RequestMethod.GET)
	@ResponseBody
	public DataView sendMsg() {
		SendMsg msg = new SendMsg();
	
		DataView view = new DataView(0, msg); 
		return view;
    }
}

package cn.v5.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.v5.framework.jdbc.JdbcDao;
import cn.v5.framework.jdbc.Record;

/** 
 * @author qgan
 * @version 2013年12月18日 上午10:19:15
 */
@Service
@Transactional
public class MsgServiceImpl implements MsgService {
	private static final Logger LOG = LoggerFactory.getLogger(MsgServiceImpl.class);
	@Autowired
	private JdbcDao dao;
	
	@Override
	public List<Record> saveAndGetAll(Record record) {
		long id = dao.save("t_send_msg", record);
		LOG.debug("saved id is id");
		return dao.find("select * from t_send_msg where id=?", id);
	}

}

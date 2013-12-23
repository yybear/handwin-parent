package cn.v5.service.impl;

import java.util.List;

import cn.v5.framework.jdbc.Record;

/** 
 * @author qgan
 * @version 2013年12月18日 上午10:18:07
 */
public interface MsgService {
	public List<Record> saveAndGetAll(Record record);
}

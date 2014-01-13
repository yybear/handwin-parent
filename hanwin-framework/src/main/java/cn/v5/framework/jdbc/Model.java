package cn.v5.framework.jdbc;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.google.common.collect.Maps;

/**
 * 目前只支持约定操作，如果tableName为t_+类名的小写下划线驼峰，字段就是字段的小写下划线驼峰
 * 
 * @author qgan
 * @version 2013年12月17日 上午11:05:40
 */
@SuppressWarnings("rawtypes")
public abstract class Model implements Serializable {

	private static final long serialVersionUID = -3347963022597705403L;

	private JdbcTemplate template;

	/**
	 * 缓存的sql.
	 */
	private static Map<String, String> SQL_CACHE = Maps.newConcurrentMap();

	public Model() {
		super();
	}

	/**
	 * 设置spring jdbcTemplate，以便数据库操作
	 * 
	 * @param template
	 */
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int save() {
		// 反射生成的效率有点低
		Class modelClazz = this.getClass();
		String tableName = DbHelper.getTableName(modelClazz.getSimpleName());
		StringBuilder sb = new StringBuilder("insert into `").append(tableName).append("` (");
		StringBuilder args = new StringBuilder();
		Field[] fields = modelClazz.getDeclaredFields();

		List<Object> list = new ArrayList<Object>();
		int i = 0;
		for (Field f : fields) {
			String fieldName = f.getName();
			if ("serialVersionUID".equals(fieldName))
				continue;
			Object fiedlValue = null;
			try {
				fiedlValue = f.get(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			list.add(fiedlValue);
			if (i > 0) {
				sb.append(",");
				args.append(",");
			}
			sb.append("`").append(DbHelper.getColumnName(fieldName))
					.append("`");
			args.append("?");
			i++;
		}
		sb.append(") values (").append(args).append(")");
		String sql = sb.toString();
		return this.template.update(sql, list.toArray());
	}

	public int delete(Long id) {
		Class modelClazz = this.getClass();
		String tableName = DbHelper.getTableName(modelClazz.getSimpleName());
		StringBuilder sb = new StringBuilder("delete from `").append(tableName).append("` where id=?");
		String sql = sb.toString();
		return this.template.update(sql, id);
	}

	/**
	 * 只更新非null值
	 * 
	 * @return
	 */
	public int update() {
		// 反射生成的效率有点低
		Class modelClazz = this.getClass();
		String tableName = DbHelper.getTableName(modelClazz.getSimpleName());
		StringBuilder sb = new StringBuilder("update `").append(tableName).append("` set ");
		Field[] fields = modelClazz.getDeclaredFields();

		List<Object> list = new ArrayList<Object>();
		Object id = null;
		int i = 0;
		for (Field f : fields) {
			String fieldName = f.getName();
			if ("serialVersionUID".equals(fieldName))
				continue;
			Object fiedlValue = null;
			try {
				fiedlValue = f.get(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			if(null == fiedlValue) // null值不更新
				continue;
			
			if ("id".equals(fieldName)) { // 设置id
				id = fiedlValue;
				continue;
			}
			list.add(fiedlValue);
			if (i > 0) {
				sb.append(",");
			}
			sb.append("`").append(DbHelper.getColumnName(fieldName))
					.append("`=?");
			i++;
		}
		sb.append(" where id=?");
		String sql = sb.toString();
		list.add(id);
		return this.template.update(sql, list.toArray());
	}

	public static <M extends Model>  M find(final Class<M> clazz, String sql, Object... params) {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		
		RowMapper<M> recordCallback = new RowMapper<M>() {
			
	        public M mapRow(ResultSet rs, int rowNum) throws SQLException {

	        	Record record = new Record();
	        	ResultSetMetaData rsmd = rs.getMetaData();
	        	int columnCount = rsmd.getColumnCount();
	        	for(int i = 1; i <= columnCount; i++) {
	        		Object value;
	        		int type = rsmd.getColumnType(i);
	        		if(type < Types.BLOB) {
	        			value = rs.getObject(i);
	        		} else if(type == Types.CLOB) {
	        			value = DbHelper.handleClob(rs.getClob(i));
	        		} else if(type == Types.NCLOB) {
	        			value = DbHelper.handleClob(rs.getNClob(i));
	        		} else if(type == Types.BLOB) {
	        			value = DbHelper.handleBlob(rs.getBlob(i));
	        		} else 
	        			value = rs.getObject(i);
	        		
	        		record.set(rsmd.getColumnLabel(i), value);
	        	}
	            return record;
	        }
	    };
		
		return this.template.query(sql, params, recordCallback);
	}
}

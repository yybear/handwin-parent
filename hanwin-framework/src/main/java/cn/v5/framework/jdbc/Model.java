package cn.v5.framework.jdbc;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Maps;

/** 
 * 目前只支持约定操作，如果tableName为t_+类名的小写下划线驼峰，字段就是字段的小写下划线驼峰
 * @author qgan
 * @version 2013年12月17日 上午11:05:40
 */
@SuppressWarnings("rawtypes")
public abstract class Model<M extends Model> implements Serializable {

	private static final long serialVersionUID = -3347963022597705403L;
	
	private JdbcTemplate template;
	
	/**
	 * 缓存的sql.
	 */
	//private static Map<String, String> SQL_CACHE = Maps.newConcurrentMap();
	
	public Model() {
		super();
	}
	
	/**
	 * 设置spring jdbcTemplate，以便数据库操作
	 * @param template
	 */
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int save() {
		// 反射生成的效率有点低
		/*Class modelClazz = this.getClass();
		String tableName = DbHelper.getTableName(modelClazz.getSimpleName());
		StringBuilder sb = new StringBuilder("insert into `").append(tableName).append("` (");
		StringBuilder args = new StringBuilder();
		Field[] fields = modelClazz.getDeclaredFields();
		
		List<Object> list = new ArrayList<Object>();
		int i = 0;
		for(Field f : fields) {
			String fieldName = f.getName();
			if("serialVersionUID".equals(fieldName))
				continue;
			Object fiedlValue = null;
			try {
				fiedlValue = f.get(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			list.add(fiedlValue);
			if(i > 0) {
				sb.append(",");
				args.append(",");
			}
			sb.append("`").append(DbHelper.getColumnName(fieldName)).append("`");
			args.append("?");
			i++;
		}
		sb.append(") values (").append(args).append(")");
		
		String sql = sb.toString();
		return this.template.update(sql, list.toArray());
		*/
		return 0;
	}
	
	public int delete(Long id) {
		return 0;
	}
	
	public int update() {
		return 0;
	}
	
	public M select(String sql, Object... params) {
		return null;
	}
	
}

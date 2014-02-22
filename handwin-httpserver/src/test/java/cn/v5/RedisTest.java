package cn.v5;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.v5.framework.Constants;
/** 
 * @author qgan
 * @version 2014年2月20日 下午6:45:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/applicationContext.xml")
@ActiveProfiles("test")
public class RedisTest {
	
	@Resource(name="userRedisTemplate")
	private ValueOperations<String, String> valueOps;
	
	String session = "09df401f395a310706f48acf28055fa7";
	@Test
	public void testRedis() {
		final String key = Constants.USER_SESSION + session;
		String nameMd5 = "";
		nameMd5 = valueOps.get(key);
		System.out.println(nameMd5);
	}
}

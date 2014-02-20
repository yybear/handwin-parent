package cn.v5.web.api;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.v5.ex.web.UnauthorizedException;
import cn.v5.framework.jdbc.JdbcDao;
import cn.v5.framework.jdbc.Record;
import cn.v5.util.SystemConstants;
import cn.v5.web.vo.User;


/** 
 * @author qgan
 * @version 2014年2月20日 下午5:07:25
 */
@Controller
@RequestMapping("/api/user")
public class UserApi {
	private static final Logger log = LoggerFactory.getLogger(UserApi.class);
	
	@Autowired
	private JdbcDao jdbcDao;
	
	@RequestMapping(value="/login")
	@ResponseBody
	public User login(String name, String password, Integer rflag, Integer app_id) {
		
        Record userRecord = jdbcDao.findOne("select * from User where name = ? and status = ?", name, SystemConstants.USER_STATUS_VALID);
        String salt = userRecord.getStr("passwd_salt");
		if (userRecord != null && (userRecord.getStr("password").equalsIgnoreCase(genPassword(password, salt)))) {
			//更新登录时间
            //handleAfterLogin(user, name, rflag,"",app_id==null?1:app_id);

			User user = new User();
			user.id = userRecord.getLong("id");
			user.name = userRecord.getStr("name");
			user.nameMd5 = userRecord.getStr("nameMd5");
            return user;
        } else {
            throw new UnauthorizedException("用户名或密码错误");
        }
	}
	
	private String genPassword(String password, String salt) {
		return DigestUtils.md5Hex(password + salt);
	}
}

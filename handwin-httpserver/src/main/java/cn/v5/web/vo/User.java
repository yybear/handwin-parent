package cn.v5.web.vo;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author qgan
 * @version 2014年2月20日 下午5:23:22
 */
public class User {
	public Long id;

	public String name;

	public String nameMd5;

	public String password;

	public String passwdSalt;

	public String nickname;

	public String mobile;

	public Integer sex;

	public String avatar;

	public String avatar_url;

	public Integer charm;

	public Timestamp createTime;

	public Timestamp lastLoginTime;

	public Long lastUpdateTime;

	public String regSource;

	public String signature;

	public String hideTime;

	public Integer userType;

	public String sessionId;
	public String tcp_server;

	public String status;

	public Integer appId;
}

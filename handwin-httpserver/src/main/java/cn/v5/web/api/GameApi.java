package cn.v5.web.api;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * @author qgan
 * @version 2014年2月20日 上午10:16:01
 */
@Controller
@RequestMapping("/api/game")
public class GameApi {
	
	/**
	 * 获取用户游戏的等级
	 * @param gameId
	 * @param userName
	 */
	@RequestMapping(value="/getUserLevel", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserLevel(Integer gameId, String userName) {
		return null;
	}
}

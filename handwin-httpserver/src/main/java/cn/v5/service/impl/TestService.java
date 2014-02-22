package cn.v5.service.impl;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/** 
 * @author qgan
 * @version 2014年2月21日 下午2:35:46
 */
@Validated
public interface TestService {
	public void test(@NotNull String name);
}

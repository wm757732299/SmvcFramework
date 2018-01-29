package com.wm.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

/**
 * 认证异常处理
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2016.09.24
 *
 */
public class AjaxLoginUrlAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {

	public AjaxLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	/**
	 * ajax的请求认证失败时，将session状态及重定向的url写入response头部，JS执行跳转操作 非ajax请求仍由服务端进行重定向
	 */
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if ("XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"))) {
			response.setHeader("sessionStatus", "timeout");
			response.setHeader("redirectUrl", this.getLoginFormUrl());
		} else {
			super.commence(request, response, authException);
		}
	}
}

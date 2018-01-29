package com.wm.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局拦截器
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2016.09.24
 *
 */
public class GlobalInterceptor implements HandlerInterceptor {

	/**
	 * Controller处理之前进行调用
	 */
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("*************1******************************************");
		//arg1.sendRedirect(arg0.getContextPath() + loginUrl);  
		return true;
	}

	/**
	 * 整个请求完成之后进行调用
	 */
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("*************2******************************************");

	}

	/**
	 * Controller处理之后进行调用
	 */
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		if(arg3!=null){
			ModelMap t = arg3.getModelMap();
			t.put("basePath", arg0.getContextPath());
		}
	}

}

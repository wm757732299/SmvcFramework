package com.wm.common.aspect;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wm.annotation.Validate;
import com.wm.annotation.Validate.Permission;
import com.wm.common.expection.PermissionExpection;
import com.wm.mapper.entity.SysUser;
import com.wm.model.LoginUserDetails;

/**
 * 权限验证
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2018.1.31
 *
 */
@Component
@Aspect
public class ValidateAspect {

	private static final Logger LOGGER = Logger.getLogger(ValidateAspect.class);

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	/**
	 * AOP切入点
	 * 
	 * @param jp
	 * @throws Throwable
	 *             [MyNotes_AloneRow_WMing] @ annotation表示标注了特定注解的目标方法连接点
	 */
	@Before("@annotation(com.wm.annotation.Validate)")
	public void doBefore(JoinPoint jp) throws Throwable {

		System.out.println("**************权限验证****************");

		String methodMapping = "";

		Method soruceMethod = null;
		try {
			soruceMethod = getSourceMethod(jp);
			Validate vali = soruceMethod.getAnnotation(Validate.class);
			if (vali != null) {
				Permission type = vali.permission();
				LoginUserDetails lud = getLoginer();

				if (type == Permission.SUPER) {
					if (SysUser.SYS_ADMIN.equals(lud.getId())) {
						return;
					} else {
						throw new PermissionExpection();
					}
				} else if (type == Permission.ORDINARY) {
					if (SysUser.SYS_ADMIN.equals(lud.getId())) {
						return;
					} else {
						String[] clazzPath = jp.getTarget().getClass().getAnnotation(RequestMapping.class).value();
						String[] methodPath = soruceMethod.getAnnotation(RequestMapping.class).value();
						methodMapping += clazzPath[0] + methodPath[0];
						
						String perStr =/* request.getParameter("actId") +*/ methodMapping;
						String[] actArr = lud.getAction();
						for (int i = 0; actArr != null && i < actArr.length; i++) {
							if (perStr.equals(actArr[i]))
								return;
						}
						throw new PermissionExpection();
					}
				}
			}
		} catch (Throwable e) {
			throw new PermissionExpection();
		}
		throw new PermissionExpection();
	}

	// @After(value = "@annotation(com.wm.annotation.Validate)")
	// public void after(){
	// System.out.println("************后置通知*********");
	// }

	// @AfterReturning(value =
	// "@annotation(com.wm.annotation.Validate)",returning="result")
	// public void afterR(JoinPoint jp,Object result){
	// System.out.println("************返回通知*********");
	// }

	// @AfterThrowing(pointcut="@annotation(com.wm.annotation.Validate)",
	// throwing = "e")
	// public void doAfterThrow(Throwable e) {
	// if(e instanceof PermissionExpection){
	// writeContent("您无权操作！");
	// }
	// }

	@Around(value = "@annotation(com.wm.annotation.Validate)")
	public Object around(ProceedingJoinPoint pjp) {
		Object result = null;
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			if (e instanceof PermissionExpection) {
				writeContent("您无权操作！");
			} else {
				LOGGER.info(e);
			}
		}
		return result;

	}

	private void writeContent(String content) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");

		PrintWriter writer = null;
		String msg = "{\"success\":\"false\",\"msg\":\"" + content + "\"}";
		try {
			writer = response.getWriter();
			writer.print(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	private Method getSourceMethod(JoinPoint jp) {
		Method proxyMethod = ((MethodSignature) jp.getSignature()).getMethod();
		try {
			return jp.getTarget().getClass()
					.getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public LoginUserDetails getLoginer() {
		LoginUserDetails userDetails = null;
		try {
			userDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDetails;
	}
}

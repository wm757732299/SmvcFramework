package com.wm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.SysAction;
import com.wm.mapper.entity.SysUser;
import com.wm.model.LoginUserDetails;
import com.wm.service.SysActionService;
import com.wm.service.SysMenuService;
import com.wm.service.SysUserService;

/**
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2017.12.06
 *
 */

@Controller
@RequestMapping(value = "/main")
public class MainController extends BaseController<SysUser> {
	private static final Logger LOGGER = Logger.getLogger(MainController.class);

	@Resource(type = SysMenuService.class)
	private SysMenuService sysMenuService;

	@Resource(type = SysUserService.class)
	private SysUserService sysUserService;

	@Resource(type = SysActionService.class)
	private SysActionService sysActionService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * 登录进管理端主页
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		String uAccount = request.getParameter("uAccount");
		String pwd = request.getParameter("uPwd");

		try {

			if (!StringUtils.isEmpty(uAccount) && !StringUtils.isEmpty(pwd)) {
				UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
						uAccount, pwd);
				// 调用loadUserByUsername
				Authentication authentication = authenticationManager
						.authenticate(authRequest); 
				SecurityContextHolder.getContext().setAuthentication(
						authentication);
				HttpSession session = request.getSession();
				// 这个非常重要，否则验证后将无法登陆
				session.setAttribute("SPRING_SECURITY_CONTEXT",
						SecurityContextHolder.getContext()); 
				result.put("success", "true");
				result.put("msg", "请求成功");
				result.put("data", "");
			} else {
				result.put("success", "false");
				result.put("msg", "请求失败");
				result.put("data", "");
				response.sendRedirect("");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/admin", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView adminHome(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		LoginUserDetails user = getLoginUser();
		 String loginerAction="";
		 try {
			 if(SysUser.SYS_ADMIN.equals(user.getId())){
				// loginerAction="true";
				 List<SysAction> action= sysActionService.queryLoginAct(user.getId());
				 ObjectMapper mapper = new ObjectMapper();
				 loginerAction = mapper.writeValueAsString(action);
			 }else{
				 List<SysAction> action= sysActionService.queryLoginAct(user.getId());
				 ObjectMapper mapper = new ObjectMapper();
				 loginerAction = mapper.writeValueAsString(action);
			 }
		} catch (JsonProcessingException e) {
			LOGGER.info(e);
		}
		result.put("uAccount", user.getuAccount());
		result.put("uName", user.getuName());
		result.put("userId", user.getId());
		result.put("loginerAction", loginerAction);
		return new ModelAndView("main/admin/home", result);
	}

	/**
	 * 首页
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/home_index", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView homeIndex(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		// response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return new ModelAndView("main/admin/home_index", result);
	}

	/**
	 * 退出登录 
	 * 此方法不用，退出操作在security配置文件中配置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	
	//=======================web端=============================//
	@ResponseBody
	@RequestMapping(value = "/web/index", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView webIndex(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
	 
		 
		
		return new ModelAndView("main/website/index", result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/web/checklogin", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> checkLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			LoginUserDetails lud = getLoginUser();
			
		//	if(lud.getId()=="userid")
			if(lud!=null){
				result.put("success", "true");
				result.put("msg", "请求成功");
				result.put("data", lud);
			}else{
				result.put("success", "false");
				result.put("msg", "请求失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	
	}
}

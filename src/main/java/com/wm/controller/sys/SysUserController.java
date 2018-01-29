package com.wm.controller.sys;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.SysUser;
import com.wm.model.LoginUserDetails;
import com.wm.service.SysUserService;

@Controller
@RequestMapping(value = "/sysuser")
public class SysUserController extends BaseController<SysUser> {

	private static final Logger LOGGER = Logger
			.getLogger(SysUserController.class);

	@Resource(type = SysUserService.class)
	private SysUserService sysUserService;

	/**
	 * 用户管理列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user_list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView userList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
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
		return new ModelAndView("main/admin/users/user_list", result);
	}

	@ResponseBody
	@RequestMapping(value = "/user_list_data", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> userListData(
			@RequestParam("curPage") int curPage,
			@RequestParam("pageSize") int pageSize, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Map<String, Object> param = getFormParam(request);
			param.put("curPage", curPage);
			param.put("pageSize", pageSize);
			param.put("roleLevel", getLoginUser().getRoleLevel());
			List<SysUser> data = sysUserService.queryUserList(param);
			Page<SysUser> page = (Page<SysUser>) data;
			Map<String, Object> pageInfo = getPageInfo(page);

			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", data);
			result.put("param", param);
			result.put("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;

	}

	/**
	 * 新增用户页
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add_user", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addUser(HttpServletResponse response,
			HttpServletRequest request) {
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
		return new ModelAndView("main/admin/users/add_user", result);
	}

	/**
	 * 编辑用户页
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit_user", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView editUser(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysUser u = null;
		try {

			u = sysUserService.queryByKey(id);
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", u);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return new ModelAndView("main/admin/users/edit_user", result);
	}

	/**
	 * 新增、编辑 保存
	 * 
	 * @param sysUser
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_user", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> saveUser(SysUser sysUser) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SysUser u = sysUserService.queryByKey(sysUser.getId());
			Timestamp tp = new Timestamp(new Date().getTime());
			sysUser.setTimeStamp(tp);
			if (u != null) {
				sysUser.setuAccount(null);
				sysUserService.update(sysUser);
			} else {
				if(sysUser.getuPwd()==null){
					sysUser.setuPwd("000000");
				}
				sysUser.setuType("A");
				sysUser.setuStatus("Y");
				sysUser.setCreateTime(tp);
				sysUserService.insert(sysUser);
			}
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/del_user", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> delUser(@RequestParam("userId") String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 不再事务内需改进
			String[] ids = userId.split(",");
			for (int i = 0; i < ids.length; i++) {
				SysUser sysUser = new SysUser();// sysUserService.queryByKey(userId);
				sysUser.setId(ids[i]);
				sysUserService.delete(sysUser);
			}
			result.put("success", "true");
			result.put("msg", "删除成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/personal_panel", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView selfInfo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			LoginUserDetails user = getLoginUser();
			result.put("success", "true");
			result.put("msg", "删除成功");
			result.put("data",user);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return new ModelAndView("main/admin/users/personal_panel", result);
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/edit_pic", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView editPic(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			 LoginUserDetails user = getLoginUser();
			
			System.out.println(getLoginUserId());
			result.put("success", "true");
			result.put("msg", "删除成功");
			result.put("data",user);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return new ModelAndView("main/admin/users/edit_pic", result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit_pic_sub", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> edit_pic_sub(HttpServletRequest req) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
		 
			result.put("success", "true");
			result.put("msg", "删除成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return result;
	}
}
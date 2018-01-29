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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.SysRole;
import com.wm.service.SysRoleService;

@Controller
@RequestMapping(value = "/sysrole")
public class SysRoleController extends BaseController<SysRole> {

	private static final Logger LOGGER = Logger
			.getLogger(SysRoleController.class);

	@Resource(type = SysRoleService.class)
	private SysRoleService sysRoleService;

	/**
	 * 角色管理列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/role_list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView userList(HttpServletRequest request) {
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
		return new ModelAndView("main/admin/users/role_list", result);
	}

	@ResponseBody
	@RequestMapping(value = "/role_list_data", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> roleListData(
			@RequestParam("curPage") int curPage,
			@RequestParam("pageSize") int pageSize, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Integer roleLevel = getLoginUser().getRoleLevel();

			Map<String, Object> param = getFormParam(request);
			param.put("curPage", curPage);
			param.put("pageSize", pageSize);
			param.put("loginRoleLevel", roleLevel);
			List<SysRole> data = sysRoleService.queryRoleList(param);
			Page<SysRole> page = (Page<SysRole>) data;
			Map<String, Object> pageInfo = getPageInfo(page);

			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", data);
			result.put("param", param);
			result.put("pageInfo", pageInfo);
			result.put("loginRoleLevel", roleLevel);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;

	}

	/**
	 * 保存
	 * 
	 * @param sysRole
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_role", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> saveRole(SysRole sysRole) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Integer roleLevel = getLoginUser().getRoleLevel();
			if (roleLevel < sysRole.getRoleLevel()) {
				SysRole sr = new SysRole();
				sr.setRoleName(sysRole.getRoleName());
				List<SysRole> list = sysRoleService.queryByCondition(sr);
				if (list == null || list.size() == 0) {
					sysRole.setRoleUnique(SysRole.ROLE_USER);
					sysRole.setTimeStamp(new Timestamp(new Date().getTime()));
					sysRoleService.insert(sysRole);
					result.put("success", "true");
					result.put("msg", "请求成功");
					result.put("data", "");
				} else {
					result.put("success", "false");
					result.put("msg", "保存失败，已存在该角色");
				}
			} else {
				result.put("success", "false");
				result.put("msg", "没有权限添加该角色");
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/del_role", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> delRole(@RequestParam("roleId") String roleId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] ids = roleId.split(",");
			if (ids.length > 0) {
				sysRoleService.batDelete(ids);
				result.put("success", "true");
				result.put("msg", "删除成功");
				result.put("data", "");
			} else {
				result.put("success", "false");
				result.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return result;
	}

	/**
	 * 编辑
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit_role", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> editRole(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysRole m = null;
		try {
			m = sysRoleService.queryByKey(id);
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", m);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	/**
	 * 编辑保存
	 * 
	 * @param sysMenu
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit_save", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> editSave(SysRole sysRole) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SysRole param = new SysRole();
			param.setRoleName(sysRole.getRoleName());
			List<SysRole> list = sysRoleService.queryByCondition(param);
			if (list != null && list.size() > 0) {
				SysRole role = null;
				for (SysRole sr : list) {
					if (sr.getId().equals(sysRole.getId())) {
						role = sr;
					} else {
						result.put("success", "false");
						result.put("msg", "保存失败，菜单名不能重复！");
						return result;
					}
				}
				if (role != null) {
					role.setRoleName(sysRole.getRoleName());
//					role.setRoleLevel(sysRole.getRoleLevel());
					role.setRemarks(sysRole.getRemarks());
					role.setTimeStamp(new Timestamp(new Date().getTime()));
					sysRoleService.update(role);
				}
			} else {
				SysRole role = sysRoleService.queryByKey(sysRole.getId());
				role.setRoleName(sysRole.getRoleName());
//				role.setRoleLevel(sysRole.getRoleLevel());
				role.setRemarks(sysRole.getRemarks());
				role.setTimeStamp(new Timestamp(new Date().getTime()));
				sysRoleService.update(role);
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

	/**
	 * 进入授权列表
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authorize_list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView userList(@RequestParam("roleId") String roleId,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", roleId);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return new ModelAndView("main/admin/users/authorize_list", result);
	}

	/**
	 * 授权用户列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authorize_user_list", method = {
			RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> authorizeUserList(
			@RequestParam("curPage") int curPage,
			@RequestParam("pageSize") int pageSize, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		String roleId = request.getParameter("roleId");
		try {
			Map<String, Object> param = getFormParam(request);
			param.put("curPage", curPage);
			param.put("pageSize", pageSize);
			param.put("roleId", roleId);
			param.put("roleLevel", getLoginUser().getRoleLevel());
			List<Map<String, Object>> data = sysRoleService
					.queryAutzeUserList(param);
			Page<Map<String, Object>> page = (Page<Map<String, Object>>) data;
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

	@ResponseBody
	@RequestMapping(value = "/authorize_user", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> authorizeUser(
			@RequestParam("roleId") String roleId,
			@RequestParam("userId") String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] userIds = userId.split(",");
			if (userIds.length > 0) {
				sysRoleService.authorizeUser(roleId, userIds);
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
	@RequestMapping(value = "/cancel_authorize", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> cancelAuthorize(
			@RequestParam("roleId") String roleId,
			@RequestParam("userId") String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] userIds = userId.split(",");
			if (userIds.length > 0) {
				sysRoleService.cancelAuthorize(roleId, userIds);
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

	/**
	 * 权限菜单
	 * 
	 * @param roleId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/authority_menu", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView authorityMenu(@RequestParam("roleId") String roleId,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", roleId);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return new ModelAndView("main/admin/users/authority_menu", result);
	}

	@ResponseBody
	@RequestMapping(value = "/authority_save", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> authoritySave(
			@RequestParam("roleId") String roleId,
			@RequestParam("selectNode") String selectNode,
			@RequestParam("removeNode") String removeNode,
			@RequestParam("rmNodeType") String rmNodeType) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			sysRoleService.authorityUpdate(roleId, selectNode, removeNode,rmNodeType);
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
}

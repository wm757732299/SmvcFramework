package com.wm.controller.sys;

import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.SysMenu;
import com.wm.service.SysMenuService;

@Controller
@RequestMapping(value = "/menu")
public class SysMenuController extends BaseController<SysMenu> {

	private static final Logger LOGGER = Logger
			.getLogger(SysMenuController.class);

	/**
	 * 一级菜单menu_parent字段值
	 */
	private static final String ONE_LEVEL = "ONE-LEVEL";
	private static final String SYS_ADMIN = "Admin";
	@Resource(type = SysMenuService.class)
	private SysMenuService sysMenuService;

	/**
	 * 初始化菜单
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/init_menu", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> initMenu(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String userId = getLoginUserId();
			String uAccount = getLoginUser().getuAccount();
			SysMenu sysm = new SysMenu();
			sysm.setId(ONE_LEVEL);
			if (!SYS_ADMIN.equals(uAccount)) {
				sysm.setUserId(userId);
			}
			List<SysMenu> menu = sysMenuService.queryMenuTree(sysm);
			for (SysMenu sysMenu : menu) {
				if (!SYS_ADMIN.equals(uAccount)) {
					sysMenu.setUserId(userId);
				}
				bulidMenuTree(sysMenu);
			}
			List<Object> menus = modiMenuTree(menu, 0);
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", menus);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	/**
	 * 初始化菜单（导航树升级版）
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/menu_tree", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> menuTree(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String userId = getLoginUserId();
			String uAccount = getLoginUser().getuAccount();
			SysMenu sysm = new SysMenu();
			sysm.setId(ONE_LEVEL);
			if (!SYS_ADMIN.equals(uAccount)) {
				sysm.setUserId(userId);
			}
			String roleId = request.getParameter("roleId");
			if (roleId != null) {
				sysm.setRoleId(roleId);
			}
			List<SysMenu> menu = sysMenuService.queryMenuTree(sysm);
			for (SysMenu sysMenu : menu) {
				if (roleId != null) {
					sysMenu.setRoleId(roleId);
				}
				if (!SYS_ADMIN.equals(uAccount)) {
					sysMenu.setUserId(userId);
				}
				bulidMenuTree(sysMenu);
			}
			List<Object> menus = modiMenuTree(menu, 1);

			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", menus);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	/**
	 * 构建导航树
	 * 
	 * @param sysMenu
	 */
	private void bulidMenuTree(SysMenu sysMenu) {
		if (SysMenu.PARENT_NODE == sysMenu.getNodeType()) {
			List<SysMenu> childrenMenu = sysMenuService.queryMenuTree(sysMenu);
			sysMenu.setChildren(childrenMenu);
			for (SysMenu sysMenu2 : childrenMenu) {
				bulidMenuTree(sysMenu2);
			}
		}
	}

	/**
	 * 修饰导航树
	 */
	private List<Object> modiMenuTree(List<SysMenu> menu, int type) {
		List<Object> ret = null;
		if (menu != null && menu.size() > 0) {
			ret = new ArrayList<Object>();
			/* tree */
			if (type == 1) {
				for (SysMenu sysMenu : menu) {
					Map<String, Object> item01 = new HashMap<String, Object>();
					item01.put("id", sysMenu.getId());
					item01.put("name", sysMenu.getMenuName());
					if (SysMenu.PARENT_NODE == sysMenu.getNodeType()) {
						item01.put("open", true);
						item01.put("children",
								modiMenuTree(sysMenu.getChildren(), type));
					}
					if (sysMenu.getAuttyMark() == 1) {
						item01.put("checked", true);
					}
					// -------
					item01.put("data", sysMenu);

					ret.add(item01);
				}
			} else {
				for (SysMenu sysMenu : menu) {
					Map<String, Object> item01 = new HashMap<String, Object>();
					item01.put("id", sysMenu.getId());
					item01.put("text", sysMenu.getMenuName());
					item01.put("url", sysMenu.getMenuUrl());
					item01.put("icon", sysMenu.getMenuIcon());
					item01.put("nodeType", sysMenu.getNodeType());
					if (SysMenu.PARENT_NODE == sysMenu.getNodeType()) {
						item01.put("active", true);
						item01.put("children",
								modiMenuTree(sysMenu.getChildren(), type));
					}
					ret.add(item01);
				}
			}
		}
		return ret;
	}

	/**
	 * 菜单管理页
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/menu_home", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView menuHome(HttpServletResponse response,
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
		return new ModelAndView("main/admin/menu/menu_home", result);
	}

	/**
	 * 新增保存
	 * 
	 * @param sysMenu
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_menu", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> saveMenu(SysMenu sysMenu) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SysMenu m = new SysMenu();
			m.setMenuName(sysMenu.getMenuName());
			long count = sysMenuService.queryCount(m);
			if (count > 0) {
				result.put("success", "false");
				result.put("msg", "保存失败，菜单名不能重复！");
				return result;
			} else {
				sysMenu.setTimeStamp(new Timestamp(new Date().getTime()));
				sysMenu.setIsAvailable(0);
				sysMenuService.insert(sysMenu);
				result.put("success", "true");
				result.put("msg", "请求成功");
				result.put("data", sysMenu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
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
	@RequestMapping(value = "/edit_menu", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> editMenu(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		SysMenu m = null;
		try {
			m = sysMenuService.queryByKey(id);
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
	public Map<String, Object> editSave(SysMenu sysMenu) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SysMenu param = new SysMenu();
			param.setMenuName(sysMenu.getMenuName());
			List<SysMenu> m = sysMenuService.queryByCondition(param);
			if (m != null && m.size() > 0) {
				SysMenu menu = null;
				for (SysMenu sm : m) {
					if (sm.getId().equals(sysMenu.getId())) {
						menu = sm;
					} else {
						result.put("success", "false");
						result.put("msg", "保存失败，菜单名不能重复！");
						return result;
					}
				}
				if (menu != null) {
					menu.setMenuName(sysMenu.getMenuName());
					menu.setMenuIcon(sysMenu.getMenuIcon());
					menu.setMenuUrl(sysMenu.getMenuUrl());
					menu.setMenuSort(sysMenu.getMenuSort());
					// menu.setNodeType(sysMenu.getNodeType());
					menu.setTimeStamp(new Timestamp(new Date().getTime()));
					sysMenuService.update(menu);
					result.put("success", "true");
					result.put("msg", "请求成功");
					result.put("data", menu);
				}
			} else {
				SysMenu menu = new SysMenu();
				menu.setId(sysMenu.getId());
				menu.setMenuName(sysMenu.getMenuName());
				menu.setMenuIcon(sysMenu.getMenuIcon());
				menu.setMenuUrl(sysMenu.getMenuUrl());
				menu.setMenuSort(sysMenu.getMenuSort());
				// menu.setNodeType(sysMenu.getNodeType());
				menu.setTimeStamp(new Timestamp(new Date().getTime()));
				sysMenuService.update(menu);
				result.put("success", "true");
				result.put("msg", "请求成功");
				result.put("data", sysMenu);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param sysMenu
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del_menu", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> delMenu(@RequestParam("menuId") String menuId,
			@RequestParam("nodeType") int nodeType) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			SysMenu sysMenu = sysMenuService.queryByKey(menuId);
			bulidMenuTree(sysMenu);
			sysMenuService.delete(sysMenu);
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

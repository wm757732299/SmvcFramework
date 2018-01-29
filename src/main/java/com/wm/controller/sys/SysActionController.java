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

import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.SysAction;
import com.wm.service.SysActionService;

@Controller
@RequestMapping(value = "/sysAction")
public class SysActionController extends BaseController<SysAction> {

	private static final Logger LOGGER = Logger.getLogger(SysActionController.class);

	@Resource(type = SysActionService.class)
	private SysActionService sysActionService;
	
	
	
	/**
	 * 用户管理列表
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/action_list", method = { RequestMethod.POST,
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
		return new ModelAndView("main/admin/sys/action/action_list", result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/action_list_data", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> actionListData(HttpServletRequest request,SysAction sysAction) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
//			Map<String, Object> param = getFormParam(request);
//			param.put("roleLevel", getLoginUser().getRoleLevel());
			List<SysAction> data = sysActionService.queryByCondition(sysAction);
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", data);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;

	}
	
	
	@ResponseBody
	@RequestMapping(value = "/del_action", method = { RequestMethod.POST,
			RequestMethod.GET })
		public Map<String, Object> delAction(@RequestParam("actionId") String actionId) {
			Map<String, Object> result = new HashMap<String, Object>();
			try {
				String[] ids = actionId.split(",");
				if (ids.length > 0) {
					sysActionService.batDelete(ids);
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
	
	@ResponseBody
	@RequestMapping(value = "/save_act", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> saveAct(SysAction sysAction) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			Timestamp tp = new Timestamp(new Date().getTime());
			sysAction.setCreateTime(tp);
			sysAction.setTimeStamp(tp);
			sysActionService.insert(sysAction);
			
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", sysAction);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}
}
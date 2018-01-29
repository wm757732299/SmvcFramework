package com.wm.controller.admin;

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

import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.WebNews;
import com.wm.service.WebNewsService;

@Controller
@RequestMapping(value = "/webNews")
public class WebNewsController extends BaseController<WebNews> {

	private static final Logger LOGGER = Logger
			.getLogger(WebNewsController.class);

	@Resource(type = WebNewsService.class)
	private WebNewsService webNewsService;

	@ResponseBody
	@RequestMapping(value = "/web_news_list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView webNewsList(HttpServletResponse response,
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
		return new ModelAndView("main/admin/webnews/web_news_list", result);
	}

	@ResponseBody
	@RequestMapping(value = "/webnews_list_data", method = {
			RequestMethod.POST, RequestMethod.GET })
	public Map<String, Object> webNewsDataList(
			@RequestParam("curPage") int curPage,
			@RequestParam("pageSize") int pageSize, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Map<String, Object> param = getFormParam(request);
			param.put("curPage", curPage);
			param.put("pageSize", pageSize);
			param.put("roleLevel", getLoginUser().getRoleLevel());
			List<WebNews> data = webNewsService.queryWebNewsList(param);
			Map<String, Object> pageInfo = getPageInfo(data);
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

}
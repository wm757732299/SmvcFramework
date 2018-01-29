package com.wm.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.Video;
import com.wm.service.VideoService;

@Controller
@RequestMapping(value = "/video/web")
public class VideoController extends BaseController<Video> {

	private static final Logger LOGGER = Logger.getLogger(VideoController.class);

	@Resource(type = VideoService.class)
	private VideoService videoService;
	
	
	@ResponseBody
	@RequestMapping(value = "/video_play", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView videoPlay(HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Video video = videoService.queryByKey("voidID1111111");
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", video);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return new ModelAndView("main/website/video/video_play", result);
	}
	
}
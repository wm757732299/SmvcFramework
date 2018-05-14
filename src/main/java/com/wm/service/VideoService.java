package com.wm.service;

import java.util.List;
import java.util.Map;

import com.wm.mapper.entity.Video;
import com.wm.service.base.IBaseService;

public interface VideoService extends IBaseService<Video>{

	public List<Video> queryVideoList(Map<String, Object> param);

	
}
package com.wm.service;

import java.util.List;
import java.util.Map;

import com.wm.mapper.entity.WebNews;
import com.wm.service.base.IBaseService;

public interface WebNewsService extends IBaseService<WebNews>{

	public List<WebNews> queryWebNewsList(Map<String, Object> param);

	
}
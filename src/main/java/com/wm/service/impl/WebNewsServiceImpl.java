package com.wm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.WebNewsMapper;
import com.wm.mapper.entity.WebNews;
import com.wm.service.WebNewsService;

@Service("webNewsService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class WebNewsServiceImpl implements WebNewsService {

	@Resource(type = WebNewsMapper.class)
	private WebNewsMapper webNewsMapper;

	public long insert(WebNews t) {
		return webNewsMapper.insert(t);
	}

	public long delete(WebNews t) {
		return webNewsMapper.deleteTrueByKey(t.getId());
	}

	public long update(WebNews t) {
		return webNewsMapper.update(t);
	}

	public WebNews queryByKey(String id) {
		return webNewsMapper.queryByKey(id);
	}

	public List<WebNews> queryByCondition(WebNews t) {
		return null;
	}

	public List<WebNews> queryByPage(WebNews t) {
		return null;
	}

	public List<WebNews> queryWebNewsList(Map<String, Object> param) {
		PageHelper.startPage((Integer)param.get("curPage"), (Integer)param.get("pageSize"));
		return  webNewsMapper.queryByPage(param);
	}


}
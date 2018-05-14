package com.wm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.VideoMapper;
import com.wm.mapper.entity.Video;
import com.wm.service.VideoService;

@Service("videoService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class VideoServiceImpl implements VideoService {

	@Resource(type = VideoMapper.class)
	private VideoMapper videoMapper;

	public long insert(Video t) {
		return videoMapper.insert(t);
	}

	public long delete(Video t) {
		return videoMapper.deleteTrueByKey(t.getId());
	}

	public long update(Video t) {
		return videoMapper.update(t);
	}

	public Video queryByKey(String id) {
		return videoMapper.queryByKey(id);
	}

	public List<Video> queryByCondition(Video t) {
		return null;
	}

	public List<Video> queryByPage(Video t) {
		return null;
	}

	@Override
	public List<Video> queryVideoList(Map<String, Object> param) {
//			System.out.println(SysUserServiceImpl.class.getAnnotation(DataSourceChoose.class).sourceKey()); 获得注解值
			PageHelper.startPage((Integer)param.get("curPage"), (Integer)param.get("pageSize"));
			return  videoMapper.queryByPage(param);
	}


}
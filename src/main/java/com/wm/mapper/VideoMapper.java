package com.wm.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.Video;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("videoMapper")
public interface VideoMapper extends IBaseMapper<Video> {

}
package com.wm.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.WebNews;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("webNewsMapper")
public interface WebNewsMapper extends IBaseMapper<WebNews> {

}

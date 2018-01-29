package com.wm.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.springframework.stereotype.Repository;

import com.wm.mapper.annotation.MybatisMapper;
import com.wm.mapper.base.IBaseMapper;
import com.wm.mapper.entity.VideoCommentVote;

@CacheNamespace(size = 1024)
@MybatisMapper
@Repository("videoCommentVoteMapper")
public interface VideoCommentVoteMapper extends IBaseMapper<VideoCommentVote> {

	/**
	 * 取消点赞、点踩
	 * @param t
	 */
	public void cancelVote(VideoCommentVote t);
	
	/**
	 * 根据指定条件修改
	 * @param t
	 * @return
	 */
	public long updateByGiven(VideoCommentVote t);

}
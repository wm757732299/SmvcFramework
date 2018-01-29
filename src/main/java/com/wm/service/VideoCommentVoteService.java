package com.wm.service;

import com.wm.mapper.entity.VideoCommentVote;
import com.wm.service.base.IBaseService;

public interface VideoCommentVoteService extends IBaseService<VideoCommentVote>{

	public long updateOrInsert(VideoCommentVote t);
	
	/**
	 * 取消点赞、点踩
	 * @param t
	 */
	public void cancelVote(VideoCommentVote t);

	
}
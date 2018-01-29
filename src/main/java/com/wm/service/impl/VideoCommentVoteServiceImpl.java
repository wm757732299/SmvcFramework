package com.wm.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wm.annotation.DataSourceChoose;
import com.wm.annotation.DataSourceChoose.SourceKey;
import com.wm.mapper.VideoCommentVoteMapper;
import com.wm.mapper.entity.VideoCommentVote;
import com.wm.service.VideoCommentVoteService;

@Service("videoCommentVoteService")
@DataSourceChoose(sourceKey = SourceKey.DSK2)
@Transactional(timeout = 60)
public class VideoCommentVoteServiceImpl implements VideoCommentVoteService {

	@Resource(type = VideoCommentVoteMapper.class)
	private VideoCommentVoteMapper videoCommentVoteMapper;

	public long insert(VideoCommentVote t) {
		return videoCommentVoteMapper.insert(t);
	}

	public long delete(VideoCommentVote t) {
		return videoCommentVoteMapper.deleteTrueByKey(t.getId());
	}

	public long update(VideoCommentVote t) {
		return videoCommentVoteMapper.update(t);
	}

	public VideoCommentVote queryByKey(String id) {
		return videoCommentVoteMapper.queryByKey(id);
	}

	public List<VideoCommentVote> queryByCondition(VideoCommentVote t) {
		return videoCommentVoteMapper.queryByCondition(t);
	}

	public List<VideoCommentVote> queryByPage(VideoCommentVote t) {
		return null;
	}

	// 更新或插入方法待优化。。（此方法高并发下会失效）
	public long updateOrInsert(VideoCommentVote t) {
		long l = -1;
		Integer vote = t.getVote();
		t.setVote(null);
		long count = videoCommentVoteMapper.queryCount(t);
		Date date = new Date();
		if (count > 0) {
			t.setVote(vote);
			t.setTimeStamp(date);
			l = videoCommentVoteMapper.updateByGiven(t);
		} else {
			t.setVote(vote);
			t.setCreateTime(date);
			t.setTimeStamp(date);
			l = videoCommentVoteMapper.insert(t);
		}
		return l;
	}

	public void cancelVote(VideoCommentVote t) {
		t.setTimeStamp(new Date());
		videoCommentVoteMapper.cancelVote(t);

	}
}
package com.wm.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wm.controller.base.BaseController;
import com.wm.mapper.entity.VideoComment;
import com.wm.mapper.entity.VideoCommentVote;
import com.wm.model.LoginUserDetails;
import com.wm.service.VideoCommentService;
import com.wm.service.VideoCommentVoteService;
import com.wm.util.BeansUtil;
import com.wm.util.CommentVoteQueue;

@Controller
@RequestMapping(value = "/videoComment")
public class VideoCommentController extends BaseController<VideoComment> {

	private static final Logger LOGGER = Logger
			.getLogger(VideoCommentController.class);

	@Resource(type = VideoCommentService.class)
	private VideoCommentService videoCommentService;

	@Resource(type = VideoCommentVoteService.class)
	private VideoCommentVoteService videoCommentVoteService;

	@ResponseBody
	@RequestMapping(value = "/save_comt", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> saveComt(VideoComment vc) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 评论时切换帐号问题
		try {
			
			LoginUserDetails loginer = getLoginUser();
			
			if ("PL".equals(vc.getVcType())) {// HF:回复,PL评论

				//冗余评论人信息
				vc.setComtUname(loginer.getuName());
				vc.setComtUpic(loginer.getuPhoto());
				
				vc.setFromUid(loginer.getId());
				// vc.setReplyGroup(vc.getTopicId()+getLoginUserId());
				vc.setLikeCount(0);
				vc.setDislikeCount(0);
				vc.setCreateTime(new Date());
				vc.setTimeStamp(new Date());
				videoCommentService.insert(vc);
			}
			if ("HF".equals(vc.getVcType())) {
				
				//冗余回复人信息
				vc.setComtUname(loginer.getuName());
				vc.setComtUpic(loginer.getuPhoto());
				
				vc.setReplyUid(loginer.getId());
				vc.setLikeCount(0);
				vc.setDislikeCount(0);
				vc.setCreateTime(new Date());
				vc.setTimeStamp(new Date());
				videoCommentService.insert(vc);
			}

			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", vc);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/web/get_comt", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> getComt(HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
		VideoComment vc = new VideoComment();
		vc.setTopicId("voidID1111111");
		vc.setVcType("PL");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("topicId", "voidID1111111");
		param.put("vcType", "PL");
		param.put("curPage", 1);
		param.put("pageSize", 10);
		param.put("deleteMark", 0);
		List<VideoComment> vcs = videoCommentService.queryByPage(param);

		param.put("vcType", "HF");
		param.put("curPage", 1);
		param.put("pageSize", 10);
		for (VideoComment videoComment : vcs) {
			param.put("replyGroup", videoComment.getId());
			videoComment.setReplyList(videoCommentService.queryByPage(param));
		}
		
			redisDb(vcs);
			List<VideoComment> vcs2 = getDatas();
			if(vcs2!=null){
				vcs=vcs2;
			}
			
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", vcs);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}

	/**
	 * 点赞点踩或取消
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/vote", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> vote(HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			String cId = request.getParameter("cId");
			String vote = request.getParameter("vote");
			VideoCommentVote vcv = new VideoCommentVote();
			vcv.setTopicId(cId);
			vcv.setFromUid(getLoginUserId());
			if ("thumbs_up".equals(vote)) {
				vcv.setVote(1);
				videoCommentVoteService.updateOrInsert(vcv);
			} else if ("thumbs_down".equals(vote)) {
				vcv.setVote(-1);
				videoCommentVoteService.updateOrInsert(vcv);
			} else if ("thumbs_up_cancel".equals(vote)
					|| "thumbs_down_cancel".equals(vote)) {
				vcv.setVote(0);
				videoCommentVoteService.cancelVote(vcv);
			}
			
			CommentVoteQueue.instance().getQueue().offer(cId);
			
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/video_comt_list", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView videoComtList(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return new ModelAndView("main/admin/comment/video_comt_list", result);
	}
	
	@ResponseBody
	@RequestMapping(value = "/vc_list_data", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> videoComtListData(
			@RequestParam("curPage") int curPage,
			@RequestParam("pageSize") int pageSize, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {
			Map<String, Object> param = getFormParam(request);
			param.put("curPage", curPage);
			param.put("pageSize", pageSize);
			param.put("vcType", "PL");
			List<VideoComment> data = videoCommentService.queryByPage(param);
			Map<String, Object> pageInfo = getPageInfo(data);
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", data);
			result.put("param", param);
			result.put("pageInfo", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;

	}
	
	@ResponseBody
	@RequestMapping(value = "/del_comt", method = { RequestMethod.POST,
			RequestMethod.GET })
	public Map<String, Object> delComt(@RequestParam("cId") String cId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String[] ids = cId.split(",");
			if(ids.length>0){
				videoCommentService.batDelete(ids);
				result.put("success", "true");
				result.put("msg", "删除成功");
				result.put("data", "");
			}else{
				result.put("success", "false");
				result.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "删除失败");
		}
		return result;
	}
	
	
	 @Autowired
	  private StringRedisTemplate redisTemplate;
	public void redisDb(List<VideoComment> vcs){
	
		HashOperations<String,Object,Object> opshash = redisTemplate.opsForHash();
		BeansUtil bu = new BeansUtil();
		for (VideoComment vc : vcs) {
			Map<String,String> map = bu.objToMap(vc, vc.getClass());
			opshash.putAll("PL"+vc.getId(), map);
		}
		
		 
		
		

		
		
		//redisTemplate.opsForValue().set("eeeeeeee", "ddddddddd");
//		ListOperations<String, String> opsList = redisTemplate.opsForList();
//		List<List<String>> s = toString(vcs);
//		Set<String> keys = redisTemplate.keys("video_comment"+"*");
//		if(keys.isEmpty()){
////			redisTemplate.delete(keys);
//			for (int i = 0; i < s.size(); i++) {
//				opsList.leftPushAll("video_comment"+s.get(i).get(0), s.get(i));
//			}
//		}
	}
	
	public List<VideoComment> getDatas(){
		Set<String> keys = redisTemplate.keys("PL"+"*");
		Iterator<String> it= keys.iterator();
		List<VideoComment> s = new ArrayList<VideoComment>();
		while(it.hasNext()){
			String key = it.next();
			VideoComment vc = (VideoComment)getData(key, VideoComment.class);
			 s.add(vc);
		}
		return s;
	}
	
	
	public Object getData(String key,Class<?> clazz){
		HashOperations<String,Object,Object> opshash = redisTemplate.opsForHash();
		Map<Object, Object> mm = opshash.entries(key);
		BeansUtil bu = new BeansUtil();
		return bu.mapToObj(mm, clazz);
	}
	public List<List<String>> toString(List<VideoComment> v){
		List<List<String>> s = new ArrayList<List<String>>();
		StringBuffer sb= new StringBuffer();
		for (VideoComment vc : v) {
			List<String> l = new ArrayList<String>();
			l.add(vc.getId());
			l.add(vc.getTopicId()==null?"":vc.getTopicId());
			l.add(vc.getFromUid()==null?"":vc.getFromUid());
			l.add(vc.getReplyUid()==null?"":vc.getReplyUid());
			l.add(vc.getContent()==null?"":vc.getContent());
			l.add(vc.getReplyGroup()==null?"":vc.getReplyGroup());
			l.add(vc.getReplyedId()==null?"":vc.getReplyedId());
			l.add(vc.getVcType()==null?"":vc.getVcType());
			l.add(vc.getLikeCount()+"");
			l.add(vc.getDislikeCount()+"");
			l.add(vc.getComtUname()==null?"":vc.getComtUname());
			l.add(vc.getComtUpic()==null?"":vc.getComtUpic());
			l.add(vc.getRepliedUname()==null?"":vc.getRepliedUname());
			l.add(vc.getRepliedUpic()==null?"":vc.getRepliedUpic());
			l.add(vc.getCreateTime().toString());
			l.add(vc.getTimeStamp().toString());
			l.add(vc.getDeleteMark()+"");
			s.add(l);
		}
		
		return s;
		
	}
	
 
	public List<VideoComment> toBean(){
		Set<String> keys = redisTemplate.keys("video_comment"+"*");
		Iterator<String> it= keys.iterator();
		List<List<String>> s = new ArrayList<List<String>>();
		while(it.hasNext()){
			String key = it.next();
			s.add(redisTemplate.opsForList().range(key, 0, -1));
		}
		System.out.println("3e");
		List<VideoComment> vcl= null;
		if(s.size()>0){
			vcl =new ArrayList<VideoComment>();
			for (List<String> list : s) {
				
				VideoComment  vc = new VideoComment();
				vc.setId(list.get(0));
						vc.setTopicId(list.get(15));
						vc.setFromUid(list.get(14));
						vc.setReplyUid(list.get(13));
						vc.setContent(list.get(12));
						vc.setReplyGroup(list.get(11));
						vc.setReplyedId(list.get(10));
						vc.setVcType(list.get(9));
						vc.setLikeCount(Integer.parseInt(list.get(8)));
						vc.setDislikeCount(Integer.parseInt(list.get(7)));
						vc.setComtUname(list.get(6));
						vc.setComtUpic(list.get(5));
						vc.setRepliedUname(list.get(4));
						vc.setRepliedUpic(list.get(3));
						vc.setCreateTime(new Date(list.get(2)));
						vc.setTimeStamp(new Date(list.get(1)));
						vc.setDeleteMark(Integer.parseInt(list.get(0)));
				 
						vcl.add(vc);
			}
		}
		
		return vcl;
	}
}

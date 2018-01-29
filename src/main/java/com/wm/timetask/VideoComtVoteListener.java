package com.wm.timetask;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wm.service.VideoCommentService;
import com.wm.service.VideoCommentVoteService;
import com.wm.util.CommentVoteQueue;
//@WebListener
@Component 
public class VideoComtVoteListener { //implements  ServletContextListener

	
	
	 @Autowired
	 private ApplicationContext applicationContext;
	 
	    @Resource(type = VideoCommentService.class)
		private VideoCommentService videoCommentService;
	    
	    @Resource(type = VideoCommentVoteService.class)
		private VideoCommentVoteService videoCommentVoteService;
	    
	 /**  
	     * 定时计算。每天凌晨 01:00 执行一次  
	     */   
//	  @Scheduled(cron = "0 0 1 * * *")   
//	    public void show(){  
//	        System.out.println("Annotation：is show run");  
//	    }  
	      
	    /**  
	     * 心跳更新。启动时执行一次，之后每隔20秒执行一次  
	     */    
	    @Scheduled(fixedRate = 1000*20)   
	    public void print(){  

			Queue<String> q = CommentVoteQueue.instance()
					.getQueue();
			String m = null;
			while (!q.isEmpty()) {
				try {
					m = q.poll();
					videoCommentService.syncVotes(m);
				//	insert(m);
					m = null;
				} catch (Exception e) {
					e.printStackTrace();
					if (m != null) {
						q.offer(m);
						m = null;
					}
				}
			}
		
	    }  
	 
	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				
				
				System.out.println(applicationContext);
				Queue<String> q = CommentVoteQueue.instance()
						.getQueue();
				String m = null;
				while (!q.isEmpty()) {
					try {
						m = q.poll();
					//	insert(m);
						m = null;
					} catch (Exception e) {
						e.printStackTrace();
						if (m != null) {
							q.offer(m);
							m = null;
						}
					}
				}
			}
		};
		new Timer().schedule(task, 1000, 1000 * 20);
		
	}

	//定时将评论点赞数量同步到评论表字段
//	SELECT COUNT(id),t.vote  from  video_comment_vote t   
//	where   t.topic_id='c45a4b0e-ed55-11e7-b917-00163e0c5677'
//	GROUP BY t.vote
	
}

package com.wm.util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CommentVoteQueue {
	private static class Holder {
		private static final CommentVoteQueue cvq = new CommentVoteQueue();
	}

	private CommentVoteQueue() {
	}

	private Queue<String> queue = new ConcurrentLinkedQueue<String>();

	public static final CommentVoteQueue instance() {
		return Holder.cvq;
	}

	public Queue<String> getQueue() {
		return queue;
	}

}

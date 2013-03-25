package com.anthavio.disquo.api.threads;

import java.util.LinkedList;
import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;
import com.anthavio.disquo.api.QThread;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;

/**
 * http://disqus.com/api/docs/forums/
 * 
 * @author martin.vanek
 *
 */
public class DisqusThreadsGroup extends DisqusFeatureGroup {

	public DisqusThreadsGroup(Disqus disqus) {
		super(disqus);
	}

	public ThreadCreateMethod create(String forum, String title) {
		return new ThreadCreateMethod(this.disqus).setForum(forum).setTitle(title);
	}

	public ThreadCreateMethod create(String forum, String title, String identifier) {
		return new ThreadCreateMethod(this.disqus).setForum(forum).setTitle(title).setIdentifier(identifier);
	}

	public ThreadUpdateMethod update(long thread) {
		ThreadUpdateMethod method = new ThreadUpdateMethod(this.disqus);
		method.setThread(QThread.build(thread));
		return method;
	}

	public ThreadDetailsMethod details(long thread) {
		ThreadDetailsMethod method = new ThreadDetailsMethod(this.disqus);
		method.setThread(QThread.build(thread));
		return method;
	}

	/**
	 * When using thread string ident, forum shortname must be also provided
	 */
	public ThreadDetailsMethod details(String threadIdent, String forum) {
		ThreadDetailsMethod method = new ThreadDetailsMethod(this.disqus);
		method.setThread(QThread.ByIdent(threadIdent)).setForum(forum);
		return method;
	}

	public ThreadCloseMethod close(long... thread) {
		ThreadCloseMethod method = new ThreadCloseMethod(this.disqus);
		method.addThread(thread);
		return method;
	}

	public ThreadCloseMethod close(String threadIdent, String forum) {
		ThreadCloseMethod method = new ThreadCloseMethod(this.disqus);
		method.addThread(QThread.ByIdent(threadIdent)).setForum(forum);
		return method;
	}

	public ThreadOpenMethod open(long... thread) {
		ThreadOpenMethod method = new ThreadOpenMethod(this.disqus);
		method.addThread(thread);
		return method;
	}

	public ThreadOpenMethod open(String threadIdent, String forum) {
		ThreadOpenMethod method = new ThreadOpenMethod(this.disqus);
		method.addThread(QThread.ByIdent(threadIdent)).setForum(forum);
		return method;
	}

	public ThreadRemoveMethod remove(long... thread) {
		ThreadRemoveMethod method = new ThreadRemoveMethod(this.disqus);
		method.addThread(thread);
		return method;
	}

	public ThreadRemoveMethod remove(String threadIdent, String forum) {
		ThreadRemoveMethod method = new ThreadRemoveMethod(this.disqus);
		method.addThread(QThread.ByIdent(threadIdent)).setForum(forum);
		return method;
	}

	public ThreadRestoreMethod restore(long... thread) {
		ThreadRestoreMethod method = new ThreadRestoreMethod(this.disqus);
		method.addThread(thread);
		return method;
	}

	public ThreadRestoreMethod restore(String threadIdent, String forum) {
		ThreadRestoreMethod method = new ThreadRestoreMethod(this.disqus);
		method.addThread(QThread.ByIdent(threadIdent)).setForum(forum);
		return method;
	}

	/**
	 * Lists Threads from @param forum
	 */
	public ThreadListMethod list(String forum) {
		return new ThreadListMethod(this.disqus).addForum(forum);
	}

	/**
	 * List ALL Threads from ALL users from ALL forums from ALL THE WORLD
	 */
	public ThreadListMethod list() {
		return new ThreadListMethod(this.disqus);
	}

	public ThreadSetMethod set(long... thread) {
		ThreadSetMethod method = new ThreadSetMethod(this.disqus);
		method.addThread(thread);
		return method;
	}

	/**
	 * Lists Posts from @param thread
	 */
	public ThreadListPostsMethod listPosts(long thread) {
		return listPosts(QThread.build(thread));
	}

	/**
	 * When using thread string ident, forum shortname must be also provided
	 */
	public ThreadListPostsMethod listPosts(String threadIdent, String forum) {
		return listPosts(QThread.ByIdent(threadIdent)).setForum(forum);
	}

	private ThreadListPostsMethod listPosts(QThread thread) {
		return new ThreadListPostsMethod(this.disqus).setThread(thread);
	}

	public ThreadListHotMethod listHot(String forum) {
		return new ThreadListHotMethod(this.disqus).addForum(forum);
	}

	/**
	 * Default interval is 7 days
	 */
	public ThreadListPopularMethod listPopular(String forum) {
		return new ThreadListPopularMethod(this.disqus).setForum(forum);
	}

	public ThreadListReactionsMethod listReactions(long thread) {
		return new ThreadListReactionsMethod(this.disqus).setThread(thread);
	}

	/**
	 * Lists All Post from Thread - iterates results with cursor
	 */
	public List<DisqusPost> listAllPosts(long thread) {
		List<DisqusPost> retval = new LinkedList<DisqusPost>();
		RecurAllPost(thread, null, retval);
		return retval;
	}

	private void RecurAllPost(long thread, String cursor, List<DisqusPost> posts) {
		ThreadListPostsMethod listPosts = this.disqus.threads().listPosts(thread);
		if (cursor != null) {
			listPosts.setCursor(cursor);
		}
		listPosts.setOrder(Order.asc); //oldest first
		listPosts.setLimit(100); //max limit to minimize number of calls
		DisqusResponse<List<DisqusPost>> response = listPosts.execute();
		for (DisqusPost post : response.getResponse()) {
			posts.add(post);
		}
		if (response.getCursor().getHasNext()) {
			RecurAllPost(thread, response.getCursor().getNext(), posts);
		}
	}

	/*
	 * Disqis API is a mess and this does not work despite documentation
	 * 
		public ThreadSubscribeMethod subscribe(String threadIdent, String forum, String email) {
			ThreadSubscribeMethod method = new ThreadSubscribeMethod(this.disqus);
			method.setThread(QThread.ByIdent(threadIdent));
			method.setForum(forum);
			method.setEmail(email);
			return method;
		}
	*/

	/**
	 * User identity not known here. Email is explicit.
	 */
	public ThreadSubscribeMethod subscribe(long threadId, String email) {
		ThreadSubscribeMethod method = new ThreadSubscribeMethod(this.disqus);
		method.setThread(threadId);
		method.setEmail(email);
		return method;
	}

	/**
	 * Requires user identity provided (sso, token)
	 * Email address is taken from that identity
	 */
	public ThreadSubscribeMethod subscribe(long threadId) {
		ThreadSubscribeMethod method = new ThreadSubscribeMethod(this.disqus);
		method.setThread(threadId);
		return method;
	}

	/*
	 * Disqis API is a mess and this does not work despite documentation
	 * 
		public ThreadUnsubscribeMethod unsubscribe(String threadIdent, String forum, String email) {
			ThreadUnsubscribeMethod method = new ThreadUnsubscribeMethod(this.disqus);
			method.setThread(QThread.ByIdent(threadIdent));
			method.setForum(forum);
			method.setEmail(email);
			return method;
		}
	*/

	/**
	 * User identity not known here. Email is explicit.
	 */
	public ThreadUnsubscribeMethod unsubscribe(long threadId, String email) {
		ThreadUnsubscribeMethod method = new ThreadUnsubscribeMethod(this.disqus);
		method.setThread(threadId);
		method.setEmail(email);
		return method;
	}

	/**
	 * Requires user identity provided (sso, token)
	 * Email address is taken from that identity
	 */
	public ThreadUnsubscribeMethod unsubscribe(long threadId) {
		ThreadUnsubscribeMethod method = new ThreadUnsubscribeMethod(this.disqus);
		method.setThread(threadId);
		return method;
	}

	public ThreadVoteMethod vote(String threadIdent, String forum, Vote vote) {
		ThreadVoteMethod method = new ThreadVoteMethod(this.disqus);
		method.setThread(QThread.ByIdent(threadIdent));
		method.setForum(forum);
		method.setVote(vote);
		return method;
	}

	public ThreadVoteMethod vote(long threadId, Vote vote) {
		ThreadVoteMethod method = new ThreadVoteMethod(this.disqus);
		method.setThread(threadId);
		method.setVote(vote);
		return method;
	}

}

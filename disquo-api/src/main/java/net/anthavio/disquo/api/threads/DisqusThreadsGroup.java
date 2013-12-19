package net.anthavio.disquo.api.threads;

import java.util.LinkedList;
import java.util.List;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusFeatureGroup;
import net.anthavio.disquo.api.QThread;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Vote;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;


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
		return new ThreadUpdateMethod(this.disqus).setThread(thread);
	}

	public ThreadDetailsMethod details(long thread) {
		return new ThreadDetailsMethod(this.disqus).setThread(thread);
	}

	/**
	 * When using thread string ident, forum shortname must be also provided
	 */
	public ThreadDetailsMethod details(String threadIdent, String forum) {
		return new ThreadDetailsMethod(this.disqus).setThread(QThread.ByIdent(threadIdent)).setForum(forum);
	}

	public ThreadCloseMethod close(long... thread) {
		return new ThreadCloseMethod(this.disqus).addThread(thread);
	}

	public ThreadCloseMethod close(String threadIdent, String forum) {
		return new ThreadCloseMethod(this.disqus).addThread(QThread.ByIdent(threadIdent)).setForum(forum);
	}

	public ThreadOpenMethod open(long... thread) {
		return new ThreadOpenMethod(this.disqus).addThread(thread);
	}

	public ThreadOpenMethod open(String threadIdent, String forum) {
		return new ThreadOpenMethod(this.disqus).addThread(QThread.ByIdent(threadIdent)).setForum(forum);
	}

	public ThreadRemoveMethod remove(long... thread) {
		return new ThreadRemoveMethod(this.disqus).addThread(thread);
	}

	public ThreadRemoveMethod remove(String threadIdent, String forum) {
		return new ThreadRemoveMethod(this.disqus).addThread(QThread.ByIdent(threadIdent)).setForum(forum);
	}

	public ThreadRestoreMethod restore(long... thread) {
		return new ThreadRestoreMethod(this.disqus).addThread(thread);
	}

	public ThreadRestoreMethod restore(String threadIdent, String forum) {
		return new ThreadRestoreMethod(this.disqus).addThread(QThread.ByIdent(threadIdent)).setForum(forum);
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
		return new ThreadSetMethod(this.disqus).addThread(thread);
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
		return new ThreadSubscribeMethod(this.disqus).setThread(threadId).setEmail(email);
	}

	/**
	 * Requires user identity provided (sso, token)
	 * Email address is taken from that identity
	 */
	public ThreadSubscribeMethod subscribe(long threadId) {
		return new ThreadSubscribeMethod(this.disqus).setThread(threadId);
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
		return new ThreadUnsubscribeMethod(this.disqus).setThread(threadId).setEmail(email);
	}

	/**
	 * Requires user identity provided (sso, token)
	 * Email address is taken from that identity
	 */
	public ThreadUnsubscribeMethod unsubscribe(long threadId) {
		return new ThreadUnsubscribeMethod(this.disqus).setThread(threadId);
	}

	public ThreadVoteMethod vote(String threadIdent, String forum, Vote vote) {
		return new ThreadVoteMethod(this.disqus).setThread(QThread.ByIdent(threadIdent)).setForum(forum).setVote(vote);
	}

	public ThreadVoteMethod vote(long threadId, Vote vote) {
		return new ThreadVoteMethod(this.disqus).setThread(threadId).setVote(vote);
	}

}

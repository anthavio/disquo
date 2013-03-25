package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.posts.PostCreateMethod.AnonymousData;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusPostsGroup extends DisqusFeatureGroup {

	public DisqusPostsGroup(Disqus disqus) {
		super(disqus);
	}

	public PostCreateMethod create() {
		return new PostCreateMethod(this.disqus);
	}

	public PostCreateMethod create(long thread, String message) {
		return new PostCreateMethod(this.disqus).setMessage(message).setThread(thread);
	}

	/**
	 * Create SSO authenticated post
	 */
	public PostCreateMethod create(long thread, String message, SsoAuthData ssoAuth) {
		PostCreateMethod method = new PostCreateMethod(this.disqus);
		method.setMessage(message).setThread(thread);
		method.setRemoteAuth(ssoAuth);
		return method;
	}

	/**
	 * Create OAUTH authenticated post
	 */
	public PostCreateMethod create(long thread, String message, String userAccessToken) {
		PostCreateMethod method = new PostCreateMethod(this.disqus);
		method.setMessage(message).setThread(thread);
		method.setAccessToken(userAccessToken);
		return method;
	}

	/**
	 * Create anonymous post
	 */
	public PostCreateMethod create(long thread, String message, AnonymousData author) {
		PostCreateMethod method = new PostCreateMethod(this.disqus);
		method.setMessage(message).setThread(thread);
		method.setAuthorAuth(author);
		return method;
	}

	public PostUpdateMethod update(long post, String message) {
		PostUpdateMethod method = new PostUpdateMethod(this.disqus);
		method.setPost(post);
		method.setMessage(message);
		return method;
	}

	public PostGetContextMethod getContext(long post) {
		PostGetContextMethod method = new PostGetContextMethod(this.disqus);
		method.setPost(post);
		return method;
	}

	public PostDetailsMethod details(long post) {
		PostDetailsMethod method = new PostDetailsMethod(this.disqus);
		method.setPost(post);
		return method;
	}

	public PostListMethod list() {
		return new PostListMethod(this.disqus);
	}

	public PostListMethod list(long thread) {
		PostListMethod method = new PostListMethod(this.disqus);
		method.addThread(thread);
		return method;
	}

	public HighlightMethod highlight(long post) {
		HighlightMethod method = new HighlightMethod(this.disqus);
		method.addPost(post);
		return method;
	}

	public PostUnhighlightMethod unhighlight(long post) {
		PostUnhighlightMethod method = new PostUnhighlightMethod(this.disqus);
		method.addPost(post);
		return method;
	}

	public PostApproveMethod approve(long post) {
		PostApproveMethod method = new PostApproveMethod(this.disqus);
		method.addPost(post);
		return method;
	}

	public PostRemoveMethod remove(long post) {
		PostRemoveMethod method = new PostRemoveMethod(this.disqus);
		method.addPost(post);
		return method;
	}

	public PostRestoreMethod restore(long post) {
		PostRestoreMethod method = new PostRestoreMethod(this.disqus);
		method.addPost(post);
		return method;
	}

	public PostSpamMethod spam(long post) {
		PostSpamMethod method = new PostSpamMethod(this.disqus);
		method.addPost(post);
		return method;
	}

	public PostReportMethod report(long post) {
		PostReportMethod method = new PostReportMethod(this.disqus);
		method.setPost(post);
		return method;
	}

	public PostVoteMethod vote(long post, Vote vote) {
		PostVoteMethod method = new PostVoteMethod(this.disqus);
		method.setVote(vote);
		method.setPost(post);
		return method;
	}

}

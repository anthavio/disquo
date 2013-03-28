package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;
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
		return new PostCreateMethod(this.disqus).setMessage(message).setThread(thread).setRemoteAuth(ssoAuth);
	}

	/**
	 * Create OAUTH authenticated post
	 */
	public PostCreateMethod create(long thread, String message, String userAccessToken) {
		return new PostCreateMethod(this.disqus).setMessage(message).setThread(thread).setAccessToken(userAccessToken);
	}

	/**
	 * Create anonymous post
	 */
	public PostCreateMethod create(long thread, String message, AnonymousData author) {
		return new PostCreateMethod(this.disqus).setMessage(message).setThread(thread).setAuthor(author);
	}

	public PostUpdateMethod update(long post, String message) {
		return new PostUpdateMethod(this.disqus).setPost(post).setMessage(message);
	}

	public PostGetContextMethod getContext(long post) {
		return new PostGetContextMethod(this.disqus).setPost(post);
	}

	public PostDetailsMethod details(long post) {
		return new PostDetailsMethod(this.disqus).setPost(post);
	}

	public PostListMethod list() {
		return new PostListMethod(this.disqus);
	}

	public PostListMethod list(long thread) {
		return new PostListMethod(this.disqus).addThread(thread);
	}

	public HighlightMethod highlight(long post) {
		return new HighlightMethod(this.disqus).addPost(post);
	}

	public PostUnhighlightMethod unhighlight(long post) {
		return new PostUnhighlightMethod(this.disqus).addPost(post);
	}

	public PostApproveMethod approve(long post) {
		return new PostApproveMethod(this.disqus).addPost(post);
	}

	public PostRemoveMethod remove(long post) {
		return new PostRemoveMethod(this.disqus).addPost(post);
	}

	public PostRestoreMethod restore(long post) {
		return new PostRestoreMethod(this.disqus).addPost(post);
	}

	public PostSpamMethod spam(long post) {
		return new PostSpamMethod(this.disqus).addPost(post);
	}

	public PostReportMethod report(long post) {
		return new PostReportMethod(this.disqus).setPost(post);
	}

	public PostVoteMethod vote(long post, Vote vote) {
		return new PostVoteMethod(this.disqus).setPost(post).setVote(vote);
	}

}

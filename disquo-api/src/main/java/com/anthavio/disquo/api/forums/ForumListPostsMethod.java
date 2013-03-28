package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListPostsMethod extends DisqusCursorPostsMethod<ForumListPostsMethod, DisqusPost> {

	public ForumListPostsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listPosts);
	}

	public ForumListPostsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ForumListPostsMethod getB() {
		return this;
	}

}

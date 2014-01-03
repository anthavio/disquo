package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorPostsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusPost;

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
	protected ForumListPostsMethod getSelf() {
		return this;
	}

}

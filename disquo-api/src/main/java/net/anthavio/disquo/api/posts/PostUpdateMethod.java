package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostUpdateMethod extends BaseSinglePostMethod<PostUpdateMethod, DisqusPost> {

	public PostUpdateMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.update);
	}

	public PostUpdateMethod setMessage(String message) {
		addParam("message", message);
		return this;
	}

	@Override
	protected PostUpdateMethod getSelf() {
		return this;
	}

}

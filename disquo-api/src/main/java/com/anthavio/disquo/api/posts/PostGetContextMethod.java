package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * Returns the hierarchal tree of a post (all parents).
 * http://disqus.com/api/docs/posts/getContext/
 * 
 * @author martin.vanek
 *
 */
public class PostGetContextMethod extends BaseSingleMultiPostMethod<DisqusPost> {

	public PostGetContextMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.getContext);
	}

	public PostGetContextMethod setDepth(int depth) {
		addParam("depth", depth);
		return this;
	}

	public PostGetContextMethod addRelated(Related... related) {
		addParam("related", related);
		return this;
	}
}

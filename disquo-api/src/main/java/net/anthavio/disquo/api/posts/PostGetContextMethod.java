package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * Returns the hierarchal tree of a post (all parents).
 * http://disqus.com/api/docs/posts/getContext/
 * 
 * @author martin.vanek
 *
 */
public class PostGetContextMethod extends BaseSingleMultiPostMethod<PostGetContextMethod, DisqusPost> {

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

	@Override
	protected PostGetContextMethod getB() {
		return this;
	}
}

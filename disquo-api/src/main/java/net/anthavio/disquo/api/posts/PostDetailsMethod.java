package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author vanek
 * 
 */
public class PostDetailsMethod extends BaseSinglePostMethod<PostDetailsMethod, DisqusPost> {

	public PostDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.details);
	}

	public PostDetailsMethod addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	@Override
	protected PostDetailsMethod getB() {
		return this;
	}

}

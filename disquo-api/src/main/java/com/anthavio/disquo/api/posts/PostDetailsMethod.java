package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author vanek
 * 
 */
public class PostDetailsMethod extends BaseSinglePostMethod<DisqusPost> {

	public PostDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.details);
	}

	public PostDetailsMethod addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

}

package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostUpdateMethod extends BaseSinglePostMethod<DisqusPost> {

	public PostUpdateMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.update);
	}

	public PostUpdateMethod setMessage(String message) {
		addParam("message", message);
		return this;
	}

}

package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseSinglePostMethod<T> extends DisqusMethod<T> {

	public BaseSinglePostMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseSinglePostMethod<T> setPost(long post) {
		addParam("post", post);
		return this;
	}

}

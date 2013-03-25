package com.anthavio.disquo.api.posts;

import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseMultiPostMethod<T> extends DisqusMethod<List<T>> {

	public BaseMultiPostMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseMultiPostMethod<T> addPost(long... post) {
		addParam("post", post);
		return this;
	}

}

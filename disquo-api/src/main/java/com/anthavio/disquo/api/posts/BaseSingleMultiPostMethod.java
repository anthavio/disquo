package com.anthavio.disquo.api.posts;

import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * Single post on input, but multiple on output
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseSingleMultiPostMethod<T> extends BaseSinglePostMethod<List<T>> {

	public BaseSingleMultiPostMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

}

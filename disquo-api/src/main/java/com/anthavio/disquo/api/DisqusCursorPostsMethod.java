package com.anthavio.disquo.api;

import java.util.Date;

import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.Related;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class DisqusCursorPostsMethod<T> extends DisqusCursorMethod<T> {

	public DisqusCursorPostsMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public DisqusCursorPostsMethod<T> setSince(Date since) {
		addParam("since", since);
		return this;
	}

	public DisqusCursorPostsMethod<T> addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	public DisqusCursorPostsMethod<T> setQuery(String query) {
		addParam("query", query);
		return this;
	}

	public DisqusCursorPostsMethod<T> addInclude(PostState... include) {
		addParam("include", include);
		return this;
	}

}

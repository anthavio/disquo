package com.anthavio.disquo.api.forums;

import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseForumListMethod<B extends DisqusMethod<?, List<T>>, T> extends DisqusCursorMethod<B, T> {

	public BaseForumListMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setForum(String forum) {
		addParam("forum", forum);
		return getB();
	}

}

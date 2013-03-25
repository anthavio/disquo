package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseForumMethod<T> extends DisqusMethod<T> {

	public BaseForumMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseForumMethod<T> setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}

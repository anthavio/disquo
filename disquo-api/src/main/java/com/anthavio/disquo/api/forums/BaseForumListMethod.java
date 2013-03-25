package com.anthavio.disquo.api.forums;

import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseForumListMethod<T> extends DisqusMethod<List<T>> {

	public BaseForumListMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseForumListMethod<T> setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}

package com.anthavio.disquo.api.trends;

import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusThread;

/**
 * http://disqus.com/api/docs/trends/listThreads/
 * 
 * @author martin.vanek
 *
 */
public class ListThreads extends DisqusMethod<ListThreads, DisqusThread> {

	public ListThreads(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Trends.listThreads);
	}

	public ListThreads limit(int limit) {
		addParam("limit", limit);
		return this;
	}

	public ListThreads addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	public ListThreads addForum(String... forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ListThreads getB() {
		return this;
	}

}

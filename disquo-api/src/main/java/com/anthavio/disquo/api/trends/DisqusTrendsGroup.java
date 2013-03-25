package com.anthavio.disquo.api.trends;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * http://disqus.com/api/docs/trends/
 * 
 * @author martin.vanek
 *
 */
public class DisqusTrendsGroup extends DisqusFeatureGroup {

	public DisqusTrendsGroup(Disqus disqus) {
		super(disqus);
	}

	//XXX ?provide listThreads as method with arguments?

	/**
	 * http://disqus.com/api/docs/trends/listThreads/
	 *
	 */
	public ListThreads listThreads() {
		return new ListThreads(disqus);
	}
}

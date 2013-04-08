package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class HighlightMethod extends BaseMultiPostMethod<HighlightMethod, DisqusId> {

	public HighlightMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.highlight);
	}

	@Override
	protected HighlightMethod getB() {
		return this;
	}

}

package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostUnhighlightMethod extends BaseMultiPostMethod<PostUnhighlightMethod, DisqusId> {

	public PostUnhighlightMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.unhighlight);
	}

	@Override
	protected PostUnhighlightMethod getB() {
		return this;
	}

}

package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostApproveMethod extends BaseMultiPostMethod<PostApproveMethod, DisqusId> {

	public PostApproveMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.approve);
	}

	@Override
	protected PostApproveMethod getB() {
		return this;
	}

}

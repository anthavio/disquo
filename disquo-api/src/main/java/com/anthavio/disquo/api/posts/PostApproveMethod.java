package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostApproveMethod extends BaseMultiPostMethod<DisqusId> {

	public PostApproveMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.approve);
	}

}

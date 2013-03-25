package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostRestoreMethod extends BaseMultiPostMethod<DisqusId> {

	public PostRestoreMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.restore);
	}

}

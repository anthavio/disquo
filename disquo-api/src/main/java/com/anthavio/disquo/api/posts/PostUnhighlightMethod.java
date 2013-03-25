package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostUnhighlightMethod extends BaseMultiPostMethod<DisqusId> {

	public PostUnhighlightMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.unhighlight);
	}

}

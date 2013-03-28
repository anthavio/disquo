package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * Reports a post (flagging).
 * 
 * @author martin.vanek
 *
 */
public class PostReportMethod extends BaseSinglePostMethod<PostReportMethod, DisqusPost> {

	public PostReportMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.report);
	}

	@Override
	protected PostReportMethod getB() {
		return this;
	}
}

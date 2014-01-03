package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusPost;

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
	protected PostReportMethod getSelf() {
		return this;
	}
}

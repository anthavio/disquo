package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostSpamMethod extends BaseMultiPostMethod<PostSpamMethod, DisqusId> {

	public PostSpamMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.spam);
	}

	@Override
	protected PostSpamMethod getSelf() {
		return this;
	}

}

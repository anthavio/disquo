package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

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

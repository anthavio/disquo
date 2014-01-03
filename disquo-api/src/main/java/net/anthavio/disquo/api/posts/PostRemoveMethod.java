package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostRemoveMethod extends BaseMultiPostMethod<PostRemoveMethod, DisqusId> {

	public PostRemoveMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.remove);
	}

	@Override
	protected PostRemoveMethod getSelf() {
		return this;
	}

}

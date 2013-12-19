package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostRestoreMethod extends BaseMultiPostMethod<PostRestoreMethod, DisqusId> {

	public PostRestoreMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.restore);
	}

	@Override
	protected PostRestoreMethod getB() {
		return this;
	}

}

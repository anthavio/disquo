package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostUnhighlightMethod extends BaseMultiPostMethod<PostUnhighlightMethod, DisqusId> {

	public PostUnhighlightMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.unhighlight);
	}

	@Override
	protected PostUnhighlightMethod getB() {
		return this;
	}

}

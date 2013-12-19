package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class HighlightMethod extends BaseMultiPostMethod<HighlightMethod, DisqusId> {

	public HighlightMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.highlight);
	}

	@Override
	protected HighlightMethod getB() {
		return this;
	}

}

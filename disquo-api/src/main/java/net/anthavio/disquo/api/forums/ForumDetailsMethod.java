package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusForum;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumDetailsMethod extends BaseForumMethod<ForumDetailsMethod, DisqusForum> {

	public ForumDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.details);
	}

	public ForumDetailsMethod addRelated(Related related) {
		addParam("related", related);
		return this;
	}

	@Override
	protected ForumDetailsMethod getSelf() {
		return this;
	}

}

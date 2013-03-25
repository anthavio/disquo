package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.response.DisqusForum;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumDetailsMethod extends BaseForumMethod<DisqusForum> {

	public ForumDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.details);
	}

	public ForumDetailsMethod addRelated(Related related) {
		addParam("related", related);
		return this;
	}

}

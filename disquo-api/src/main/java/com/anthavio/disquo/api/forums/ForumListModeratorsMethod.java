package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusModerator;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListModeratorsMethod extends DisqusCursorMethod<DisqusModerator> {

	public ForumListModeratorsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listModerators);
	}

	public ForumListModeratorsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}

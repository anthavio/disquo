package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusUser;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListMostActiveUsersMethod extends DisqusCursorMethod<DisqusUser> {

	public ForumListMostActiveUsersMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listMostActiveUsers);
	}

	public ForumListMostActiveUsersMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}

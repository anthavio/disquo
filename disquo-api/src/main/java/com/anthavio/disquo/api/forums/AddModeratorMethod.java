package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class AddModeratorMethod extends DisqusMethod<DisqusId> {

	public AddModeratorMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.addModerator);
	}

	public AddModeratorMethod setUser(QUser user) {
		addParam("user", user);
		return this;
	}

	public AddModeratorMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}

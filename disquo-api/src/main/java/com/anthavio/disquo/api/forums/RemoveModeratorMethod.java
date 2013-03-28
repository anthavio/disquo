package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class RemoveModeratorMethod extends BaseForumMethod<RemoveModeratorMethod, DisqusId> {

	public RemoveModeratorMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.removeModerator);
	}

	public RemoveModeratorMethod setModerator(QUser moderator) {
		addParam("moderator", moderator);
		return this;
	}

	@Override
	protected RemoveModeratorMethod getB() {
		return this;
	}

}

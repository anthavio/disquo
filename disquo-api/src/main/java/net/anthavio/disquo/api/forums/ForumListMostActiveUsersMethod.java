package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusUser;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListMostActiveUsersMethod extends DisqusCursorMethod<ForumListMostActiveUsersMethod, DisqusUser> {

	public ForumListMostActiveUsersMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listMostActiveUsers);
	}

	public ForumListMostActiveUsersMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ForumListMostActiveUsersMethod getB() {
		return this;
	}

}

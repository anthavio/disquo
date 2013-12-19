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
public class ForumListUsersMethod extends DisqusCursorMethod<ForumListUsersMethod, DisqusUser> {

	public ForumListUsersMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listUsers);
	}

	public ForumListUsersMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ForumListUsersMethod setSince_id(Integer since_id) {
		addParam("since_id", since_id);
		return this;
	}

	@Override
	protected ForumListUsersMethod getB() {
		return this;
	}

}

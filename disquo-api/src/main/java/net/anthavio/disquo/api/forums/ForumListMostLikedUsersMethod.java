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
public class ForumListMostLikedUsersMethod extends DisqusCursorMethod<ForumListMostLikedUsersMethod, DisqusUser> {

	public ForumListMostLikedUsersMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listMostLikedUsers);
	}

	public ForumListMostLikedUsersMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ForumListMostLikedUsersMethod getB() {
		return this;
	}

}

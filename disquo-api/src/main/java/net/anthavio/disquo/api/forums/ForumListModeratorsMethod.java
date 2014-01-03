package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusModerator;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListModeratorsMethod extends DisqusCursorMethod<ForumListModeratorsMethod, DisqusModerator> {

	public ForumListModeratorsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listModerators);
	}

	public ForumListModeratorsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ForumListModeratorsMethod getSelf() {
		return this;
	}

}

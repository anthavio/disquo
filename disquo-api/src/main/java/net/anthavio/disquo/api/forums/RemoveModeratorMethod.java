package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.response.DisqusId;

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
	protected RemoveModeratorMethod getSelf() {
		return this;
	}

}

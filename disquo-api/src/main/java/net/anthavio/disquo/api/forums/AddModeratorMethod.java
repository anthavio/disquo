package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.response.DisqusId;

/**
 * 
 * @author martin.vanek
 *
 */
public class AddModeratorMethod extends DisqusMethod<AddModeratorMethod, DisqusId> {

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

	@Override
	protected AddModeratorMethod getSelf() {
		return this;
	}

}

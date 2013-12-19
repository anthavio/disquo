package net.anthavio.disquo.api.users;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;

/**
 * 
 * @author martin.vanek
 *
 */
public class UnfollowMethod extends DisqusMethod<UnfollowMethod, Object> {

	public UnfollowMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.unfollow);
	}

	public UnfollowMethod setUser(long userId) {
		return setUser(QUser.build(userId));
	}

	public UnfollowMethod setUser(String username) {
		return setUser(QUser.build(username));
	}

	public UnfollowMethod setUser(QUser user) {
		addParam("target", user);
		return this;
	}

	@Override
	protected UnfollowMethod getB() {
		return this;
	}

}

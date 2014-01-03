package net.anthavio.disquo.api.users;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.response.DisqusForum;

/**
 * 
 * @author martin.vanek
 *
 */
public class UserListForumsMethod extends DisqusCursorMethod<UserListForumsMethod, DisqusForum> {

	public UserListForumsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.listForums);
	}

	public UserListForumsMethod setSinceId(Integer since_id) {
		addParam("since_id", since_id);
		return this;
	}

	public UserListForumsMethod setUser(long userId) {
		return setUser(QUser.build(userId));
	}

	public UserListForumsMethod setUser(String username) {
		return setUser(QUser.build(username));
	}

	public UserListForumsMethod setUser(QUser user) {
		addParam("user", user);
		return this;
	}

	@Override
	protected UserListForumsMethod getSelf() {
		return this;
	}

}

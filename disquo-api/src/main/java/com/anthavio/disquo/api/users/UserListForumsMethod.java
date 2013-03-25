package com.anthavio.disquo.api.users;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.response.DisqusForum;

/**
 * 
 * @author martin.vanek
 *
 */
public class UserListForumsMethod extends DisqusCursorMethod<DisqusForum> {

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

}

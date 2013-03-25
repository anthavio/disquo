package com.anthavio.disquo.api.users;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QUser;

/**
 * 
 * @author martin.vanek
 *
 */
public class UnfollowMethod extends DisqusMethod<Object> {

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

}

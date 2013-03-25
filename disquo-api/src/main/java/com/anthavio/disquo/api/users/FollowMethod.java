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
public class FollowMethod extends DisqusMethod<Object> {

	public FollowMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.follow);
	}

	public FollowMethod setUser(long userId) {
		return setUser(QUser.build(userId));
	}

	public FollowMethod setUser(String username) {
		return setUser(QUser.build(username));
	}

	public FollowMethod setUser(QUser user) {
		addParam("target", user);
		return this;
	}

}

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
public abstract class BaseSingleUserMethod<T> extends DisqusMethod<T> {

	public BaseSingleUserMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public BaseSingleUserMethod<T> setUser(long userId) {
		return setUser(QUser.build(userId));
	}

	public BaseSingleUserMethod<T> setUser(String username) {
		return setUser(QUser.build(username));
	}

	public BaseSingleUserMethod<T> setUser(QUser user) {
		addParam("user", user);
		return this;
	}

}

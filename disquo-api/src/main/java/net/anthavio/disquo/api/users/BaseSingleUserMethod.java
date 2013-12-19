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
public abstract class BaseSingleUserMethod<B extends DisqusMethod<?, T>, T> extends DisqusMethod<B, T> {

	public BaseSingleUserMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setUser(long userId) {
		return setUser(QUser.build(userId));
	}

	public B setUser(String username) {
		return setUser(QUser.build(username));
	}

	public B setUser(QUser user) {
		addParam("user", user);
		return getB();
	}

}

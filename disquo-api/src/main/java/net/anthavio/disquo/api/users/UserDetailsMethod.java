package net.anthavio.disquo.api.users;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusUser;

/**
 * 
 * @author martin.vanek
 *
 */
public class UserDetailsMethod extends BaseSingleUserMethod<UserDetailsMethod, DisqusUser> {

	public UserDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.details);
	}

	@Override
	protected UserDetailsMethod getSelf() {
		return this;
	}

}

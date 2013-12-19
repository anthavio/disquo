package net.anthavio.disquo.api.users;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;

/**
 * Throws exception when username already exists
 * 
 * @author martin.vanek
 *
 */
public class CheckUsernameMethod extends DisqusMethod<CheckUsernameMethod, String> {

	public CheckUsernameMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.checkUsername);
	}

	public CheckUsernameMethod setUsername(String username) {
		addParam("username", username);
		return this;
	}

	@Override
	protected CheckUsernameMethod getB() {
		return this;
	}

}

package com.anthavio.disquo.api.users;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * Throws exception when username already exists
 * 
 * @author martin.vanek
 *
 */
public class CheckUsernameMethod extends DisqusMethod<String> {

	public CheckUsernameMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.checkUsername);
	}

	public CheckUsernameMethod setUsername(String username) {
		addParam("username", username);
		return this;
	}

}

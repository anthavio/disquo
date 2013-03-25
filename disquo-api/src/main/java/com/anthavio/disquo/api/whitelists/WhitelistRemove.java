package com.anthavio.disquo.api.whitelists;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.forums.BaseForumMethod;
import com.anthavio.disquo.api.response.DisqusFilter;

/**
 * 
 * @author martin.vanek
 *
 */
public class WhitelistRemove extends BaseForumMethod<DisqusFilter> {

	public WhitelistRemove(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Whitelists.remove);
	}

	public WhitelistRemove addUser(String... user) {
		for (String u : user) {
			addParam("user", QUser.build(u));
		}
		return this;
	}

	public WhitelistRemove addEmail(String... email) {
		addParam("email", email);
		return this;
	}
}

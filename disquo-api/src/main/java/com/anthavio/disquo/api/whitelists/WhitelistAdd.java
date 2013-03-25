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
public class WhitelistAdd extends BaseForumMethod<DisqusFilter> {

	public WhitelistAdd(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Whitelists.add);
	}

	public WhitelistAdd setNotes(String notes) {
		addParam("notes", notes);
		return this;
	}

	public WhitelistAdd addUser(String... user) {
		for (String u : user) {
			addParam("user", QUser.build(u));
		}
		return this;
	}

	public WhitelistAdd addEmail(String... email) {
		addParam("email", email);
		return this;
	}

}

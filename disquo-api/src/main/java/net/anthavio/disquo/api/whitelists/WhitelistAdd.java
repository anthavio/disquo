package net.anthavio.disquo.api.whitelists;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.forums.BaseForumMethod;
import net.anthavio.disquo.api.response.DisqusFilter;

/**
 * 
 * @author martin.vanek
 *
 */
public class WhitelistAdd extends BaseForumMethod<WhitelistAdd, DisqusFilter> {

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

	@Override
	protected WhitelistAdd getB() {
		return this;
	}

}

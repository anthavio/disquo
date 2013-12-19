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
public class WhitelistRemove extends BaseForumMethod<WhitelistRemove, DisqusFilter> {

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

	@Override
	protected WhitelistRemove getB() {
		return this;
	}
}

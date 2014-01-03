package net.anthavio.disquo.api.blacklists;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.forums.BaseForumListMethod;
import net.anthavio.disquo.api.response.DisqusFilter;

/**
 * 
 * @author martin.vanek
 *
 */
public class BlacklistRemove extends BaseForumListMethod<BlacklistRemove, DisqusFilter> {

	public BlacklistRemove(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Blacklists.remove);
	}

	public BlacklistRemove addDomain(String... domain) {
		addParam("domain", domain);
		return this;
	}

	public BlacklistRemove addWord(String... word) {
		addParam("word", word);
		return this;
	}

	public BlacklistRemove addIp(String... ip) {
		addParam("ip", ip);
		return this;
	}

	public BlacklistRemove addUser(String... user) {
		for (String u : user) {
			addParam("user", QUser.build(u));
		}
		return this;
	}

	public BlacklistRemove addEmail(String... email) {
		addParam("email", email);
		return this;
	}

	@Override
	protected BlacklistRemove getSelf() {
		return this;
	}
}

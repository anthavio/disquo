package net.anthavio.disquo.api.blacklists;

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
public class BlacklistAdd extends BaseForumMethod<BlacklistAdd, DisqusFilter> {

	public BlacklistAdd(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Blacklists.add);
	}

	public BlacklistAdd setRetroactive(boolean retroactive) {
		addParam("retroactive", retroactive);
		return this;
	}

	public BlacklistAdd setNotes(String notes) {
		addParam("notes", notes);
		return this;
	}

	public BlacklistAdd addDomain(String... domain) {
		addParam("domain", domain);
		return this;
	}

	public BlacklistAdd addWord(String... word) {
		addParam("word", word);
		return this;
	}

	public BlacklistAdd addIp(String... ip) {
		addParam("ip", ip);
		return this;
	}

	public BlacklistAdd addUser(String... user) {
		for (String u : user) {
			addParam("user", QUser.build(u));
		}
		return this;
	}

	public BlacklistAdd addEmail(String... email) {
		addParam("email", email);
		return this;
	}

	@Override
	protected BlacklistAdd getSelf() {
		return this;
	}

}

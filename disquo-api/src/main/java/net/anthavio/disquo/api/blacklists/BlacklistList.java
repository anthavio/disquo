package net.anthavio.disquo.api.blacklists;

import java.util.Date;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusFilter;


/**
 * 
 * @author martin.vanek
 *
 */
public class BlacklistList extends DisqusCursorMethod<BlacklistList, DisqusFilter> {

	public BlacklistList(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Blacklists.list);
	}

	public BlacklistList setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public BlacklistList setSince(Date since) {
		addParam("since", since);
		return this;
	}

	public BlacklistList addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	public BlacklistList addType(FilterType... type) {
		addParam("type", type);
		return this;
	}

	@Override
	protected BlacklistList getSelf() {
		return this;
	}

}

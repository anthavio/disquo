package net.anthavio.disquo.api.whitelists;

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
public class WhitelistList extends DisqusCursorMethod<WhitelistList, DisqusFilter> {

	public WhitelistList(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Whitelists.list);
	}

	public WhitelistList setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public WhitelistList setSince(Date since) {
		addParam("since", since);
		return this;
	}

	public WhitelistList addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	public WhitelistList addType(FilterType... type) {
		addParam("type", type);
		return this;
	}

	@Override
	protected WhitelistList getB() {
		return this;
	}

}

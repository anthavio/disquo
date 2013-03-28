package com.anthavio.disquo.api.blacklists;

import java.util.Date;

import com.anthavio.disquo.api.ArgumentConfig.FilterType;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusFilter;

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
	protected BlacklistList getB() {
		return this;
	}

}

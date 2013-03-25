package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusForum;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumCreateMethod extends DisqusMethod<DisqusForum> {

	public ForumCreateMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.create);
	}

	public ForumCreateMethod setWebsite(String website) {
		addParam("website", website);
		return this;
	}

	public ForumCreateMethod setName(String name) {
		addParam("name", name);
		return this;
	}

	public ForumCreateMethod setShortName(String shortName) {
		addParam("short_name", shortName);
		return this;
	}

}

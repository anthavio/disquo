package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusForum;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumCreateMethod extends DisqusMethod<ForumCreateMethod, DisqusForum> {

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

	@Override
	protected ForumCreateMethod getSelf() {
		return this;
	}

}

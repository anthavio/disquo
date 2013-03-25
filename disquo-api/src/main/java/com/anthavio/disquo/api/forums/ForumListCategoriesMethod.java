package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusCategory;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListCategoriesMethod extends DisqusCursorMethod<DisqusCategory> {

	public ForumListCategoriesMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Forums.listCategories);
	}

	public ForumListCategoriesMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ForumListCategoriesMethod setSince_id(String since_id) {
		addParam("since_id", since_id);
		return this;
	}

}

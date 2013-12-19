package net.anthavio.disquo.api.forums;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusCategory;

/**
 * 
 * @author martin.vanek
 *
 */
public class ForumListCategoriesMethod extends DisqusCursorMethod<ForumListCategoriesMethod, DisqusCategory> {

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

	@Override
	protected ForumListCategoriesMethod getB() {
		return this;
	}

}

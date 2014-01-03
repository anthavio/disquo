package net.anthavio.disquo.api.category;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusCategory;

/**
 * http://disqus.com/api/docs/categories/list/
 * 
 * @author martin.vanek
 *
 */
public class CategoryListMethod extends DisqusCursorMethod<CategoryListMethod, DisqusCategory> {

	public CategoryListMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Category.list);
	}

	public CategoryListMethod addForum(String... forum) {
		addParam("forum", forum);
		return this;
	}

	public CategoryListMethod setSince_id(String since_id) {
		addParam("since_id", since_id);
		return this;
	}

	@Override
	protected CategoryListMethod getSelf() {
		return this;
	}

}

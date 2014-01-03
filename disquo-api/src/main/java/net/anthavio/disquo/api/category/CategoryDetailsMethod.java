package net.anthavio.disquo.api.category;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusCategory;

/**
 * 
 * @author vanek
 * 
 */
public class CategoryDetailsMethod extends DisqusMethod<CategoryDetailsMethod, DisqusCategory> {

	public CategoryDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Category.details);
	}

	public CategoryDetailsMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

	@Override
	protected CategoryDetailsMethod getSelf() {
		return this;
	}

}

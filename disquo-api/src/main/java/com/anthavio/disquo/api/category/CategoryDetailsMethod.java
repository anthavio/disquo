package com.anthavio.disquo.api.category;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusCategory;

/**
 * 
 * @author vanek
 * 
 */
public class CategoryDetailsMethod extends DisqusMethod<DisqusCategory> {

	public CategoryDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Category.details);
	}

	public CategoryDetailsMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

}

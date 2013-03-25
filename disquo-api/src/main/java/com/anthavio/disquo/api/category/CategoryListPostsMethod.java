package com.anthavio.disquo.api.category;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class CategoryListPostsMethod extends DisqusCursorPostsMethod<DisqusPost> {

	public CategoryListPostsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Category.listPosts);
	}

	public CategoryListPostsMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

}

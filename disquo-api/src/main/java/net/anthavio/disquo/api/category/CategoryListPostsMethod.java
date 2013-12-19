package net.anthavio.disquo.api.category;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorPostsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class CategoryListPostsMethod extends DisqusCursorPostsMethod<CategoryListPostsMethod, DisqusPost> {

	public CategoryListPostsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Category.listPosts);
	}

	public CategoryListPostsMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

	@Override
	protected CategoryListPostsMethod getB() {
		return this;
	}

}

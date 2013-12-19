package net.anthavio.disquo.api.category;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusCategoryGroup extends DisqusFeatureGroup {

	public DisqusCategoryGroup(Disqus disqus) {
		super(disqus);
	}

	public CategoryCreateMethod create(String forum, String title) {
		return new CategoryCreateMethod(disqus).setForum(forum).setTitle(title);
	}

	public CategoryDetailsMethod details(long category) {
		return new CategoryDetailsMethod(disqus).setCategory(category);
	}

	public CategoryListMethod list(String forum) {
		return new CategoryListMethod(disqus).addForum(forum);
	}

	public CategoryListPostsMethod listPosts(long category) {
		return new CategoryListPostsMethod(disqus).setCategory(category);
	}

	public CategoryListThreadsMethod listThreads(long category) {
		return new CategoryListThreadsMethod(disqus).setCategory(category);
	}
}

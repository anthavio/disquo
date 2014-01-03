package net.anthavio.disquo.api.category;

import java.util.Date;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusThread;


/**
 * 
 * @author martin.vanek
 * 
 * XXX extends DisqusCursorThreadMethod
 */
public class CategoryListThreadsMethod extends DisqusCursorMethod<CategoryListThreadsMethod, DisqusThread> {

	public CategoryListThreadsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Category.listThreads);
	}

	public CategoryListThreadsMethod setCategory(long category) {
		addParam("category", category);
		return this;
	}

	public CategoryListThreadsMethod setSince(Date since) {
		addParam("since", since);
		return this;
	}

	public CategoryListThreadsMethod addRelated(Related... related) {
		addParam("related", related);
		return this;
	}

	@Override
	protected CategoryListThreadsMethod getSelf() {
		return this;
	}

}

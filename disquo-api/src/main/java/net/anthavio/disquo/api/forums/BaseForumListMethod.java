package net.anthavio.disquo.api.forums;

import java.util.List;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorMethod;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;


/**
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseForumListMethod<B extends DisqusMethod<?, List<T>>, T> extends DisqusCursorMethod<B, T> {

	public BaseForumListMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setForum(String forum) {
		addParam("forum", forum);
		return getB();
	}

}

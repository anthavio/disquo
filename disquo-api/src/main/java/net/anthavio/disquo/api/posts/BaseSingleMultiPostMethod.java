package net.anthavio.disquo.api.posts;

import java.util.List;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;


/**
 * Single post on input, but multiple on output
 * 
 * @author martin.vanek
 *
 */
public abstract class BaseSingleMultiPostMethod<B extends DisqusMethod<?, List<T>>, T> extends
		BaseSinglePostMethod<B, List<T>> {

	public BaseSingleMultiPostMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

}

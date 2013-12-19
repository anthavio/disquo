package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Order;


/**
 * Base class for cusror based requests
 * 
 * @author martin.vanek
 * @param <T>
 *
 */
public abstract class DisqusCursorMethod<B extends DisqusMethod<?, List<T>>, T> extends DisqusMethod<B, List<T>> {

	public DisqusCursorMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public B setCursor(String cursor) {
		addParam("cursor", cursor);
		return getB();
	}

	public B setLimit(int limit) {
		addParam("limit", limit);
		return getB();
	}

	public B setOrder(Order order) {
		addParam("order", order);
		return getB();
	}

}

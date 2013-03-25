package com.anthavio.disquo.api;

import java.util.List;

import com.anthavio.disquo.api.ArgumentConfig.Order;

/**
 * Base class for cusror based requests
 * 
 * @author martin.vanek
 * @param <T>
 *
 */
public abstract class DisqusCursorMethod<T> extends DisqusMethod<List<T>> {

	public DisqusCursorMethod(Disqus disqus, DisqusMethodConfig config) {
		super(disqus, config);
	}

	public DisqusCursorMethod<T> setCursor(String cursor) {
		addParam("cursor", cursor);
		return this;
	}

	public DisqusCursorMethod<T> setLimit(int limit) {
		addParam("limit", limit);
		return this;
	}

	public DisqusCursorMethod<T> setOrder(Order order) {
		addParam("order", order);
		return this;
	}

}

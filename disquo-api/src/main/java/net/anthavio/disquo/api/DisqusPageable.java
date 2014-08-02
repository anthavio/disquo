package net.anthavio.disquo.api;

import java.util.Date;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.httl.api.RestVar;

/**
 * Can be used as @RestCall method parameter 
 * 
 * https://disqus.com/api/docs/cursors/
 * 
 * @author martin.vanek
 *
 */
@RestVar
public class DisqusPageable {

	private Date since;

	private String cursor;

	private Integer limit;

	private String query;

	private Order order;

	public DisqusPageable(String cursor) {
		this.cursor = cursor;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}

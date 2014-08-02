package net.anthavio.disquo.api;

import java.util.Date;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.httl.api.RestVar;

/**
 * Can be used as @RestCall method parameter 
 * 
 * @author martin.vanek
 *
 */
@RestVar
public class DisqusPage {

	private Date since;

	private String cursor;

	private Integer limit;

	private Order order;

	public DisqusPage(String cursor) {
		this.cursor = cursor;
	}

	public DisqusPage(Date since, String cursor, Integer limit, Order order) {
		this.since = since;
		this.cursor = cursor;
		this.limit = limit;
		this.order = order;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}

package com.anthavio.disquo.api;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author martin.vanek
 *
 */
public class QUser implements Queryable {

	private final String id;

	private final String username;

	public static QUser build(Number id) {
		return new QUser(id.longValue());
	}

	public static QUser build(long id) {
		return new QUser(id);
	}

	public static QUser build(String username) {
		if (StringUtils.isBlank(username)) {
			throw new IllegalArgumentException("Username is null or blank");
		}
		return new QUser(username);
	}

	public QUser(long id) {
		this.id = String.valueOf(id);
		this.username = null;
	}

	public QUser(String username) {
		this.id = null;
		this.username = username;
	}

	public String getQuery() {
		if (id != null) {
			return null;
		} else {
			return "username";
		}
	}

	public String getValue() {
		if (id != null) {
			return id;
		} else {
			return username;
		}
	}

	@Override
	public String toString() {
		return getValue();
	}

}

package net.anthavio.disquo.api;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author martin.vanek
 *
 */
public class QThread implements Queryable {

	private final String id;

	private final String ident;

	private final String link;

	public static QThread build(Number id) {
		return new QThread(id.longValue());
	}

	public static QThread build(long id) {
		return new QThread(id);
	}

	public static QThread ByIdent(String ident) {
		if (StringUtils.isBlank(ident)) {
			throw new IllegalArgumentException("ident is null or blank");
		}
		return new QThread(ident, null);
	}

	public static QThread ByLink(String link) {
		if (StringUtils.isBlank(link)) {
			throw new IllegalArgumentException("link is null or blank");
		}
		return new QThread(null, link);
	}

	public QThread(long id) {
		this.id = String.valueOf(id);
		this.ident = null;
		this.link = null;
	}

	private QThread(String ident, String link) {
		this.id = null;
		this.ident = ident;
		this.link = link;
	}

	public String getQuery() {
		if (id != null) {
			return null;
		} else if (ident != null) {
			return "ident";
		} else {
			return "link";
		}
	}

	public String getValue() {
		if (id != null) {
			return id;
		} else if (ident != null) {
			return ident;
		} else {
			return link;
		}
	}

	@Override
	public String toString() {
		return getValue();
	}

}

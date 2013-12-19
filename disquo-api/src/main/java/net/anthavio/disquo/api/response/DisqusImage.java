package net.anthavio.disquo.api.response;

import java.io.Serializable;

public class DisqusImage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String permalink;
	private String cache;

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}

}

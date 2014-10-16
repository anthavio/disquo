package net.anthavio.disquo.api.response;

import java.io.Serializable;

/**
 * Categories exist for grouping threads within a forum. These are currently only used in conjunction with the API.
 * 
 * https://help.disqus.com/customer/portal/articles/1131785
 * 
 * @author martin.vanek
 *
 */
public class DisqusCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Unique numerical ID of the category across the entire Disqus network
	 */
	private Long id;

	/**
	 * The chosen title of the category from the Disqus admin settings
	 */
	private String title;

	/**
	 * The Disqus shortname that the category belongs to
	 */
	private String forum;

	/**
	 * The order that the category is listed as
	 */
	private Integer order;

	/**
	 * Whether or not this is the default category for the website
	 */
	private Boolean isDefault;

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}

	@Override
	public int hashCode() {
		return this.id == null ? 0 : this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DisqusCategory other = (DisqusCategory) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getForum() {
		return this.forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public Boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}

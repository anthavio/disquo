package com.anthavio.disquo.api.response;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusModerator {

	private Long id;

	private String forum;

	private DisqusUser user;

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
		DisqusModerator other = (DisqusModerator) obj;
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

	public String getForum() {
		return this.forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public DisqusUser getUser() {
		return this.user;
	}

	public void setUser(DisqusUser user) {
		this.user = user;
	}

}

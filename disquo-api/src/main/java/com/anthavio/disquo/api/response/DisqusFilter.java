package com.anthavio.disquo.api.response;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author martin.vanek
 * 
 */
public class DisqusFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String forum;

	private String notes;

	private String value; // type=email,word,domain,ip

	private DisqusUser user; // type==user

	private String type; // maybe enum

	private Date createdAt;

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
		DisqusFilter other = (DisqusFilter) obj;
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

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DisqusUser getUser() {
		return this.user;
	}

	public void setUser(DisqusUser user) {
		this.user = user;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}

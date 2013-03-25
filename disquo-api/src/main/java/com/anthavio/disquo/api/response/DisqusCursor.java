package com.anthavio.disquo.api.response;

import java.io.Serializable;

public class DisqusCursor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private Boolean hasNext;

	private String next;

	private Boolean hasPrev;

	private String prev;

	private Boolean more;

	private Integer total;

	public DisqusCursor() {

	}

	public DisqusCursor(String id) {
		this.id = id;
		this.hasPrev = false;
		this.hasNext = false;
		this.more = false;
	}

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
		DisqusCursor other = (DisqusCursor) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getHasNext() {
		return this.hasNext;
	}

	public void setHasNext(Boolean nasNext) {
		this.hasNext = nasNext;
	}

	public String getNext() {
		return this.next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public Boolean getHasPrev() {
		return this.hasPrev;
	}

	public void setHasPrev(Boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public String getPrev() {
		return this.prev;
	}

	public void setPrev(String prev) {
		this.prev = prev;
	}

	public Boolean getMore() {
		return this.more;
	}

	public void setMore(Boolean more) {
		this.more = more;
	}

	public Integer getTotal() {
		return this.total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}

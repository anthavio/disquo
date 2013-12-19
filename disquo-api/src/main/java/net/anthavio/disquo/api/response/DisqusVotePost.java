package net.anthavio.disquo.api.response;

import java.io.Serializable;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusVotePost implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Integer likesDelta;

	private Integer dislikesDelta;

	private Integer delta;

	private Integer vote;

	private DisqusPost post;

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
		DisqusVotePost other = (DisqusVotePost) obj;
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

	public Integer getLikesDelta() {
		return this.likesDelta;
	}

	public void setLikesDelta(Integer likesDelta) {
		this.likesDelta = likesDelta;
	}

	public Integer getDislikesDelta() {
		return this.dislikesDelta;
	}

	public void setDislikesDelta(Integer dislikesDelta) {
		this.dislikesDelta = dislikesDelta;
	}

	public Integer getDelta() {
		return this.delta;
	}

	public void setDelta(Integer delta) {
		this.delta = delta;
	}

	public Integer getVote() {
		return this.vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	public DisqusPost getPost() {
		return this.post;
	}

	public void setPost(DisqusPost post) {
		this.post = post;
	}

}

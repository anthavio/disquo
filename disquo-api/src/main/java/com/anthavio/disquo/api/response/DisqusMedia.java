package com.anthavio.disquo.api.response;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusMedia {

	private String forum;

	private Long thread;

	private String location;

	private Long post;

	private String thumbnailURL;

	private Integer type;

	private Metadata metadata;

	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public Long getThread() {
		return thread;
	}

	public void setThread(Long thread) {
		this.thread = thread;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getPost() {
		return post;
	}

	public void setPost(Long post) {
		this.post = post;
	}

	public String getThumbnailURL() {
		return thumbnailURL;
	}

	public void setThumbnailURL(String thumbnailURL) {
		this.thumbnailURL = thumbnailURL;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public static class Metadata {

		private String create_method;

		private String thumbnail;

		public String getCreate_method() {
			return create_method;
		}

		public void setCreate_method(String create_method) {
			this.create_method = create_method;
		}

		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		@Override
		public String toString() {
			return JsonStringBuilder.toString(this);
		}
	}

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}
}

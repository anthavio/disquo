package net.anthavio.disquo.api.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author martin.vanek
 * 
 */
public class DisqusThread implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	// private String forumId;

	private Object forum; // Related.forum

	// private Integer categoryId;

	private Object category; // Related.category

	// private Integer authorId;

	private Object author; // Related.author

	private String title;

	private String clean_title;

	private Date createdAt;

	private List<String> identifiers;

	private String ipAddress;

	private Integer posts;

	private Integer reactions;

	private Integer userScore;

	private Integer likes;

	private Integer dislikes;

	private Boolean isClosed;

	private Boolean isDeleted;

	private Boolean canModerate;

	private Boolean canPost;

	private Boolean userSubscription;

	private String feed;

	private String slug;

	private String link;

	private String message;

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
		DisqusThread other = (DisqusThread) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public void setInitialValues() {
		setDislikes(0);
		setLikes(0);
		setPosts(0);
		setReactions(0);
		setUserScore(0);

		setCanModerate(true);
		setCanPost(true);
		setIsClosed(false);
		setIsDeleted(false);
		setUserSubscription(true);
	}

	public Boolean getCanModerate() {
		return this.canModerate;
	}

	public void setCanModerate(Boolean canModerate) {
		this.canModerate = canModerate;
	}

	public Boolean getCanPost() {
		return this.canPost;
	}

	public void setCanPost(Boolean canPost) {
		this.canPost = canPost;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Object getForum() {
		return this.forum;
	}

	public void setForum(Object forum) {
		this.forum = forum;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCleanTitle() {
		return clean_title;
	}

	public void setCleanTitle(String clean_title) {
		this.clean_title = clean_title;
	}

	public Object getCategory() {
		return this.category;
	}

	public void setCategory(Object category) {
		this.category = category;
	}

	public Object getAuthor() {
		return this.author;
	}

	public void setAuthor(Object author) {
		this.author = author;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getIdentifiers() {
		return this.identifiers;
	}

	public void setIdentifiers(List<String> identifiers) {
		this.identifiers = identifiers;
	}

	public Integer getPosts() {
		return this.posts;
	}

	public void setPosts(Integer posts) {
		this.posts = posts;
	}

	public Integer getReactions() {
		return this.reactions;
	}

	public void setReactions(Integer reactions) {
		this.reactions = reactions;
	}

	public Integer getUserScore() {
		return this.userScore;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getDislikes() {
		return this.dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	public Boolean getIsClosed() {
		return this.isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getUserSubscription() {
		return this.userSubscription;
	}

	public void setUserSubscription(Boolean userSubscription) {
		this.userSubscription = userSubscription;
	}

	public String getFeed() {
		return this.feed;
	}

	public void setFeed(String feed) {
		this.feed = feed;
	}

	public String getSlug() {
		return this.slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}

package net.anthavio.disquo.api.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * An individual comment within a thread
 * https://help.disqus.com/customer/portal/articles/1131785
 * 
 * @author martin.vanek
 * 
 */
public class DisqusPost implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The network-wide unique ID number associated with the comment.
	 */
	private Long id;

	/**
	 * The ID number of the parent comment. Will return null if it's a top-level comment. NOTE: This is changing to a string in the near future. See latest API responses for current status
	 */
	private Long parent;

	/**
	 * The Disqus thread ID number that the comment belongs to. Typically this is expandable by using related=thread.
	 */
	private Object thread; // Related.thread

	/**
	 * The Disqus shortname of the forum which the comment was posted under.
	 */
	private Object forum; // Related.forum

	/**
	 * Information about the comment author
	 */
	private DisqusUser author;

	/**
	 * The timestamp of the comment when it was created in the Disqus system.
	 */
	private Date createdAt;

	private String ipAddress;

	/**
	 * Whether or not the comment is publicly visible. Unapproved comments are only visible to the comment author and forum moderators.
	 */
	private Boolean isApproved;

	/**
	 * Whether the comment has been edited by the commenter or moderator.
	 */
	private Boolean isEdited;

	/**
	 * Whether the comment has been deleted or not.
	 */
	private Boolean isDeleted;

	/**
	 * Whether or not the comment has been marked as spam.
	 */
	private Boolean isSpam;

	/**
	 * Whether or not a thread author or forum moderator has "highlighted" the comment.
	 */
	private Boolean isHighlighted;

	/**
	 * Whether or not the comment has been flagged by at least one other user.
	 */
	private Boolean isFlagged;
	/**
	 * (currently unused) Whether the comment has been system-flagged.
	 */
	private Boolean isJuliaFlagged;

	/**
	 * The unformatted comment body.
	 */
	private String raw_message;

	/**
	 * The comment body with HTML formatting.
	 */
	private String message;
	private String url;

	/**
	 * The number of unique likes (upvotes) that the comment has received.
	 */
	private Integer likes;
	/**
	 * The number of dislikes (downvotes) a comment has received.
	 */
	private Integer dislikes;

	/**
	 * The number of unique flags that the comment has received from other users.
	 */
	private Integer numReports;

	/**
	 * The sum of the likes (upvotes) and the dislikes (downvotes).
	 */
	private Integer points;

	/**
	 * The score that the authenticated user has given to a particular comment. Typically this is just -1, 0 or 1.
	 */
	private Integer userScore;

	/**
	 * Links to images that the user attached to their comment.
	 */
	private List<DisqusMedia> media;
	private DisqusCoordinates approxLoc;

	private Integer depth; // property is present only in Disqus hacky download

	// (we have to set it by ourself)

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
		DisqusPost other = (DisqusPost) obj;
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

		setApproxLoc(null); // whatever
		setMedia(null); // whatever
		setUrl(null);// whatever

		setDislikes(0);
		setLikes(0);
		setNumReports(0);
		setPoints(0);
		setUserScore(0);

		setIsApproved(true);
		setIsDeleted(false);
		setIsEdited(false);
		setIsFlagged(false);
		setIsHighlighted(false);
		setIsJuliaFlagged(true);
		setIsSpam(false);
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParent() {
		return this.parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Object getThread() {
		return this.thread;
	}

	public void setThread(Object thread) {
		this.thread = thread;
	}

	public Object getForum() {
		return this.forum;
	}

	public void setForum(Object forum) {
		this.forum = forum;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public DisqusUser getAuthor() {
		return this.author;
	}

	public void setAuthor(DisqusUser author) {
		this.author = author;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<DisqusMedia> getMedia() {
		return this.media;
	}

	public void setMedia(List<DisqusMedia> media) {
		this.media = media;
	}

	public Boolean getIsApproved() {
		return this.isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getIsEdited() {
		return this.isEdited;
	}

	public void setIsEdited(Boolean isEdited) {
		this.isEdited = isEdited;
	}

	public Boolean getIsSpam() {
		return this.isSpam;
	}

	public void setIsSpam(Boolean isSpam) {
		this.isSpam = isSpam;
	}

	public Boolean getIsFlagged() {
		return this.isFlagged;
	}

	public void setIsFlagged(Boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	public Boolean getIsJuliaFlagged() {
		return this.isJuliaFlagged;
	}

	public void setIsJuliaFlagged(Boolean isJuliaFlagged) {
		this.isJuliaFlagged = isJuliaFlagged;
	}

	public Boolean getIsHighlighted() {
		return this.isHighlighted;
	}

	public void setIsHighlighted(Boolean isHighlighted) {
		this.isHighlighted = isHighlighted;
	}

	public String getRaw_message() {
		return this.raw_message;
	}

	public void setRaw_message(String raw_message) {
		this.raw_message = raw_message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public Integer getNumReports() {
		return this.numReports;
	}

	public void setNumReports(Integer numReports) {
		this.numReports = numReports;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getUserScore() {
		return this.userScore;
	}

	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}

	public DisqusCoordinates getApproxLoc() {
		return this.approxLoc;
	}

	public void setApproxLoc(DisqusCoordinates approxLoc) {
		this.approxLoc = approxLoc;
	}

	public Integer getDepth() {
		return this.depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

}
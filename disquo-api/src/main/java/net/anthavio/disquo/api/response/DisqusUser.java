package net.anthavio.disquo.api.response;

import java.io.Serializable;
import java.util.Date;

/**
 * @author martin.vanek
 * 
 */
public class DisqusUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String name;

	private Float rep;

	private Date joinedAt; // "2012-11-06T16:55:31"

	private String about;

	private String url;

	private String profileUrl;

	private String email;

	private String emailHash;

	private String location;

	private Boolean isPrimary;

	private Boolean isAnonymous;

	private Boolean isVerified;

	private Boolean isFollowing;

	private Boolean isFollowedBy;

	private Integer numPosts;

	private Integer numLikesReceived;

	private Integer numFollowing;

	private Integer numFollowers;

	private Avatar avatar;

	private Remote remote; // SSO users

	private Object connections; // ???

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
		DisqusUser other = (DisqusUser) obj;
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

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Float getRep() {
		return this.rep;
	}

	public void setRep(Float rep) {
		this.rep = rep;
	}

	public Float getReputation() {
		return this.rep;
	}

	public void setReputation(Float rep) {
		this.rep = rep;
	}

	public Date getJoinedAt() {
		return this.joinedAt;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}

	public String getProfileUrl() {
		return this.profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailHash() {
		return this.emailHash;
	}

	public void setEmailHash(String emailHash) {
		this.emailHash = emailHash;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Boolean getIsPrimary() {
		return this.isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Boolean getIsAnonymous() {
		return this.isAnonymous;
	}

	public void setIsAnonymous(Boolean isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public Boolean getIsVerified() {
		return this.isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getIsFollowing() {
		return this.isFollowing;
	}

	public void setIsFollowing(Boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

	public Boolean getIsFollowedBy() {
		return this.isFollowedBy;
	}

	public void setIsFollowedBy(Boolean isFollowedBy) {
		this.isFollowedBy = isFollowedBy;
	}

	public Integer getNumFollowers() {
		return this.numFollowers;
	}

	public void setNumFollowers(Integer numFollowers) {
		this.numFollowers = numFollowers;
	}

	public Integer getNumPosts() {
		return this.numPosts;
	}

	public void setNumPosts(Integer numPosts) {
		this.numPosts = numPosts;
	}

	public Integer getNumLikesReceived() {
		return this.numLikesReceived;
	}

	public void setNumLikesReceived(Integer numLikesReceived) {
		this.numLikesReceived = numLikesReceived;
	}

	public Integer getNumFollowing() {
		return this.numFollowing;
	}

	public void setNumFollowing(Integer numFollowing) {
		this.numFollowing = numFollowing;
	}

	public Object getConnections() {
		return this.connections;
	}

	public void setConnections(Object connections) {
		this.connections = connections;
	}

	public Avatar getAvatar() {
		return this.avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Remote getRemote() {
		return this.remote;
	}

	public void setRemote(Remote remote) {
		this.remote = remote;
	}

	public static class Avatar extends DisqusImage {

		private static final long serialVersionUID = 1L;

		private Boolean isCustom;

		private DisqusImage small;

		private DisqusImage large;

		public Boolean getIsCustom() {
			return this.isCustom;
		}

		public void setIsCustom(Boolean isCustom) {
			this.isCustom = isCustom;
		}

		public DisqusImage getSmall() {
			return this.small;
		}

		public void setSmall(DisqusImage small) {
			this.small = small;
		}

		public DisqusImage getLarge() {
			return this.large;
		}

		public void setLarge(DisqusImage large) {
			this.large = large;
		}

		@Override
		public String toString() {
			return JsonStringBuilder.toString(this);
		}
	}

	public static class Remote implements Serializable {

		private static final long serialVersionUID = 1L;

		private String domain;

		private String identifier;

		protected Remote() {

		}

		public Remote(String domain, String identifier) {
			this.domain = domain;
			this.identifier = identifier;
		}

		public String getDomain() {
			return this.domain;
		}

		public void setDomain(String domain) {
			this.domain = domain;
		}

		public String getIdentifier() {
			return this.identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String toString() {
			return JsonStringBuilder.toString(this);
		}

		@Override
		public int hashCode() {
			return this.identifier == null ? 0 : this.identifier.hashCode();
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
			Remote other = (Remote) obj;
			if (this.identifier == null) {
				if (other.identifier != null) {
					return false;
				}
			} else if (!this.identifier.equals(other.identifier)) {
				return false;
			}
			return true;
		}

	}

}

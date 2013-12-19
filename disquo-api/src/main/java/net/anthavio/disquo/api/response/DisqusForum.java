package net.anthavio.disquo.api.response;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author martin.vanek
 * 
 */
public class DisqusForum implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id; // shortname

	private String name;

	private Long founder;

	private String language;

	private ForumSettings settings;

	private DisqusImage favicon;

	private List<String> url; // forums/detail returns array

	private DisqusUser author; // related.author

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
		DisqusForum other = (DisqusForum) obj;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFounder() {
		return this.founder;
	}

	public void setFounder(Long founder) {
		this.founder = founder;
	}

	public ForumSettings getSettings() {
		return this.settings;
	}

	public void setSettings(ForumSettings settings) {
		this.settings = settings;
	}

	public List<String> getUrl() {
		return this.url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	public DisqusImage getFavicon() {
		return this.favicon;
	}

	public void setFavicon(DisqusImage favicon) {
		this.favicon = favicon;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public DisqusUser getAuthor() {
		return this.author;
	}

	public void setAuthor(DisqusUser author) {
		this.author = author;
	}

	public static class ForumSettings implements Serializable {

		private static final long serialVersionUID = 1L;

		private Boolean ssoRequired;
		private Boolean allowAnonPost;
		private Boolean allowMedia;
		private Boolean hasReactions;
		private Boolean audienceSyncEnabled;
		private Boolean backplaneEnabled;

		@Override
		public String toString() {
			return JsonStringBuilder.toString(this);
		}

		public boolean isSsoRequired() {
			return this.ssoRequired;
		}

		public void setSsoRequired(Boolean ssoRequired) {
			this.ssoRequired = ssoRequired;
		}

		public Boolean getAllowAnonPost() {
			return this.allowAnonPost;
		}

		public void setAllowAnonPost(Boolean allowAnonPost) {
			this.allowAnonPost = allowAnonPost;
		}

		public Boolean getAllowMedia() {
			return this.allowMedia;
		}

		public void setAllowMedia(Boolean allowMedia) {
			this.allowMedia = allowMedia;
		}

		public Boolean getHasReactions() {
			return this.hasReactions;
		}

		public void setHasReactions(Boolean hasReactions) {
			this.hasReactions = hasReactions;
		}

		public Boolean getAudienceSyncEnabled() {
			return this.audienceSyncEnabled;
		}

		public void setAudienceSyncEnabled(Boolean audienceSyncEnabled) {
			this.audienceSyncEnabled = audienceSyncEnabled;
		}

		public Boolean getBackplaneEnabled() {
			return this.backplaneEnabled;
		}

		public void setBackplaneEnabled(Boolean backplaneEnabled) {
			this.backplaneEnabled = backplaneEnabled;
		}

	}

}

/*
 * 
 * POST /api/3.0/forums/create.json
 * 
 * Response JSON
 * 
 * { "code" : 0, "response" : { "name" : "TestForumName", "founder" :
 * "35902625", "settings" : { "ssoRequired" : false, "allowAnonPost" : false,
 * "allowMedia" : true, "hasReactions" : true, "audienceSyncEnabled" : false },
 * "url" : [ "TestWebsite" ], "favicon" : { "permalink" :
 * "http://disqus.com/api/forums/favicons/testforumshortname.jpg", "cache" :
 * "http://mediacdn.disqus.com/1352501630/images/favicon-default.png" },
 * "language" : "en", "id" : "testforumshortname" } }
 */

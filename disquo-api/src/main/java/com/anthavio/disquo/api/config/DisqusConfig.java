package com.anthavio.disquo.api.config;

import org.apache.commons.lang.StringUtils;

/**
 * Holds disqus config
 * 
 * @author g.awekar
 * 
 */

public class DisqusConfig {
	private final String disqusForumShortname;
	private final boolean disqusEnabled;
	private final String disqusPublicKey;
	private final String disqusSecretKey;
	private final String disqusAccessToken;
	private final boolean devModeEnabled;
	private final int commentsPerPage;

	public DisqusConfig(boolean disqusEnabled, String disqusForumShortname,
			String disqusPublicKey, String disqusSecretKey,
			String disqusAccessToken, boolean devModeEnabled,
			int commentsPerPage) {
		this.disqusEnabled = disqusEnabled;
		if (StringUtils.isBlank(disqusForumShortname)
				|| StringUtils.isBlank(disqusPublicKey)
				|| StringUtils.isBlank(disqusSecretKey)
				|| StringUtils.isBlank(disqusAccessToken)) {
			throw new IllegalStateException(
					"disqus is enabled. Ensure you provide forum-shortname,public-key,private-key and disqus start date");
		}
		this.disqusForumShortname = disqusForumShortname;
		this.disqusPublicKey = disqusPublicKey;
		this.disqusSecretKey = disqusSecretKey;
		this.disqusAccessToken = disqusAccessToken;
		this.devModeEnabled = devModeEnabled;
		this.commentsPerPage = commentsPerPage;
	}

	/**
	 * @return the disqusForumShortname
	 */
	public String getDisqusForumShortname() {
		return this.disqusForumShortname;
	}

	/**
	 * @return the disqusEnabled
	 */
	public boolean isDisqusEnabled() {
		return this.disqusEnabled;
	}

	/**
	 * @return the disqusPublicKey
	 */
	public String getDisqusPublicKey() {
		return this.disqusPublicKey;
	}

	/**
	 * @return the disqusSecretKey
	 */
	public String getDisqusSecretKey() {
		return this.disqusSecretKey;
	}

	public int getDevMode() {
		return this.devModeEnabled ? 1 : 0;
	}

	public String getDisqusAccessToken() {
		return this.disqusAccessToken;
	}

	public int getCommentsPerPage() {
		return this.commentsPerPage;
	}

	@Override
	public String toString() {
		return "DisqusConfig [disqusForumShortname="
				+ this.disqusForumShortname + ", disqusEnabled="
				+ this.disqusEnabled + ", disqusPublicKey="
				+ this.disqusPublicKey + ", commentsPerPage=" + commentsPerPage
				+ "]";
	}

}

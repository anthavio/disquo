package com.anthavio.disquo.api.config;

import java.security.InvalidParameterException;

import org.apache.commons.lang.StringUtils;

public class DisqusCacheConfig {
	private final int mostCommentedCacheTimeInMins;
	private final int commentsCacheTimeInMins;
	private final int commentCountCacheTimeInMins;
	private final int mostCommentedExtendedCacheTimeInMins;
	private final int commentCountExtendedCacheTimeInMins;
	private final String uniqueIdentifier;
	private final boolean individualCommentCountCacheEnabled;

	public DisqusCacheConfig(String uniqueIdentifier,
			int mostCommentedCacheTimeInMins, int commentCacheTimeInMins,
			int commentCountCacheTimeInMins,
			int mostCommentedExtendedCacheTimeInMins,
			int commentCountExtendedCacheTimeInMins,
			boolean individualCommentCountCacheEnabled) {
		this.uniqueIdentifier = uniqueIdentifier;
		this.mostCommentedCacheTimeInMins = mostCommentedCacheTimeInMins;
		this.commentsCacheTimeInMins = commentCacheTimeInMins;
		this.commentCountCacheTimeInMins = commentCountCacheTimeInMins;
		this.mostCommentedExtendedCacheTimeInMins = mostCommentedExtendedCacheTimeInMins;
		this.commentCountExtendedCacheTimeInMins = commentCountExtendedCacheTimeInMins;
		this.individualCommentCountCacheEnabled = individualCommentCountCacheEnabled;
	}

	/**
	 * @return the mostCommentedCacheTimeInMins
	 */
	public int getMostCommentedCacheTimeInMins() {
		return mostCommentedCacheTimeInMins;
	}

	/**
	 * @return the commentCacheTimeInMins
	 */
	public int getCommentsCacheTimeInMins() {
		return commentsCacheTimeInMins;
	}

	/**
	 * @return the commentCountCacheTimeInMins
	 */
	public int getCommentCountCacheTimeInMins() {
		return commentCountCacheTimeInMins;
	}

	/**
	 * @return the mostCommentedExtendedCacheTimeInMins
	 */
	public int getMostCommentedExtendedCacheTimeInMins() {
		return mostCommentedExtendedCacheTimeInMins;
	}

	/**
	 * @return the commentCountExtendedCacheTimeInMins
	 */
	public int getCommentCountExtendedCacheTimeInMins() {
		return commentCountExtendedCacheTimeInMins;
	}

	/**
	 * @return the uniqueIdentifier
	 */
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public static Builder Builder(String uniqueIdentifier) {
		if (StringUtils.isBlank(uniqueIdentifier)) {
			throw new InvalidParameterException(
					"uniqueIdentifier cannot be empty or null ");
		}
		return new Builder(uniqueIdentifier);
	}

	/**
	 * @return the individualCommentCountCacheEnabled
	 */
	public boolean isIndividualCommentCountCacheEnabled() {
		return individualCommentCountCacheEnabled;
	}

	@Override
	public String toString() {
		return "DisqusCacheConfig [mostCommentedCacheTimeInMins="
				+ mostCommentedCacheTimeInMins + ", commentsCacheTimeInMins="
				+ commentsCacheTimeInMins + ", commentCountCacheTimeInMins="
				+ commentCountCacheTimeInMins
				+ ", mostCommentedExtendedCacheTimeInMins="
				+ mostCommentedExtendedCacheTimeInMins
				+ ", commentCountExtendedCacheTimeInMins="
				+ commentCountExtendedCacheTimeInMins + ", uniqueIdentifier="
				+ uniqueIdentifier + ", individualCommentCountCacheEnabled="
				+ individualCommentCountCacheEnabled + "]";
	}

	public static class Builder {
		private int mostCommentedCacheTimeInMins;
		private int commentCacheTimeInMins;
		private int commentCountCacheTimeInMins;
		private int mostCommentedExtendedCacheTimeInMins;
		private int commentCountExtendedCacheTimeInMins;
		private final String uniqueIdentifier;
		private boolean individualCommentCountCacheEnabled = false;

		Builder(String uniqueIdentifier) {
			this.uniqueIdentifier = uniqueIdentifier;
		}

		public Builder mostCommentedCacheTimeInMins(
				int mostCommentedCacheTimeInMins) {
			this.mostCommentedCacheTimeInMins = mostCommentedCacheTimeInMins;
			return this;
		}

		public Builder commentCacheTimeInMins(int commentCacheTimeInMins) {
			this.commentCacheTimeInMins = commentCacheTimeInMins;
			return this;
		}

		public Builder commentCountCacheTimeInMins(
				int commentCountCacheTimeInMins) {
			this.commentCountCacheTimeInMins = commentCountCacheTimeInMins;
			return this;
		}

		public Builder mostCommentedExtendedCacheTimeInMins(
				int mostCommentedExtendedCacheTimeInMins) {
			this.mostCommentedExtendedCacheTimeInMins = mostCommentedExtendedCacheTimeInMins;
			return this;
		}

		public Builder commentCountExtendedCacheTimeInMins(
				int commentCountExtendedCacheTimeInMins) {
			this.commentCountExtendedCacheTimeInMins = commentCountExtendedCacheTimeInMins;
			return this;
		}

		public Builder individualCommentCountCacheEnabled() {
			this.individualCommentCountCacheEnabled = true;
			return this;
		}

		public DisqusCacheConfig build() {
			assertTime(commentCountExtendedCacheTimeInMins,
					"EXTENDED comment count cache time should be greater than ZERO");
			assertTime(mostCommentedExtendedCacheTimeInMins,
					"EXTENDED Most commented cache time should be greater than ZERO");
			assertTime(commentCountCacheTimeInMins,
					"Comment count cache time should be greater than ZERO");
			assertTime(commentCacheTimeInMins,
					"Post cache time should be greater than ZERO");
			assertTime(mostCommentedCacheTimeInMins,
					"Most Commented cache time should be greater than ZERO");

			return new DisqusCacheConfig(this.uniqueIdentifier,
					this.mostCommentedCacheTimeInMins,
					this.commentCacheTimeInMins,
					this.commentCountCacheTimeInMins,
					this.mostCommentedExtendedCacheTimeInMins,
					this.commentCountExtendedCacheTimeInMins,
					this.individualCommentCountCacheEnabled);
		}

		private void assertTime(int time, String message) {
			if (time <= 0) {
				throw new InvalidParameterException("Invalid value: " + message);
			}
		}
	}
}

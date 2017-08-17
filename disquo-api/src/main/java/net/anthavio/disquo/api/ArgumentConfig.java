package net.anthavio.disquo.api;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author martin.vanek
 *
 */
public class ArgumentConfig {

	public enum PostState {
		unapproved, approved, spam, deleted, flagged, highlighted;

		public static PostState[] ALL = new PostState[] { unapproved, approved, spam, deleted, flagged, highlighted };

		public static List<PostState> CALL = Arrays.asList(ALL);
	}

	public enum ThreadState {
		open, closed, killed;

		public static ThreadState[] ALL = new ThreadState[] { open, closed, killed };

		public static List<ThreadState> CALL = Arrays.asList(ALL);
	}

	public enum UserInclude {
		user, replies, following; //Users.listActivity
	}

	public enum Related {
		forum, thread, author, category
	}

	public enum Order {
		asc, desc;
	}

	public enum Attach {
		followsForum, forumCanDisableAds, forumForumCategory, counters, forumDaysAlive, forumFeatures, forumIntegration, forumNewPolicy, forumPermissions
	}

	public enum Vote {

		PLUS(1), ZERO(0), MINUS(-1);

		public final int value;

		Vote(int value) {
			this.value = value;
		}

		public static Vote valueOf(int value) {
			if (value > 0) {
				return Vote.PLUS;
			} else if (value < 0) {
				return Vote.MINUS;
			} else {
				return Vote.ZERO;
			}
		}
	}

	public enum FilterType {

		domain, word, ip, user, thread_slug, email;

		public static FilterType[] ALL = new FilterType[] { domain, word, ip, user, thread_slug, email };
	}

}

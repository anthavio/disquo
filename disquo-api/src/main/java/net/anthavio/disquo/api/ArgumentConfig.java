package net.anthavio.disquo.api;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author martin.vanek
 *
 */
public class ArgumentConfig {

	/**
	 * 
	 * Argh! This is nice excample where ploymorphism and generics are horrible syntax overkill
	 *
	 */
	public abstract static class ArgType<P, S> {

		public static final ArgType<String, Serializable> STRING = new ArgType<String, Serializable>(String.class,
				Serializable.class) {

			@Override
			public String convert(Serializable secondary) {
				return String.valueOf(secondary);
			}
		};

		public static final ArgType<PostState, String> POST_STATE = new ArgType<PostState, String>(PostState.class,
				String.class) {

			@Override
			public PostState convert(String secondary) {
				return PostState.valueOf(secondary);
			}

			@Override
			public void check(PostState primary) {

			}
		};
		public static final ArgType<ThreadState, String> THREAD_STATE = new ArgType<ThreadState, String>(ThreadState.class,
				String.class) {

			@Override
			public ThreadState convert(String secondary) {
				return ThreadState.valueOf(secondary);
			}
		};
		public static final ArgType<Related, String> RELATED = new ArgType<Related, String>(Related.class, String.class) {

			@Override
			public Related convert(String secondary) {
				return Related.valueOf(secondary);
			}
		};
		public static final ArgType<Number, String> INTEGER = new ArgType<Number, String>(Number.class, String.class) {

			@Override
			public Integer convert(String secondary) {
				return Integer.parseInt(secondary);
			}
		};
		public static final ArgType<Number, String> LONG = new ArgType<Number, String>(Number.class, String.class) {

			@Override
			public Long convert(String secondary) {
				return Long.parseLong(secondary);
			}
		};
		public static final ArgType<Number, String> LIMIT = new ArgType<Number, String>(Number.class, String.class) {

			@Override
			public Integer convert(String secondary) {
				return Integer.parseInt(secondary);
			}
		};

		public static final ArgType<QUser, Number> ID_USER = new ArgType<QUser, Number>(QUser.class, Number.class) {

			@Override
			public QUser convert(Number secondary) {
				return QUser.build(secondary);
			}
		};
		public static final ArgType<QThread, Number> ID_THREAD = new ArgType<QThread, Number>(QThread.class, Number.class) {

			@Override
			public QThread convert(Number secondary) {
				return QThread.build(secondary);
			}
		};

		public static final ArgType<Vote, String> VOTE = new ArgType<Vote, String>(Vote.class, String.class) {

			@Override
			public Vote convert(String secondary) {
				return Vote.valueOf(secondary);
			}
		};
		public static final ArgType<Boolean, String> BOOLEAN = new ArgType<Boolean, String>(Boolean.class, String.class) {

			@Override
			public Boolean convert(String secondary) {
				return Boolean.valueOf(secondary);
			}
		};
		public static final ArgType<Order, String> ORDER = new ArgType<Order, String>(Order.class, String.class) {

			@Override
			public Order convert(String secondary) {
				return Order.valueOf(secondary);
			}
		};
		public static final ArgType<FilterType, String> FILTER_TYPE = new ArgType<FilterType, String>(FilterType.class,
				String.class) {

			@Override
			public FilterType convert(String secondary) {
				return FilterType.valueOf(secondary);
			}
		};
		public static final ArgType<Date, String> TIMESTAMP = new ArgType<Date, String>(Date.class, String.class) {

			@Override
			public Date convert(String secondary) {
				try {
					return new SimpleDateFormat(Disqus.DATE_FORMAT).parse(secondary);
				} catch (ParseException px) {
					throw new IllegalArgumentException("Parameter value " + secondary + " does not conform " + Disqus.DATE_FORMAT
							+ " format");
				}
			}

		};
		public static final ArgType<String, String> EMAIL = new ArgType<String, String>(String.class, String.class) {

			@Override
			public String convert(String secondary) {
				return secondary;
			}
		};

		private final Class<P> primary;

		private final Class<S> secondary;

		private ArgType(Class<P> primary, Class<S> secondary) {
			this.primary = primary;
			this.secondary = secondary;
		}

		public Class<P> getPrimary() {
			return this.primary;
		}

		public Class<S> getSecondary() {
			return this.secondary;
		}

		protected void check(P primary) {
			//to be overiden
		}

		protected abstract P convert(S secondary);

		public P validate(Object value) {
			Class<? extends Object> vclass = value.getClass();
			if (this.primary.isAssignableFrom(vclass)) {
				P p = (P) value;
				check(p);
				return p;
			} else if (this.secondary.isAssignableFrom(vclass)) {
				P p = convert((S) value);
				check(p);
				return p;
			} else {
				throw new IllegalArgumentException("Parameter type is neither " + this.primary.getSimpleName() + " nor "
						+ this.secondary.getSimpleName() + " but " + vclass.getSimpleName());
			}
		}
	}

	public static final ArgumentConfig API_KEY = new ArgumentConfig("api_key", ArgType.STRING, false, false);
	public static final ArgumentConfig SECRET_KEY = new ArgumentConfig("secret_key", ArgType.STRING, false, false);
	public static final ArgumentConfig REMOTE_AUTH = new ArgumentConfig("remote_auth", ArgType.STRING, false, false);
	public static final ArgumentConfig ACCESS_TOKEN = new ArgumentConfig("access_token", ArgType.STRING, false, false);

	public static enum PostState {
		unapproved, approved, spam, deleted, flagged, highlighted;

		public static PostState[] ALL = new PostState[] { unapproved, approved, spam, deleted, flagged, highlighted };

		public static List<PostState> CALL = Arrays.asList(ALL);
	}

	public static enum ThreadState {
		open, closed, killed;

		public static ThreadState[] ALL = new ThreadState[] { open, closed, killed };

		public static List<ThreadState> CALL = Arrays.asList(ALL);
	}

	public static enum UserInclude {
		user, replies, following; //Users.listActivity
	}

	public static enum Related {
		forum, thread, author, category;
	}

	public static enum Order {
		asc, desc;
	}

	public static enum Vote {

		PLUS(1), ZERO(0), MINUS(-1);

		public final int value;

		private Vote(int value) {
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

	public static enum FilterType {

		domain, word, ip, user, thread_slug, email;

		public static FilterType[] ALL = new FilterType[] { domain, word, ip, user, thread_slug, email };
	}

	private final String name;

	private final ArgType<?, ?> type;

	private final boolean isRequired;

	private final boolean isMultivalue;

	public ArgumentConfig(String name, ArgType<?, ?> type, boolean required, boolean multi) {
		this.name = name;
		this.type = type;
		this.isRequired = required;
		this.isMultivalue = multi;
	}

	public String getName() {
		return this.name;
	}

	public ArgType<?, ?> getType() {
		return this.type;
	}

	public boolean getIsRequired() {
		return this.isRequired;
	}

	public boolean getIsMultivalue() {
		return this.isMultivalue;
	}

}

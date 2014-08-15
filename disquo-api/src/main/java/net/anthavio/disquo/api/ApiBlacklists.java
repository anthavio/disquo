package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/blacklists/
 * 
 * @author martin.vanek
 *
 */
@HttlApi("/blacklists/")
public interface ApiBlacklists {

	@HttlCall("GET list.json")
	public DisqusResponse<List<DisqusFilter>> list(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar(name = "forum", required = true) String forum);

	@HttlCall("GET list.json")
	public ListBlacklistBuilder listbuild(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar(name = "forum", required = true) String forum);

	public static interface ListBlacklistBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusFilter>>> {

		public ListBlacklistBuilder since(Date since);

		public ListBlacklistBuilder related(Related... related);

		public ListBlacklistBuilder cursor(String cursor);

		public ListBlacklistBuilder limit(int limit);

		public ListBlacklistBuilder query(String query);

		public ListBlacklistBuilder type(FilterType... type);

		public ListBlacklistBuilder order(Order order);

	}

	@HttlCall("POST add.json")
	public DisqusResponse<Void[]> add(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "email", required = true) String email);

	@HttlCall("POST add.json")
	public BlacklistAddBuilder add(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar(name = "forum", required = true) String forum);

	public static interface BlacklistAddBuilder extends HttlCallBuilder<DisqusResponse<Void[]>> {

		public BlacklistAddBuilder domain(String... domain);

		public BlacklistAddBuilder word(String... word);

		public BlacklistAddBuilder retroactive(boolean retroactive);

		public BlacklistAddBuilder notes(String notes);

		public BlacklistAddBuilder ip(String... ip);

		public BlacklistAddBuilder email(String... email);

		public BlacklistAddBuilder user(@HttlVar("user:username") String... username);
	}

	@HttlCall("POST remove.json")
	public DisqusResponse<Void[]> remove(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "email", required = true) String email);

	@HttlCall("POST remove.json")
	public BlacklistRemoveBuilder remove(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar(name = "forum", required = true) String forum);

	public static interface BlacklistRemoveBuilder extends HttlCallBuilder<DisqusResponse<Void[]>> {

		public BlacklistRemoveBuilder domain(String... domain);

		public BlacklistRemoveBuilder word(String... word);

		public BlacklistRemoveBuilder ip(String... ip);

		public BlacklistRemoveBuilder email(String... email);

		public BlacklistRemoveBuilder user(@HttlVar("user:username") String... username);
	}
}

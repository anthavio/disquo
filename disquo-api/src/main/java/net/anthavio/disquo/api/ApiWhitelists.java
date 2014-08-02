package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * https://disqus.com/api/docs/whitelists/
 * 
 * @author martin.vanek
 *
 */
@RestApi("/whitelists/")
public interface ApiWhitelists {

	@RestCall("GET list.json")
	public DisqusResponse<List<DisqusFilter>> list(@RestVar(name = "access_token", required = true) String token,
			@RestVar(name = "forum", required = true) String forum);

	@RestCall("GET list.json")
	public ListWhitelistBuilder listbuild(@RestVar(name = "access_token", required = true) String token,
			@RestVar(name = "forum", required = true) String forum);

	public static interface ListWhitelistBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusFilter>>> {

		public ListWhitelistBuilder since(Date since);

		public ListWhitelistBuilder related(Related... related);

		public ListWhitelistBuilder cursor(String cursor);

		public ListWhitelistBuilder limit(int limit);

		public ListWhitelistBuilder query(String query);

		public ListWhitelistBuilder type(FilterType... type);

		public ListWhitelistBuilder order(Order order);

	}

	@RestCall("POST add.json")
	public DisqusResponse<Void[]> add(@RestVar(name = "access_token", required = true) String token,
			@RestVar(name = "forum", required = true) String forum, @RestVar(name = "email", required = true) String email);

	@RestCall("POST add.json")
	public WhitelistAddBuilder add(@RestVar(name = "access_token", required = true) String token,
			@RestVar(name = "forum", required = true) String forum);

	public static interface WhitelistAddBuilder extends HttlCallBuilder<DisqusResponse<Void[]>> {

		public WhitelistAddBuilder notes(String notes);

		public WhitelistAddBuilder email(String... email);

		public WhitelistAddBuilder user(@RestVar("user:username") String... username);
	}

	@RestCall("POST remove.json")
	public DisqusResponse<Void[]> remove(@RestVar(name = "access_token", required = true) String token,
			@RestVar(name = "forum", required = true) String forum, @RestVar(name = "email", required = true) String email);

	@RestCall("POST remove.json")
	public WhitelistRemoveBuilder remove(@RestVar(name = "access_token", required = true) String token,
			@RestVar(name = "forum", required = true) String forum);

	public static interface WhitelistRemoveBuilder extends HttlCallBuilder<DisqusResponse<Void[]>> {

		public WhitelistRemoveBuilder email(String... email);

		public WhitelistRemoveBuilder user(@RestVar("user:username") String... username);
	}
}

package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApi.IdentitySetter;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlHeaders;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/whitelists/
 * 
 * @author martin.vanek
 *
 */
@HttlApi(uri = "/api/3.0/whitelists/", setters = IdentitySetter.class)
public interface ApiWhitelists {

	@HttlCall("GET list.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<DisqusFilter>> list(@HttlVar(name = "forum", required = true) String forum);

	@HttlCall("GET list.json")
	public DisqusResponse<List<DisqusFilter>> list(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum);

	@HttlCall("GET list.json")
	@HttlHeaders("X!-AUTH: true")
	public ListWhitelistBuilder listBuilder(@HttlVar(name = "forum", required = true) String forum);

	@HttlCall("GET list.json")
	public ListWhitelistBuilder listBuilder(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum);

	public static interface ListWhitelistBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusFilter>>> {

		public ListWhitelistBuilder since(Date since);

		public ListWhitelistBuilder related(Related... related);

		public ListWhitelistBuilder cursor(String cursor);

		public ListWhitelistBuilder limit(int limit);

		public ListWhitelistBuilder query(String query);

		public ListWhitelistBuilder type(FilterType... type);

		public ListWhitelistBuilder order(Order order);

	}

	@HttlCall("POST add.json")
	public DisqusResponse<Void[]> add(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "email", required = true) String email);

	@HttlCall("POST add.json")
	public WhitelistAddBuilder addBuilder(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum);

	@HttlCall("POST add.json")
	@HttlHeaders("X!-AUTH: true")
	public WhitelistAddBuilder addBuilder(@HttlVar(name = "forum", required = true) String forum);

	public static interface WhitelistAddBuilder extends HttlCallBuilder<DisqusResponse<Void[]>> {

		public WhitelistAddBuilder notes(String notes);

		public WhitelistAddBuilder email(String... email);

		public WhitelistAddBuilder user(@HttlVar("user:username") String... username);
	}

	@HttlCall("POST remove.json")
	public DisqusResponse<Void[]> remove(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "email", required = true) String email);

	@HttlCall("POST remove.json")
	public WhitelistRemoveBuilder removeBuilder(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum);

	@HttlCall("POST remove.json")
	@HttlHeaders("X!-AUTH: true")
	public WhitelistRemoveBuilder removeBuilder(@HttlVar(name = "forum", required = true) String forum);

	public static interface WhitelistRemoveBuilder extends HttlCallBuilder<DisqusResponse<Void[]>> {

		public WhitelistRemoveBuilder email(String... email);

		public WhitelistRemoveBuilder user(@HttlVar("user:username") String... username);
	}
}

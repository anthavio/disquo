package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.response.DisqusCategory;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * 
 * https://disqus.com/api/docs/categories/
 * 
 * @author martin.vanek
 *
 */
@RestApi("/categories/")
public interface ApiCategories {

	@RestCall("POST create.json")
	public DisqusResponse<DisqusCategory> create(@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "title", required = true) String title, @RestVar("default") Boolean isDefault);

	@RestCall("GET details.json")
	public DisqusResponse<DisqusCategory> details(@RestVar("category") long category);

	@RestCall("GET list.json")
	public DisqusResponse<List<DisqusCategory>> list(@RestVar(name = "forum", required = true) String forum);

	@RestCall("GET list.json")
	public DisqusResponse<List<DisqusCategory>> list(@RestVar(name = "forum", required = true) String forum,
			@RestVar("cursor") String cursor, @RestVar("limit") Integer limit, @RestVar("order") Order order);

	@RestCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@RestVar("category") long category);

	public static interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		public ListPostsBuilder since(Date since);

		public ListPostsBuilder related(Related... related);

		public ListPostsBuilder cursor(String cursor);

		public ListPostsBuilder limit(int limit);

		public ListPostsBuilder query(String query);

		public ListPostsBuilder include(PostState... include);

		public ListPostsBuilder order(Order order);

	}

	@RestCall("GET listThreads.json")
	public ListThreadsBuilder listThreads(@RestVar("category") long category);

	public static interface ListThreadsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusThread>>> {

		public ListThreadsBuilder since(Date since);

		public ListThreadsBuilder related(Related... related);

		public ListThreadsBuilder cursor(String cursor);

		public ListThreadsBuilder limit(int limit);

		public ListThreadsBuilder order(Order order);

	}
}

package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.response.DisqusCategory;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusId;
import net.anthavio.disquo.api.response.DisqusModerator;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * https://disqus.com/api/docs/forums/
 * 
 * @author martin.vanek
 *
 */
@RestApi("/forums/")
public interface ApiForums {

	/*
	 * https://disqus.com/api/docs/forums/addModerator/
	 */

	@RestCall("POST addModerator.json")
	public DisqusResponse<DisqusId> addModerator(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "user:username", required = true) String username);

	@RestCall("POST addModerator.json")
	public DisqusResponse<DisqusId> addModerator(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String forum, @RestVar(name = "user", required = true) long user);

	/*
	 * https://disqus.com/api/docs/forums/removeModerator/
	 */
	//use listModerators to get moderator id
	@RestCall("POST removeModerator.json")
	public DisqusResponse<DisqusId> removeModerator(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "moderator", required = true) long moderator);

	/*
	 * https://disqus.com/api/docs/forums/create/
	 */

	@RestCall("POST create.json")
	public DisqusResponse<DisqusForum> create(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "name", required = true) String name,
			@RestVar(name = "short_name", required = true) String short_name,
			@RestVar(name = "website", required = true) String website);

	/*
	 * https://disqus.com/api/docs/forums/details/
	 */

	@RestCall("GET details.json")
	public DisqusResponse<DisqusForum> details(@RestVar(name = "forum", required = true) String short_name,
			@RestVar("related") Related... related);

	@RestCall("GET listCategories.json")
	public DisqusResponse<List<DisqusCategory>> listCategories(
			@RestVar(name = "forum", required = true) String short_name, @RestVar DisqusPageable page);

	@RestCall("GET listModerators.json")
	public DisqusResponse<List<DisqusModerator>> listModerators(
			@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String short_name);

	@RestCall("GET listMostActiveUsers.json")
	public DisqusResponse<List<DisqusUser>> listMostActiveUsers(
			@RestVar(name = "forum", required = true) String short_name, @RestVar DisqusPageable page);

	@RestCall("GET listMostLikedUsers.json")
	public DisqusResponse<List<DisqusUser>> listMostLikedUsers(
			@RestVar(name = "forum", required = true) String short_name, @RestVar DisqusPageable page);

	/*
	 * https://disqus.com/api/docs/forums/listPosts/
	 */

	@RestCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@RestVar(name = "forum", required = true) String short_name,
			@RestVar DisqusPageable page);

	@RestCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@RestVar(name = "forum", required = true) String short_name);

	public static interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		public ListPostsBuilder since(Date since);

		public ListPostsBuilder related(Related... related);

		public ListPostsBuilder cursor(String cursor);

		public ListPostsBuilder limit(int limit);

		public ListPostsBuilder query(String query);

		public ListPostsBuilder include(PostState... include);

		public ListPostsBuilder order(Order order);

	}

	/*
	 * https://disqus.com/api/docs/forums/listThreads/
	 */

	@RestCall("GET listThreads.json")
	public DisqusResponse<List<DisqusThread>> listThreads(@RestVar(name = "forum", required = true) String short_name,
			@RestVar DisqusPageable page);

	@RestCall("GET listThreads.json")
	public ListThreadsBuilder listThreads(@RestVar(name = "forum", required = true) String short_name);

	public static interface ListThreadsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusThread>>> {

		public ListThreadsBuilder since(Date since);

		public ListThreadsBuilder related(Related... related);

		public ListThreadsBuilder cursor(String cursor);

		public ListThreadsBuilder limit(int limit);

		public ListThreadsBuilder query(String query);

		public ListThreadsBuilder include(ThreadState... include);

		public ListThreadsBuilder order(Order order);
	}

	/*
	 * https://disqus.com/api/docs/forums/listUsers/
	 */

	@RestCall("GET listUsers.json")
	public DisqusResponse<List<DisqusUser>> listUsers(@RestVar(name = "forum", required = true) String short_name,
			@RestVar DisqusPageable page);

}

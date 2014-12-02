package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ApiThreads.ListThreadsBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApi.IdentitySetter;
import net.anthavio.disquo.api.response.DisqusCategory;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusId;
import net.anthavio.disquo.api.response.DisqusModerator;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlHeaders;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/forums/
 * 
 * @author martin.vanek
 *
 */
@HttlApi(uri = "/api/3.0/forums/", setters = IdentitySetter.class)
public interface ApiForums {

	/*
	 * https://disqus.com/api/docs/forums/addModerator/
	 */

	@HttlCall("POST addModerator.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId> addModerator(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "user:username", required = true) String username);

	@HttlCall("POST addModerator.json")
	public DisqusResponse<DisqusId> addModerator(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "user:username", required = true) String username);

	@HttlCall("POST addModerator.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId> addModerator(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "user", required = true) long user);

	@HttlCall("POST addModerator.json")
	public DisqusResponse<DisqusId> addModerator(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "user", required = true) long user);

	/*
	 * https://disqus.com/api/docs/forums/removeModerator/
	 */
	//use listModerators to get moderator id
	@HttlCall("POST removeModerator.json")
	public DisqusResponse<DisqusId> removeModerator(@HttlVar(required = true) Identity token,
			@HttlVar(name = "moderator", required = true) long moderator);

	@HttlCall("POST removeModerator.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId> removeModerator(@HttlVar(name = "moderator", required = true) long moderator);

	/*
	 * https://disqus.com/api/docs/forums/create/
	 */

	@HttlCall("POST create.json")
	public DisqusResponse<DisqusForum> create(@HttlVar(required = true) Identity token,
			@HttlVar(name = "name", required = true) String name,
			@HttlVar(name = "short_name", required = true) String short_name,
			@HttlVar(name = "website", required = true) String website);

	@HttlCall("POST create.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusForum> create(@HttlVar(name = "name", required = true) String name,
			@HttlVar(name = "short_name", required = true) String short_name,
			@HttlVar(name = "website", required = true) String website);

	/*
	 * https://disqus.com/api/docs/forums/details/
	 */

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusForum> details(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar("related") Related... related);

	@HttlCall("GET listCategories.json")
	public DisqusResponse<List<DisqusCategory>> listCategories(
			@HttlVar(name = "forum", required = true) String short_name, @HttlVar DisqusPage page);

	//list...

	@HttlCall("GET listModerators.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<DisqusModerator>> listModerators(
			@HttlVar(name = "forum", required = true) String short_name);

	@HttlCall("GET listModerators.json")
	public DisqusResponse<List<DisqusModerator>> listModerators(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String short_name);

	@HttlCall("GET listMostActiveUsers.json")
	public DisqusResponse<List<DisqusUser>> listMostActiveUsers(
			@HttlVar(name = "forum", required = true) String short_name, @HttlVar DisqusPage page);

	@HttlCall("GET listMostLikedUsers.json")
	public DisqusResponse<List<DisqusUser>> listMostLikedUsers(
			@HttlVar(name = "forum", required = true) String short_name, @HttlVar DisqusPage page);

	/*
	 * https://disqus.com/api/docs/forums/listPosts/
	 */

	@HttlCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar DisqusPage page);

	@HttlCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@HttlVar(name = "forum", required = true) String short_name);

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

	@HttlCall("GET listThreads.json")
	public DisqusResponse<List<DisqusThread>> listThreads(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar DisqusPage page);

	@HttlCall("GET listThreads.json")
	public ListThreadsBuilder listThreads(@HttlVar(name = "forum", required = true) String short_name);

	public static interface ListThreadsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusThread>>> {

		public ListThreadsBuilder thread(@HttlVar("thread") long... thread);

		public ListThreadsBuilder threadIdent(@HttlVar("thread:ident") String... threadIdent);
		
		public ListThreadsBuilder threadLink(@HttlVar("thread:link") String... threadLink);
		
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

	@HttlCall("GET listUsers.json")
	public DisqusResponse<List<DisqusUser>> listUsers(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar DisqusPage page);

}

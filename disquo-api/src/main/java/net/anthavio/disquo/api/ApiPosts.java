package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.auth.AnonymousData;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.auth.SsoAuthenticator;
import net.anthavio.disquo.api.response.DisqusId;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusVotePost;
import net.anthavio.httl.HttlRequestBuilders.HttlRequestBuilder;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;
import net.anthavio.httl.api.VarSetter;

/**
 * https://disqus.com/api/docs/posts/
 * 
 * @author martin.vanek
 *
 */
@HttlApi("/posts/")
public interface ApiPosts {

	/**
	 * https://disqus.com/api/docs/posts/approve/
	 */
	@HttlCall("POST approve.json")
	public DisqusResponse<List<DisqusId>> approve(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar("post") long... post);

	/**
	 * https://disqus.com/api/docs/posts/create/
	 */

	// OAuth access_token
	@HttlCall("POST create.json")
	public DisqusResponse<DisqusPost> create(
			//
			@HttlVar(name = "access_token", required = true) String access_token,//
			@HttlVar("thread") long thread, @HttlVar(name = "message", required = true) String message,
			@HttlVar("parent") Long parent);

	/**
	 * You must allow anonymou commenting in Disqus admin
	 * https://www.disqus.com/admin/settings/?p=general
	 * Guest Commenting: Allow guests to comment
	 * 
	 * Anonymous comments are allowed under two conditions:
	 * 
	 * 1. You're using legacy auth, and your secret key
	 * 2. You're using your public key, you've come from a verified referrer, you're unauthenticated,
	 * and the forum you're attempting to create the post on is listed in the applications trusted forums.
	 * 
	 * 
	 */
	@HttlCall("POST create.json")
	//@RestHeaders("Referer: {http-referer}")
	public DisqusResponse<DisqusPost> create(
			@HttlVar(required = true) AnonymousData anonymous,// 
			//@RestVar(name = "http-referer", required = true) String referer,//
			@HttlVar("thread") long thread, @HttlVar(name = "message", required = true) String message,
			@HttlVar("parent") Long parent);

	// SsoAuthData
	@HttlCall("POST create.json")
	public DisqusResponse<DisqusPost> create(
			//
			@HttlVar(setter = SsoAuthDataSetter.class) SsoAuthData ssoAuth,//
			@HttlVar(name = "api_secret", required = true) String api_secret,//
			@HttlVar("thread") long thread, @HttlVar(name = "message", required = true) String message,
			@HttlVar("parent") Long parent);

	@HttlCall("POST create.json")
	public CreatePostBuilder create(@HttlVar("thread") long thread, @HttlVar(name = "message") String message);

	public static interface CreatePostBuilder extends HttlCallBuilder<DisqusResponse<DisqusPost>> {

		public CreatePostBuilder parent(long parent);

		// moderator

		public CreatePostBuilder state(PostState state);

		public CreatePostBuilder date(Date date);

		public CreatePostBuilder ip_address(String ip_address);

		// oauth

		public CreatePostBuilder access_token(long access_token);

		// anonymous

		public CreatePostBuilder author_name(long author_name);

		public CreatePostBuilder author_email(long author_email);

		public CreatePostBuilder author_url(long author_url);

	}

	/**
	 * https://disqus.com/api/docs/posts/details/
	 */
	@HttlCall("GET details.json")
	public DisqusResponse<DisqusPost> details(@HttlVar("post") long post, @HttlVar("related") Related... related);

	/**
	 * https://disqus.com/api/docs/posts/getContext/
	 */
	@HttlCall("GET getContext.json")
	public DisqusResponse<List<DisqusPost>> getContext(@HttlVar("post") long post, @HttlVar("depth") Integer depth,
			@HttlVar("related") Related... related);

	/**
	 * https://disqus.com/api/docs/posts/list/
	 */

	@HttlCall("GET list.json")
	public DisqusResponse<List<DisqusPost>> list(@HttlVar DisqusPageable page);

	@HttlCall("GET list.json")
	public DisqusResponse<List<DisqusPost>> list(@HttlVar("thread") long thread, @HttlVar DisqusPageable page);

	@HttlCall("GET list.json")
	public ListPostsBuilder list();

	public static interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		public ListPostsBuilder category(long... category);

		public ListPostsBuilder thread(long... thread);

		public ListPostsBuilder forum(String... forum);

		public ListPostsBuilder since(Date since);

		public ListPostsBuilder related(Related... related);

		public ListPostsBuilder include(PostState... include);

		public ListPostsBuilder cursor(String cursor);

		public ListPostsBuilder query(String query);

		public ListPostsBuilder limit(int limit);

		public ListPostsBuilder order(Order order);
	}

	/**
	 * https://disqus.com/api/docs/posts/remove/
	 */
	@HttlCall("POST remove.json")
	public DisqusResponse<List<DisqusId>> remove(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "post", required = true) long... post);

	/**
	 * https://disqus.com/api/docs/posts/restore/
	 */
	@HttlCall("POST restore.json")
	public DisqusResponse<List<DisqusId>> restore(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "post", required = true) long... post);

	/**
	 * https://disqus.com/api/docs/posts/report/
	 */
	@HttlCall("POST report.json")
	public DisqusResponse<DisqusPost> report(@HttlVar("post") long post);

	/**
	 * https://disqus.com/api/docs/posts/spam/
	 */
	@HttlCall("POST spam.json")
	public DisqusResponse<List<DisqusId>> spam(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "post", required = true) long... post);

	/**
	 * https://disqus.com/api/docs/posts/vote/
	 */
	@HttlCall("POST vote.json")
	public DisqusResponse<DisqusVotePost> vote(@HttlVar("post") long post, @HttlVar("vote") int vote);

	/**
	 * https://disqus.com/api/docs/posts/update/
	 */
	@HttlCall("POST update.json")
	public DisqusResponse<DisqusPost> update(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar("post") long post, @HttlVar(name = "message", required = true) String message);

	/**
	 * Setter for SsoAuthData -> remote_auth
	 * 
	 * @author martin.vanek
	 *
	 */
	public static class SsoAuthDataSetter implements VarSetter<SsoAuthData> {

		@Override
		public void set(SsoAuthData value, String name, HttlRequestBuilder<?> builder) {
			//quite hackish way to pass api_secret here
			String api_secret = builder.getParameters().getFirst("api_secret");
			String remote_auth = SsoAuthenticator.remote_auth_s3(value, api_secret);
			builder.param("remote_auth", remote_auth);
		}
	}
}

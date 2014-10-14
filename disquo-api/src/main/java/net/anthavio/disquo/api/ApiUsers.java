package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApi.IdentitySetter;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlHeaders;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/users/
 * 
 * @author vanek
 *
 */
@HttlApi(uri = "/api/3.0/users/", setters = IdentitySetter.class)
public interface ApiUsers {

	/**
	 * For taken or illegal usernames it returns http 400 and response
	 * "{"code":2,"response":"Invalid argument, 'username': Username already exists."}"
	 */
	@HttlCall("POST checkUsername.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<String> checkUsername(@HttlVar(name = "username", required = true) String username)
			throws DisqusServerException;

	@HttlCall("POST checkUsername.json")
	public DisqusResponse<String> checkUsername(@HttlVar(required = true) Identity token,
			@HttlVar(name = "username", required = true) String username) throws DisqusServerException;

	/*
	 * https://disqus.com/api/docs/users/details/
	 */

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@HttlVar("user") long user);

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@HttlVar(name = "user:username", required = true) String username);

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@HttlVar(required = true) Identity token);

	@HttlCall("GET details.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusUser> details();

	/*
	 * https://disqus.com/api/docs/users/follow/
	 */

	@HttlCall("POST follow.json")
	public DisqusResponse<Void[]> follow(@HttlVar(required = true) Identity token, @HttlVar("target") long user);

	@HttlCall("POST follow.json")
	public DisqusResponse<Void[]> follow(@HttlVar(required = true) Identity token,
			@HttlVar(name = "target:username", required = true) String username);

	/*
	 * https://disqus.com/api/docs/users/unfollow/
	 */

	@HttlCall("POST unfollow.json")
	public DisqusResponse<Void[]> unfollow(@HttlVar(required = true) Identity token, @HttlVar("target") long user);

	@HttlCall("POST unfollow.json")
	public DisqusResponse<Void[]> unfollow(@HttlVar(required = true) Identity token,
			@HttlVar(name = "target:username", required = true) String username);

	/*
	 * Returns a list of forums a user has been active on.
	 * 
	 * https://disqus.com/api/docs/users/listActiveForums/
	 */

	@HttlCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForums(@HttlVar("user") long user, @HttlVar DisqusPage page);

	@HttlCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForums(
			@HttlVar(name = "user:username", required = true) String username, @HttlVar DisqusPage page);

	@HttlCall("GET listActiveForums.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<DisqusForum>> listActiveForums(@HttlVar DisqusPage page);

	/*
	 * Returns a list of forums a user owns.
	 * 
	 * https://disqus.com/api/docs/users/listForums/
	 */
	@HttlCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForums(@HttlVar("user") long user, @HttlVar DisqusPage page);

	@HttlCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForums(
			@HttlVar(name = "user:username", required = true) String username, @HttlVar DisqusPage page);

	@HttlCall("GET listForums.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<DisqusForum>> listForums(@HttlVar DisqusPage page);

	/*
	 * Returns a list of posts made by the user.
	 * 
	 * https://disqus.com/api/docs/users/listPosts/
	 */

	@HttlCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@HttlVar("user") long user, @HttlVar DisqusPage page,
			@HttlVar("include") PostState... include);

	@HttlCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@HttlVar(name = "user:username", required = true) String username,
			@HttlVar DisqusPage page, @HttlVar("include ") PostState... include);

	@HttlCall("GET listPosts.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<DisqusPost>> listPosts(@HttlVar DisqusPage page, @HttlVar("include") PostState... include);

	@HttlCall("GET listPosts.json")
	public ListPostsBuilder listPosts(DisqusPage page);

	public interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		public ListPostsBuilder user(@HttlVar("user") long user);

		public ListPostsBuilder username(@HttlVar(name = "user:username") String username);

		public ListPostsBuilder include(PostState... include);

		public ListPostsBuilder related(Related... related);
	}

	/*
	 * https://disqus.com/api/docs/users/updateProfile/
	 * 
	 * Updates user profile.
	 * All fields are optional, but any field not present will be updated as blank.
	 */

	@HttlCall("POST updateProfile.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<Void[]> updateProfile(@HttlVar("about") String about, @HttlVar("name") String name,
			@HttlVar("url") String url, @HttlVar("location") String location);

	@HttlCall("POST updateProfile.json")
	public DisqusResponse<Void[]> updateProfile(@HttlVar(required = true) Identity token, @HttlVar("about") String about,
			@HttlVar("name") String name, @HttlVar("url") String url, @HttlVar("location") String location);

}

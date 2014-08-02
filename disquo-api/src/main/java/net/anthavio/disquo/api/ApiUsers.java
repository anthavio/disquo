package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * https://disqus.com/api/docs/users/
 * 
 * @author vanek
 *
 */
@RestApi("/users/")
public interface ApiUsers {

	/**
	 * For taken or illegal usernames it returns http 400 and response
	 * "{"code":2,"response":"Invalid argument, 'username': Username already exists."}"
	 */
	@RestCall("POST checkUsername.json")
	public DisqusResponse<String> checkUsername(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "username", required = true) String username) throws DisqusServerException;

	/*
	 * https://disqus.com/api/docs/users/details/
	 */

	@RestCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@RestVar("user") long user);

	@RestCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@RestVar(name = "user:username", required = true) String username);

	@RestCall("GET details.json")
	public DisqusResponse<DisqusUser> detailsOfMe(@RestVar(name = "access_token", required = true) String access_token);

	/*
	 * https://disqus.com/api/docs/users/follow/
	 */

	@RestCall("POST follow.json")
	public DisqusResponse<Void[]> follow(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar("target") long user);

	@RestCall("POST follow.json")
	public DisqusResponse<Void[]> follow(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "target:username", required = true) String username);

	/*
	 * https://disqus.com/api/docs/users/unfollow/
	 */

	@RestCall("POST unfollow.json")
	public DisqusResponse<Void[]> unfollow(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar("target") long user);

	@RestCall("POST unfollow.json")
	public DisqusResponse<Void[]> unfollow(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "target:username", required = true) String username);

	/*
	 * Returns a list of forums a user has been active on.
	 * 
	 * https://disqus.com/api/docs/users/listActiveForums/
	 */

	@RestCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForums(@RestVar("user") long user, @RestVar DisqusPageable page);

	@RestCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForums(
			@RestVar(name = "user:username", required = true) String username, @RestVar DisqusPageable page);

	@RestCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForumsOfMe(
			@RestVar(name = "access_token", required = true) String access_token, @RestVar DisqusPageable page);

	/*
	 * Returns a list of forums a user owns.
	 * 
	 * https://disqus.com/api/docs/users/listForums/
	 */
	@RestCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForums(@RestVar("user") long user, @RestVar DisqusPageable page);

	@RestCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForums(
			@RestVar(name = "user:username", required = true) String username, @RestVar DisqusPageable page);

	@RestCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForumsOfMe(
			@RestVar(name = "access_token", required = true) String access_token, @RestVar DisqusPageable page);

	/*
	 * Returns a list of posts made by the user.
	 * 
	 * https://disqus.com/api/docs/users/listPosts/
	 */

	@RestCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@RestVar("user") long user, @RestVar DisqusPageable page,
			@RestVar("include ") PostState... include);

	@RestCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@RestVar(name = "user:username", required = true) String username,
			@RestVar DisqusPageable page, @RestVar("include ") PostState... include);

	@RestCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPostsOfMe(
			@RestVar(name = "access_token", required = true) String access_token, @RestVar DisqusPageable page,
			@RestVar("include ") PostState... include);

	/*
	 * https://disqus.com/api/docs/users/updateProfile/
	 * 
	 * Updates user profile.
	 * All fields are optional, but any field not present will be updated as blank.
	 */

	@RestCall("POST updateProfile.json")
	public DisqusResponse<Void[]> updateProfile(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar("about") String about, @RestVar("name") String name, @RestVar("url") String url,
			@RestVar("location") String location);

}

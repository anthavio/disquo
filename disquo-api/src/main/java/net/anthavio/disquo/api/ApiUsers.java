package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/users/
 * 
 * @author vanek
 *
 */
@HttlApi("/users/")
public interface ApiUsers {

	/**
	 * For taken or illegal usernames it returns http 400 and response
	 * "{"code":2,"response":"Invalid argument, 'username': Username already exists."}"
	 */
	@HttlCall("POST checkUsername.json")
	public DisqusResponse<String> checkUsername(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "username", required = true) String username) throws DisqusServerException;

	/*
	 * https://disqus.com/api/docs/users/details/
	 */

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@HttlVar("user") long user);

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusUser> details(@HttlVar(name = "user:username", required = true) String username);

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusUser> detailsOfMe(@HttlVar(name = "access_token", required = true) String access_token);

	/*
	 * https://disqus.com/api/docs/users/follow/
	 */

	@HttlCall("POST follow.json")
	public DisqusResponse<Void[]> follow(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar("target") long user);

	@HttlCall("POST follow.json")
	public DisqusResponse<Void[]> follow(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "target:username", required = true) String username);

	/*
	 * https://disqus.com/api/docs/users/unfollow/
	 */

	@HttlCall("POST unfollow.json")
	public DisqusResponse<Void[]> unfollow(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar("target") long user);

	@HttlCall("POST unfollow.json")
	public DisqusResponse<Void[]> unfollow(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "target:username", required = true) String username);

	/*
	 * Returns a list of forums a user has been active on.
	 * 
	 * https://disqus.com/api/docs/users/listActiveForums/
	 */

	@HttlCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForums(@HttlVar("user") long user, @HttlVar DisqusPageable page);

	@HttlCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForums(
			@HttlVar(name = "user:username", required = true) String username, @HttlVar DisqusPageable page);

	@HttlCall("GET listActiveForums.json")
	public DisqusResponse<List<DisqusForum>> listActiveForumsOfMe(
			@HttlVar(name = "access_token", required = true) String access_token, @HttlVar DisqusPageable page);

	/*
	 * Returns a list of forums a user owns.
	 * 
	 * https://disqus.com/api/docs/users/listForums/
	 */
	@HttlCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForums(@HttlVar("user") long user, @HttlVar DisqusPageable page);

	@HttlCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForums(
			@HttlVar(name = "user:username", required = true) String username, @HttlVar DisqusPageable page);

	@HttlCall("GET listForums.json")
	public DisqusResponse<List<DisqusForum>> listForumsOfMe(
			@HttlVar(name = "access_token", required = true) String access_token, @HttlVar DisqusPageable page);

	/*
	 * Returns a list of posts made by the user.
	 * 
	 * https://disqus.com/api/docs/users/listPosts/
	 */

	@HttlCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@HttlVar("user") long user, @HttlVar DisqusPageable page,
			@HttlVar("include ") PostState... include);

	@HttlCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPosts(@HttlVar(name = "user:username", required = true) String username,
			@HttlVar DisqusPageable page, @HttlVar("include ") PostState... include);

	@HttlCall("GET listPosts.json")
	public DisqusResponse<List<DisqusPost>> listPostsOfMe(
			@HttlVar(name = "access_token", required = true) String access_token, @HttlVar DisqusPageable page,
			@HttlVar("include ") PostState... include);

	/*
	 * https://disqus.com/api/docs/users/updateProfile/
	 * 
	 * Updates user profile.
	 * All fields are optional, but any field not present will be updated as blank.
	 */

	@HttlCall("POST updateProfile.json")
	public DisqusResponse<Void[]> updateProfile(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar("about") String about, @HttlVar("name") String name, @HttlVar("url") String url,
			@HttlVar("location") String location);

}

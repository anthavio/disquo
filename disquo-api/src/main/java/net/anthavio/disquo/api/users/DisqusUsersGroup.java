package net.anthavio.disquo.api.users;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusFeatureGroup;
import net.anthavio.disquo.api.QUser;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusUsersGroup extends DisqusFeatureGroup {

	public DisqusUsersGroup(Disqus disqus) {
		super(disqus);
	}

	public CheckUsernameMethod checkUsername(String username) {
		return new CheckUsernameMethod(this.disqus).setUsername(username);
	}

	public UserDetailsMethod details() {
		return new UserDetailsMethod(this.disqus);
	}

	public UserDetailsMethod details(long userId) {
		return details(QUser.build(userId));
	}

	public UserDetailsMethod details(String userName) {
		return details(QUser.build(userName));
	}

	public UserDetailsMethod details(QUser user) {
		return new UserDetailsMethod(this.disqus).setUser(user);
	}

	public UserListPostsMethod listPosts() {
		return new UserListPostsMethod(this.disqus);
	}

	public UserListPostsMethod listPosts(QUser user) {
		return new UserListPostsMethod(this.disqus).setUser(user);
	}

	public UserListPostsMethod listPosts(long userId) {
		return listPosts(QUser.build(userId));
	}

	public UserListPostsMethod listPosts(String userName) {
		return new UserListPostsMethod(this.disqus).setUser(userName);
	}

	public UserListActiveForumsMethod listActiveForums() {
		return new UserListActiveForumsMethod(this.disqus);
	}

	public UserListActiveForumsMethod listActiveForums(long userId) {
		return new UserListActiveForumsMethod(this.disqus).setUser(userId);
	}

	public UserListForumsMethod listForums() {
		return new UserListForumsMethod(this.disqus);
	}

	public UserListForumsMethod listForums(QUser user) {
		return new UserListForumsMethod(this.disqus).setUser(user);
	}

	public UserListForumsMethod listForums(long userId) {
		return listForums(QUser.build(userId));
	}

	public UserListForumsMethod listForums(String userName) {
		return listForums(QUser.build(userName));
	}

	public FollowMethod follow(QUser user) {
		return new FollowMethod(this.disqus).setUser(user);
	}

	public FollowMethod follow(long userId) {
		return follow(QUser.build(userId));
	}

	public FollowMethod follow(String userName) {
		return follow(QUser.build(userName));
	}

	public UnfollowMethod unfollow(QUser user) {
		return new UnfollowMethod(this.disqus).setUser(user);
	}

	public UnfollowMethod unfollow(long userId) {
		return unfollow(QUser.build(userId));
	}

	public UnfollowMethod unfollow(String username) {
		return unfollow(QUser.build(username));
	}

}

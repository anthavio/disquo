package net.anthavio.disquo.api.users;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorPostsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class UserListPostsMethod extends DisqusCursorPostsMethod<UserListPostsMethod, DisqusPost> {

	public UserListPostsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Users.listPosts);
	}

	public UserListPostsMethod setUser(long userId) {
		return setUser(QUser.build(userId));
	}

	public UserListPostsMethod setUser(String username) {
		return setUser(QUser.build(username));
	}

	public UserListPostsMethod setUser(QUser user) {
		addParam("user", user);
		return this;
	}

	@Override
	protected UserListPostsMethod getSelf() {
		return this;
	}

}

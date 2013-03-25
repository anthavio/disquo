package com.anthavio.disquo.api.users;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class UserListPostsMethod extends DisqusCursorPostsMethod<DisqusPost> {

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

}

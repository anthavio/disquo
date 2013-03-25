package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.users.CheckUsernameMethod;
import com.anthavio.disquo.api.users.FollowMethod;
import com.anthavio.disquo.api.users.UnfollowMethod;
import com.anthavio.disquo.api.users.UserDetailsMethod;
import com.anthavio.disquo.api.users.UserListActiveForumsMethod;
import com.anthavio.disquo.api.users.UserListForumsMethod;
import com.anthavio.disquo.api.users.UserListPostsMethod;

public class UsersTest extends DisqusMethodTest {

	@Test
	public void checkUsername() {
		CheckUsernameMethod method = disqus.users().checkUsername("username");

		assertThat(getParameters(method).size()).isEqualTo(1);
	}

	@Test
	public void details() {
		UserDetailsMethod method = disqus.users().details();
		method.setUser(1);

		assertThat(getParameters(method).size()).isEqualTo(1);
	}

	@Test
	public void follow() {
		FollowMethod method = disqus.users().follow(1);

		assertThat(getParameters(method).size()).isEqualTo(1);
	}

	@Test
	public void unfollow() {
		UnfollowMethod method = disqus.users().unfollow(1);

		assertThat(getParameters(method).size()).isEqualTo(1);
	}

	@Test
	public void listActiveForums() {
		UserListActiveForumsMethod method = disqus.users().listActiveForums();
		method.setUser(1);
		method.setSinceId(321);
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(5);
	}

	@Test
	public void listForums() {
		UserListForumsMethod method = disqus.users().listForums();
		method.setUser(1);
		method.setSinceId(321);
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(5);
	}

	@Test
	public void listPosts() {
		UserListPostsMethod method = disqus.users().listPosts();
		method.setUser(1);
		method.addRelated(Related.forum, Related.thread);
		method.addInclude(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged,
				PostState.highlighted);
		method.setSince(new Date());
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(13);
	}

}

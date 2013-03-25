package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.posts.HighlightMethod;
import com.anthavio.disquo.api.posts.PostApproveMethod;
import com.anthavio.disquo.api.posts.PostCreateMethod;
import com.anthavio.disquo.api.posts.PostDetailsMethod;
import com.anthavio.disquo.api.posts.PostListMethod;
import com.anthavio.disquo.api.posts.PostRemoveMethod;
import com.anthavio.disquo.api.posts.PostReportMethod;
import com.anthavio.disquo.api.posts.PostRestoreMethod;
import com.anthavio.disquo.api.posts.PostSpamMethod;
import com.anthavio.disquo.api.posts.PostUnhighlightMethod;
import com.anthavio.disquo.api.posts.PostUpdateMethod;
import com.anthavio.disquo.api.posts.PostVoteMethod;
import com.anthavio.disquo.api.posts.PostCreateMethod.AnonymousData;

public class PostsTest extends DisqusMethodTest {

	private static final int thread = 0;

	@Test
	public void create() {
		PostCreateMethod method = disqus.posts().create(thread, "message");
		method.setParent(0);

		AnonymousData auth = new AnonymousData("username", "email@email.com");
		method.setAuthorAuth(auth);
		method.setAuthorUrl("http://x.y.com");

		method.setState(PostState.unapproved);
		method.setDate(new Date());
		method.setIpAddress("127.0.0.1");
		assertThat(getParameters(method).size()).isEqualTo(9);
	}

	@Test
	public void details() {
		PostDetailsMethod method = disqus.posts().details(1);
		method.addRelated(Related.forum, Related.thread);
		assertThat(getParameters(method).size()).isEqualTo(3);
	}

	@Test
	public void update() {
		PostUpdateMethod method = disqus.posts().update(1, "message");
		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void list() {
		PostListMethod method = disqus.posts().list(1);
		method.addCategory(1, 2);
		method.addThread(2, 3);
		method.addForum("forum1", "forum2");
		method.addRelated(Related.forum, Related.thread);
		method.addInclude(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged,
				PostState.highlighted);
		method.setSince(new Date());
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(19);
	}

	@Test
	public void approve() {
		PostApproveMethod method = disqus.posts().approve(1);
		method.addPost(2, 3, 4);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void highlight() {
		HighlightMethod method = disqus.posts().highlight(1);
		method.addPost(2, 3, 4);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void remove() {
		PostRemoveMethod method = disqus.posts().remove(1);
		method.addPost(2, 3, 4);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void report() {
		PostReportMethod method = disqus.posts().report(1);
		assertThat(getParameters(method).size()).isEqualTo(1);
	}

	@Test
	public void restore() {
		PostRestoreMethod method = disqus.posts().restore(1);
		method.addPost(2, 3, 4);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void spam() {
		PostSpamMethod method = disqus.posts().spam(1);
		method.addPost(2, 3, 4);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void unhighlight() {
		PostUnhighlightMethod method = disqus.posts().unhighlight(1);
		method.addPost(2, 3, 4);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void vote() {
		PostVoteMethod method = disqus.posts().vote(1, Vote.MINUS);
		assertThat(getParameters(method).size()).isEqualTo(2);
	}

}

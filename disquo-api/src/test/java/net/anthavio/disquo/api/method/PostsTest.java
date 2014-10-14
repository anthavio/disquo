package net.anthavio.disquo.api.method;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiPosts.CreatePostBuilder;
import net.anthavio.disquo.api.ApiPosts.ListPostsBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.Vote;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.junit.Test;

public class PostsTest extends DisqusMethodTest {

	private static final int thread = 0;

	@Test
	public void create() {
		CreatePostBuilder method = disqus.posts().createBuilder(thread, "My unicorn got cancer");
		method.parent(99);
		method.author_name("Pista Hufnagl");
		method.author_email("email@email.com");
		method.author_url("http://x.y.com");
		method.state(PostState.unapproved);
		method.date(new Date(1));
		method.ip_address("127.0.0.1");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(9 + 1);
	}

	@Test
	public void details() {
		disqus.posts().details(123, Related.forum, Related.thread);
		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void context() {
		disqus.posts().getContext(123l, 2, Related.forum, Related.thread);
		assertThat(getParameters().size()).isEqualTo(3 + 1);
	}

	@Test
	public void update() {
		disqus.posts().update(Identity.access("zxzxzx"), 123, "message");
		assertThat(getParameters().size()).isEqualTo(3 + 1);
	}

	@Test
	public void list() {
		ListPostsBuilder builder = disqus.posts().list();
		builder.category(1, 2);
		builder.thread(2, 3);
		builder.forum("forum1", "forum2");
		builder.related(Related.forum, Related.thread);
		builder.include(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged,
				PostState.highlighted);
		builder.since(new Date(1));
		builder.cursor("cursor");
		builder.limit(99);
		builder.order(Order.desc);
		builder.execute();

		assertThat(getParameters().size()).isEqualTo(9 + 1);
	}

	@Test
	public void approve() {
		disqus.posts().approve(1, 2, 3, 4);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void remove() {
		disqus.posts().remove(1, 2, 3, 4);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void report() {
		disqus.posts().report(123);
		assertThat(getParameters().size()).isEqualTo(1 + 1);
	}

	@Test
	public void restore() {
		disqus.posts().restore(1, 2, 3, 4);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void spam() {
		disqus.posts().spam(Identity.access("zxzxzx"), 1, 2, 3, 4);
		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void vote() {
		disqus.posts().vote(123, Vote.MINUS);
		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

}

package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiThreads.CreateThreadBuilder;
import net.anthavio.disquo.api.ApiThreads.ListPostsBuilder;
import net.anthavio.disquo.api.ApiThreads.ListThreadsBuilder;
import net.anthavio.disquo.api.ApiThreads.ThreadUpdateBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.ArgumentConfig.Vote;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.testng.annotations.Test;

public class ThreadsTest extends DisqusMethodTest {

	@Test
	public void close() {
		disqus.threads().close(1, 2, 3);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void create() {
		CreateThreadBuilder builder = disqus.threads().createBuilder("forum", "title");
		builder.category(1);
		builder.url("http://a.b.c/");
		builder.date(new Date(1));
		builder.message("message");
		builder.identifier("123456");
		builder.slug("slug");
		builder.execute();

		assertThat(getParameters().size()).isEqualTo(8 + 1 + 1);
	}

	@Test
	public void details() {
		disqus.threads().details(123, Related.forum, Related.author, Related.category);

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void list() {
		ListThreadsBuilder method = disqus.threads().list();
		method.category(1, 2);
		method.forum("forum1", "forum2");
		method.thread(123);
		method.thread("tident");
		method.author(123);
		method.author("username");
		method.related(Related.forum, Related.author, Related.category);
		method.include(ThreadState.open, ThreadState.closed);
		method.since(new Date(1));
		method.cursor("0:0:0");
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(12 + 1);
	}

	@Test
	public void set() {
		disqus.threads().set("forum", "t1", "t2", "t3");

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void listPosts() {
		ListPostsBuilder method = disqus.threads().listPosts("t1", "forum");
		method.since(new Date());
		method.related(Related.forum, Related.thread);
		method.include(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged);
		method.cursor("0:0:0");
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(8 + 1);
	}

	@Test
	public void open() {
		disqus.threads().open(1, 2, 3);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void remove() {
		disqus.threads().remove(1, 2, 3);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void restore() {
		disqus.threads().restore(1, 2, 3);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void subscribe() {
		disqus.threads().subscribe(123, "gogo@gege.com");

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void unsubscribe() {
		disqus.threads().unsubscribe(1, "gogo@gege.com");

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void update() {
		ThreadUpdateBuilder method = disqus.threads().update(123);
		method.category(1);
		method.forum("forum");
		method.title("title");
		method.url("http://q.w.e/rty");
		method.author(132);
		method.message("message");
		method.identifier("654321");
		method.slug("slug");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(9 + 1 + 1);
	}

	@Test
	public void vote() {
		disqus.threads().vote(Vote.MINUS, "t1", "forum");
		assertThat(getParameters().size()).isEqualTo(3 + 1);
	}

}

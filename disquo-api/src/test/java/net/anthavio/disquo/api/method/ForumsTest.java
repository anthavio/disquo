package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiForums.ListPostsBuilder;
import net.anthavio.disquo.api.ApiForums.ListThreadsBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.DisqusMethodTest;
import net.anthavio.disquo.api.DisqusPage;

import org.testng.annotations.Test;

public class ForumsTest extends DisqusMethodTest {

	private static final String CURSOR = "0:0:0";
	private static final String FORUM = "forum";

	@Test
	public void addModerator() {
		disqus.forums().addModerator(FORUM, "unworthy");
		assertThat(getParameters().size()).isEqualTo(2 + 1 + 1);
	}

	@Test
	public void removeModerator() {
		disqus.forums().removeModerator(666);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void create() {
		disqus.forums().create(FORUM, "website", "name");
		assertThat(getParameters().size()).isEqualTo(3 + 1 + 1);
	}

	@Test
	public void details() {
		disqus.forums().details(FORUM, Related.author);
		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void listCategories() {
		DisqusPage page = new DisqusPage("0:0:0");
		page.setSince_id(333l);
		page.setLimit(99);
		page.setOrder(Order.desc);
		disqus.forums().listCategories(FORUM, page);

		assertThat(getParameters().size()).isEqualTo(5 + 1);
	}

	@Test
	public void listPosts() {
		ListPostsBuilder method = disqus.forums().listPosts(FORUM);
		method.since(new Date(1));
		method.related(Related.thread);
		method.query("what");
		method.include(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged);
		method.cursor(CURSOR);
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(8 + 1);
	}

	@Test
	public void listThreads() {
		ListThreadsBuilder method = disqus.forums().listThreads(FORUM);
		method.since(new Date(1));
		method.related(Related.forum, Related.author);
		method.include(ThreadState.open, ThreadState.closed);
		method.cursor(CURSOR);
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(7 + 1);
	}

	@Test
	public void listUsers() {
		DisqusPage page = new DisqusPage("0:0:0");
		page.setSince_id(333l);
		page.setLimit(99);
		page.setOrder(Order.desc);
		disqus.forums().listUsers(FORUM, page);

		assertThat(getParameters().size()).isEqualTo(5 + 1);
	}

	@Test
	public void listModerators() {
		disqus.forums().listModerators(FORUM);
		assertThat(getParameters().size()).isEqualTo(1 + 1 + 1);
	}

	@Test
	public void listMostActiveUsers() {
		DisqusPage page = new DisqusPage("0:0:0");
		page.setLimit(99);
		page.setOrder(Order.desc);
		disqus.forums().listMostActiveUsers(FORUM, page);

		assertThat(getParameters().size()).isEqualTo(4 + 1);
	}

	@Test
	public void listMostLikedUsers() {
		DisqusPage page = new DisqusPage("0:0:0");
		page.setLimit(99);
		page.setOrder(Order.desc);
		disqus.forums().listMostLikedUsers(FORUM, page);

		assertThat(getParameters().size()).isEqualTo(4 + 1);
	}
}

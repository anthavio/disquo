package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.DisqusMethodTest;
import net.anthavio.disquo.api.QThread;
import net.anthavio.disquo.api.QUser;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.forums.AddModeratorMethod;
import net.anthavio.disquo.api.forums.ForumCreateMethod;
import net.anthavio.disquo.api.forums.ForumDetailsMethod;
import net.anthavio.disquo.api.forums.ForumListCategoriesMethod;
import net.anthavio.disquo.api.forums.ForumListModeratorsMethod;
import net.anthavio.disquo.api.forums.ForumListMostActiveUsersMethod;
import net.anthavio.disquo.api.forums.ForumListMostLikedUsersMethod;
import net.anthavio.disquo.api.forums.ForumListPostsMethod;
import net.anthavio.disquo.api.forums.ForumListThreadsMethod;
import net.anthavio.disquo.api.forums.ForumListUsersMethod;
import net.anthavio.disquo.api.forums.RemoveModeratorMethod;

import org.testng.annotations.Test;


public class ForumsTest extends DisqusMethodTest {

	private static final String CURSOR = "cursor";
	private static final String FORUM = "forum";

	@Test
	public void addModerator() {
		AddModeratorMethod method = disqus.forums().addModerator(FORUM, QUser.build(666));
		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void removeModerator() {
		RemoveModeratorMethod method = disqus.forums().removeModerator(FORUM, QUser.build(666));
		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void create() {
		ForumCreateMethod method = disqus.forums().create(FORUM, "website", "name");
		assertThat(getParameters(method).size()).isEqualTo(3);
	}

	@Test
	public void details() {
		ForumDetailsMethod method = disqus.forums().details(FORUM);
		method.addRelated(Related.author);
		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void listCategories() {
		ForumListCategoriesMethod method = disqus.forums().listCategories(FORUM);
		method.setSince_id("1");
		method.setCursor(CURSOR);
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(5);
	}

	@Test
	public void listPosts() {
		ForumListPostsMethod method = disqus.forums().listPosts(FORUM);
		method.setSince(new Date());
		method.addRelated(Related.thread);
		method.setQuery("???");
		method.addInclude(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged);
		method.setCursor(CURSOR);
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(12);
	}

	@Test
	public void listThreads() {
		ForumListThreadsMethod method = disqus.forums().listThreads(FORUM);
		method.addThread(QThread.build(0), QThread.ByIdent("thread1"));
		method.setSince(new Date());
		method.addRelated(Related.forum, Related.author);
		method.addInclude(ThreadState.open, ThreadState.closed);
		method.setCursor(CURSOR);
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(11);
	}

	@Test
	public void listUsers() {
		ForumListUsersMethod method = disqus.forums().listUsers(FORUM);
		method.setSince_id(333);
		method.setCursor(CURSOR);
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(5);
	}

	@Test
	public void listModerators() {
		ForumListModeratorsMethod method = disqus.forums().listModerators(FORUM);
		assertThat(getParameters(method).size()).isEqualTo(1);
	}

	@Test
	public void listMostActiveUsers() {
		ForumListMostActiveUsersMethod method = disqus.forums().listMostActiveUsers(FORUM);
		method.setCursor(CURSOR);
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void listMostLikedUsers() {
		ForumListMostLikedUsersMethod method = disqus.forums().listMostLikedUsers(FORUM);
		method.setCursor(CURSOR);
		method.setLimit(99);
		method.setOrder(Order.desc);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}
}

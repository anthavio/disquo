package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.QThread;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.ArgumentConfig.ThreadState;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.threads.ThreadCloseMethod;
import com.anthavio.disquo.api.threads.ThreadCreateMethod;
import com.anthavio.disquo.api.threads.ThreadDetailsMethod;
import com.anthavio.disquo.api.threads.ThreadListMethod;
import com.anthavio.disquo.api.threads.ThreadListPostsMethod;
import com.anthavio.disquo.api.threads.ThreadListReactionsMethod;
import com.anthavio.disquo.api.threads.ThreadOpenMethod;
import com.anthavio.disquo.api.threads.ThreadRemoveMethod;
import com.anthavio.disquo.api.threads.ThreadRestoreMethod;
import com.anthavio.disquo.api.threads.ThreadSetMethod;
import com.anthavio.disquo.api.threads.ThreadSubscribeMethod;
import com.anthavio.disquo.api.threads.ThreadUnsubscribeMethod;
import com.anthavio.disquo.api.threads.ThreadUpdateMethod;
import com.anthavio.disquo.api.threads.ThreadVoteMethod;

public class ThreadsTest extends DisqusMethodTest {

	@Test
	public void close() {
		ThreadCloseMethod method = disqus.threads().close(1);
		method.addThread(2, 3);
		method.setForum("forum");
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void create() {
		ThreadCreateMethod method = disqus.threads().create("forum", "title");
		method.setCategory(1);
		method.setUrl("url");
		method.setDate(new Date());
		method.setMessage("message");
		method.setIdentifier("123456");
		method.setSlug("slug");

		assertThat(getParameters(method).size()).isEqualTo(8);
	}

	@Test
	public void details() {
		ThreadDetailsMethod method = disqus.threads().details(1);
		method.setForum("forum");
		method.addRelated(Related.forum, Related.author, Related.category);

		assertThat(getParameters(method).size()).isEqualTo(5);
	}

	@Test
	public void list() {
		ThreadListMethod method = disqus.threads().list();
		method.addCategory(1, 2);
		method.addForum("forum1", "forum2");
		method.addThread(QThread.build(1), QThread.ByIdent("ident"), QThread.ByLink("link"));
		method.addAuthor(QUser.build(1), QUser.build("username"));
		method.addRelated(Related.forum, Related.author, Related.category);
		method.addInclude(ThreadState.open, ThreadState.closed);
		method.setSince(new Date());
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);

		assertThat(getParameters(method).size()).isEqualTo(18);
	}

	@Test
	public void set() {
		ThreadSetMethod method = disqus.threads().set(1);
		method.addThread(2, 3);
		method.setForum("forum");
		method.addRelated(Related.forum, Related.author, Related.category);

		assertThat(getParameters(method).size()).isEqualTo(7);
	}

	@Test
	public void listPosts() {
		ThreadListPostsMethod method = disqus.threads().listPosts(1);
		method.setForum("forum");
		method.setSince(new Date());
		method.addRelated(Related.forum, Related.thread);
		method.addInclude(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged);
		method.setQuery("?query?");
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);

		assertThat(getParameters(method).size()).isEqualTo(14);
	}

	@Test
	public void listReactions() {
		ThreadListReactionsMethod method = disqus.threads().listReactions(1);
		method.setForum("forum");
		method.setCursor("cursor");
		method.setLimit(99);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void open() {
		ThreadOpenMethod method = disqus.threads().open(1);
		method.addThread(2, 3);
		method.setForum("forum");
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void remove() {
		ThreadRemoveMethod method = disqus.threads().remove(1);
		method.addThread(2, 3);
		method.setForum("forum");
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void restore() {
		ThreadRestoreMethod method = disqus.threads().restore(1);
		method.addThread(2, 3);
		method.setForum("forum");
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void subscribe() {
		ThreadSubscribeMethod method = disqus.threads().subscribe(1, "gogo@gege.com");

		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void unsubscribe() {
		ThreadUnsubscribeMethod method = disqus.threads().unsubscribe(1, "gogo@gege.com");

		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void update() {
		ThreadUpdateMethod method = disqus.threads().update(1);
		method.setCategory(1);
		method.setForum("forum");
		method.setTitle("title");
		method.setUrl("url");
		method.setAuthor(1);
		method.setMessage("message");
		method.setIdentifier("654321");
		method.setSlug("slug");
		assertThat(getParameters(method).size()).isEqualTo(9);
	}

	@Test
	public void vote() {
		ThreadVoteMethod method = disqus.threads().vote(1, Vote.MINUS);
		method.setForum("forum");
		assertThat(getParameters(method).size()).isEqualTo(3);
	}

}

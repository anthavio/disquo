package net.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import net.anthavio.disquo.TestInputData;
import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.applications.ListUsageMethod;
import net.anthavio.disquo.api.blacklists.BlacklistList;
import net.anthavio.disquo.api.forums.ForumDetailsMethod;
import net.anthavio.disquo.api.posts.PostDetailsMethod;
import net.anthavio.disquo.api.posts.PostListMethod;
import net.anthavio.disquo.api.response.DisqusCategory;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.disquo.api.threads.ThreadDetailsMethod;
import net.anthavio.disquo.api.threads.ThreadListPopularMethod;
import net.anthavio.disquo.api.threads.ThreadListPopularMethod.DisqusThreadWithPostsInInterval;
import net.anthavio.disquo.api.users.UserDetailsMethod;
import net.anthavio.disquo.api.whitelists.WhitelistList;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 * Disqus REST API is being called in theese test to verify that JSON response deserializers works
 * 
 * @author martin.vanek
 * 
 */
public class OnlineTest {

	private Disqus disqus;

	private String forum;

	private long long_thread_id;

	@BeforeClass
	public void setup() {

		TestInputData tidata = TestInputData.load("disqus-test.properties");
		this.disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());

		this.forum = tidata.getForum();
		this.long_thread_id = tidata.getLongThreadId();
	}

	@Test
	public void online_applications() {
		ListUsageMethod listUsage = this.disqus.applications().listUsage();
		listUsage.setAccessToken(this.disqus.getApplicationKeys().getAccessToken());
		DisqusResponse<Object> response = listUsage.execute();
		assertThat(response.getCode()).isEqualTo(0);
		assertThat(response.getResponse()).isNotNull();
	}

	@Test
	public void online_forum() {
		ForumDetailsMethod detailsSmall = this.disqus.forums().details(this.forum);
		DisqusResponse<DisqusForum> responseSmall = detailsSmall.execute();
		assertThat(responseSmall.getCode()).isEqualTo(0);
		assertThat(responseSmall.getResponse().getFounder()).isNotNull();
		assertThat(responseSmall.getResponse().getAuthor()).isNull();

		ForumDetailsMethod detailsBig = this.disqus.forums().details(this.forum);
		detailsBig.addRelated(Related.author);
		DisqusResponse<DisqusForum> responseBig = detailsBig.execute();
		assertThat(responseBig.getCode()).isEqualTo(0);
		assertThat(responseBig.getResponse().getFounder()).isNotNull();
		assertThat(responseBig.getResponse().getAuthor()).isNotNull();
	}

	@Test
	public void online_thread() {
		ThreadDetailsMethod detailsSmall = this.disqus.threads().details(this.long_thread_id);
		DisqusResponse<DisqusThread> responseSmall = detailsSmall.execute();
		assertThat(responseSmall.getCode()).isEqualTo(0);
		assertThat(responseSmall.getResponse().getForum()).isInstanceOf(String.class);
		assertThat(responseSmall.getResponse().getAuthor()).isInstanceOf(Long.class);
		assertThat(responseSmall.getResponse().getCategory()).isInstanceOf(Long.class);

		ThreadDetailsMethod detailsBig = this.disqus.threads().details(this.long_thread_id);
		detailsBig.addRelated(Related.forum, Related.author, Related.category);
		DisqusResponse<DisqusThread> responseBig = detailsBig.execute();
		assertThat(responseBig.getCode()).isEqualTo(0);
		assertThat(responseBig.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		assertThat(responseBig.getResponse().getAuthor()).isInstanceOf(DisqusUser.class);
		assertThat(responseBig.getResponse().getCategory()).isInstanceOf(DisqusCategory.class);
	}

	@Test
	public void online_thread_popular() {
		ThreadListPopularMethod listPopular = this.disqus.threads().listPopular(this.forum).setInterval("90d");
		DisqusResponse<List<DisqusThreadWithPostsInInterval>> response = listPopular.execute();
		assertThat(response.getCode()).isEqualTo(0);
	}

	@Test
	public void online_post() {
		//find post first
		PostListMethod postList = this.disqus.posts().list(this.long_thread_id);
		Long postId = postList.execute().getResponse().get(0).getId();

		PostDetailsMethod detailsSmall = this.disqus.posts().details(postId);
		DisqusResponse<DisqusPost> responseSmall = detailsSmall.execute();
		assertThat(responseSmall.getCode()).isEqualTo(0);
		assertThat(responseSmall.getResponse().getForum()).isInstanceOf(String.class);
		assertThat(responseSmall.getResponse().getThread()).isInstanceOf(Long.class);

		PostDetailsMethod detailsBig = this.disqus.posts().details(postId);
		detailsBig.addRelated(Related.forum, Related.thread);
		DisqusResponse<DisqusPost> responseBig = detailsBig.execute();
		assertThat(responseBig.getCode()).isEqualTo(0);
		assertThat(responseBig.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		assertThat(responseBig.getResponse().getThread()).isInstanceOf(DisqusThread.class);
	}

	@Test
	public void online_user() {
		UserDetailsMethod detailsSmall = this.disqus.users().details("anthavio");
		DisqusResponse<DisqusUser> responseSmall = detailsSmall.execute();
		assertThat(responseSmall.getCode()).isEqualTo(0);
	}

	@Test
	public void online_blacklist() {
		BlacklistList list = this.disqus.blacklists().list(this.forum);
		list.setAccessToken(this.disqus.getApplicationKeys().getAccessToken());
		DisqusResponse<List<DisqusFilter>> response = list.execute();
		assertThat(response.getCode()).isEqualTo(0);
	}

	@Test
	public void online_whitelist() {
		WhitelistList list = this.disqus.whitelists().list(this.forum);
		list.setAccessToken(this.disqus.getApplicationKeys().getAccessToken());
		DisqusResponse<List<DisqusFilter>> response = list.execute();
		assertThat(response.getCode()).isEqualTo(0);
	}
}
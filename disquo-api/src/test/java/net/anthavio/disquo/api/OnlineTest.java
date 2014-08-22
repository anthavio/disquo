package net.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import net.anthavio.disquo.TestInputData;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.response.DisqusCategory;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusUser;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Disqus REST API is being called in theese test to verify that JSON response deserializers works
 * 
 * @author martin.vanek
 * 
 */
public class OnlineTest {

	private DisqusApi disqus;

	private String forum;

	private String token;

	private long long_thread_id;

	@BeforeClass
	public void setup() {

		TestInputData tidata = TestInputData.load("disqus-test.properties");
		this.disqus = new DisqusApi(tidata.getApplicationKeys(), tidata.getUrl());

		this.forum = tidata.getForum();
		this.long_thread_id = tidata.getLongThreadId();
		this.token = disqus.getApplicationKeys().getAccessToken();
	}

	@Test
	public void online_applications() {
		DisqusResponse<List<String[]>> listUsage = this.disqus.applications().listUsage(Identity.access(token));
		assertThat(listUsage.getCode()).isEqualTo(0);
		assertThat(listUsage.getResponse()).isNotNull();
	}

	@Test
	public void online_forum() {
		DisqusResponse<DisqusForum> detailsSmall = this.disqus.forums().details(this.forum);
		assertThat(detailsSmall.getCode()).isEqualTo(0);
		assertThat(detailsSmall.getResponse().getFounder()).isNotNull();
		assertThat(detailsSmall.getResponse().getAuthor()).isNull();

		DisqusResponse<DisqusForum> detailsBig = this.disqus.forums().details(this.forum, Related.author);
		assertThat(detailsBig.getCode()).isEqualTo(0);
		assertThat(detailsBig.getResponse().getFounder()).isNotNull();
		assertThat(detailsBig.getResponse().getAuthor()).isNotNull();
	}

	@Test
	public void online_thread() {
		DisqusResponse<DisqusThread> detailsSmall = this.disqus.threads().details(this.long_thread_id);
		assertThat(detailsSmall.getCode()).isEqualTo(0);
		assertThat(detailsSmall.getResponse().getForum()).isInstanceOf(String.class);
		assertThat(detailsSmall.getResponse().getAuthor()).isInstanceOf(Long.class);
		assertThat(detailsSmall.getResponse().getCategory()).isInstanceOf(Long.class);

		DisqusResponse<DisqusThread> detailsBig = this.disqus.threads().details(this.long_thread_id, Related.forum,
				Related.author, Related.category);
		assertThat(detailsBig.getCode()).isEqualTo(0);
		assertThat(detailsBig.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		assertThat(detailsBig.getResponse().getAuthor()).isInstanceOf(DisqusUser.class);
		assertThat(detailsBig.getResponse().getCategory()).isInstanceOf(DisqusCategory.class);
	}

	@Test
	public void online_post() {
		//find post first
		DisqusResponse<List<DisqusPost>> posts = this.disqus.posts().list(this.long_thread_id, null);
		Long postId = posts.getResponse().get(0).getId();

		DisqusResponse<DisqusPost> detailsSmall = this.disqus.posts().details(postId);
		assertThat(detailsSmall.getCode()).isEqualTo(0);
		assertThat(detailsSmall.getResponse().getForum()).isInstanceOf(String.class);
		assertThat(detailsSmall.getResponse().getThread()).isInstanceOf(Long.class);

		DisqusResponse<DisqusPost> detailsBig = this.disqus.posts().details(postId, Related.forum, Related.thread);
		assertThat(detailsBig.getCode()).isEqualTo(0);
		assertThat(detailsBig.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		assertThat(detailsBig.getResponse().getThread()).isInstanceOf(DisqusThread.class);
	}

	@Test
	public void online_user() {
		DisqusResponse<DisqusUser> details = this.disqus.users().details("anthavio");
		assertThat(details.getCode()).isEqualTo(0);
	}

	@Test
	public void online_blacklist() {
		DisqusResponse<List<DisqusFilter>> list = this.disqus.blacklists().list(this.forum);
		assertThat(list.getCode()).isEqualTo(0);
	}

	@Test
	public void online_whitelist() {
		DisqusResponse<List<DisqusFilter>> list = this.disqus.whitelists().list(this.forum);
		assertThat(list.getCode()).isEqualTo(0);
	}
}
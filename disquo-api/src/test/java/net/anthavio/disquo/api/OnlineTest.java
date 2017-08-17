package net.anthavio.disquo.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import net.anthavio.disquo.TestInputData;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.response.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Disqus REST API is being called in theese test to verify that JSON response deserializers works
 * 
 * @author martin.vanek
 * 
 */
public class OnlineTest {

	private static DisqusApi disqus;

	private static String forum;

	private static String token;

	private static long long_thread_id;

	@BeforeClass
	public static void setup() {
		TestInputData tidata = TestInputData.load("disqus-test.properties");
		disqus = new DisqusApi(tidata.getApplicationKeys(), tidata.getUrl());

		forum = tidata.getForum();
		long_thread_id = tidata.getLongThreadId();
		token = disqus.getApplicationKeys().getAccessToken();
	}

	@Test
	public void online_forum_categories() {
		DisqusResponse<List<DisqusForumCategory>> list = disqus.forumCategories().list();
		assertThat(list.getCode()).isEqualTo(0);
		assertThat(list.getResponse()).isNotNull();
		assertThat(list.getResponse().size()).isGreaterThan(0);

		DisqusResponse<DisqusForumCategory> business = disqus.forumCategories().details(1);
		assertThat(business.getCode()).isEqualTo(0);
		assertThat(business.getResponse()).isNotNull();
		assertThat(business.getResponse().getId()).isEqualTo(1);
		assertThat(business.getResponse().getName()).isEqualTo("Business");
		assertThat(business.getResponse().getDate_added()).isInThePast();
	}

	@Test
	public void online_applications() {
		DisqusResponse<List<String[]>> listUsage = disqus.applications().listUsage(Identity.access(token));
		assertThat(listUsage.getCode()).isEqualTo(0);
		assertThat(listUsage.getResponse()).isNotNull();
		assertThat(listUsage.getResponse().size()).isGreaterThan(0);
	}

	@Test
	public void online_forum_detail() {
		DisqusResponse<DisqusForum> detailsSmall = disqus.forums().details(this.forum);
		assertThat(detailsSmall.getCode()).isEqualTo(0);
		assertThat(detailsSmall.getResponse().getFounder()).isNotNull();
		assertThat(detailsSmall.getResponse().getAuthor()).isNull();

		DisqusResponse<DisqusForum> detailsBig = disqus.forums().details(this.forum, Related.author);
		assertThat(detailsBig.getCode()).isEqualTo(0);
		assertThat(detailsBig.getResponse().getFounder()).isNotNull();
		assertThat(detailsBig.getResponse().getAuthor()).isNotNull();
	}

	@Test
	public void online_forum_update() {
		DisqusResponse<DisqusForum> forumNoChange = disqus.forums().update(this.forum).execute();

		DisqusResponse<DisqusForum> forum = disqus.forums().update(this.forum)
				.commentsLinkZero("commentsLinkZero").commentsLinkOne("commentsLinkOne").commentsLinkMultiple("commentsLinkMultiple").commentPolicyText("commentPolicyText")
				.guidelines("guidelines").description("description").name("Updated name").website("http://update.website.com/whatever")
				.sort(4).colorScheme("light").typeface("sans-serif").unapproveReputationLevel(3).twitterName("twitterName")
				.category("Business").forumCategory(1).attach(ArgumentConfig.Attach.forumPermissions)
				.flaggingEnabled(true).adultContent(true).validateAllPosts(true)
				.execute();
	}

	@Test
	public void online_thread() {
		DisqusResponse<DisqusThread> detailsSmall = disqus.threads().details(this.long_thread_id);
		assertThat(detailsSmall.getCode()).isEqualTo(0);
		assertThat(detailsSmall.getResponse().getForum()).isInstanceOf(String.class);
		assertThat(detailsSmall.getResponse().getAuthor()).isInstanceOf(Long.class);
		assertThat(detailsSmall.getResponse().getCategory()).isInstanceOf(Long.class);

		DisqusResponse<DisqusThread> detailsBig = disqus.threads().details(this.long_thread_id, Related.forum,
				Related.author, Related.category);
		assertThat(detailsBig.getCode()).isEqualTo(0);
		assertThat(detailsBig.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		assertThat(detailsBig.getResponse().getAuthor()).isInstanceOf(DisqusUser.class);
		assertThat(detailsBig.getResponse().getCategory()).isInstanceOf(DisqusCategory.class);
	}

	@Test
	public void online_post() {
		//find post first
		DisqusResponse<List<DisqusPost>> posts = disqus.posts().list(this.long_thread_id, null);
		Long postId = posts.getResponse().get(0).getId();

		DisqusResponse<DisqusPost> detailsSmall = disqus.posts().details(postId);
		assertThat(detailsSmall.getCode()).isEqualTo(0);
		assertThat(detailsSmall.getResponse().getForum()).isInstanceOf(String.class);
		assertThat(detailsSmall.getResponse().getThread()).isInstanceOf(Long.class);

		DisqusResponse<DisqusPost> detailsBig = disqus.posts().details(postId, Related.forum, Related.thread);
		assertThat(detailsBig.getCode()).isEqualTo(0);
		assertThat(detailsBig.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		assertThat(detailsBig.getResponse().getThread()).isInstanceOf(DisqusThread.class);
	}

	@Test
	public void online_user() {
		DisqusResponse<DisqusUser> details = disqus.users().details("anthavio");
		assertThat(details.getCode()).isEqualTo(0);
	}

	@Test
	public void online_blacklist() {
		DisqusResponse<List<DisqusFilter>> list = disqus.blacklists().list(this.forum);
		assertThat(list.getCode()).isEqualTo(0);
	}

	@Test
	public void online_whitelist() {
		DisqusResponse<List<DisqusFilter>> list = disqus.whitelists().list(this.forum);
		assertThat(list.getCode()).isEqualTo(0);
	}

}
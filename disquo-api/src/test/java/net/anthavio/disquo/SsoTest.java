package net.anthavio.disquo;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.posts.PostCreateMethod;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;


public class SsoTest {

	public static void main(String[] args) {
		TestInputData tidata = TestInputData.load("disqus-nature.properties");
		Disqus disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());

		String forum = tidata.getForum();
		//int sso_user_id = 36593632; //npg_disqus_user_id_test_1 = test-npg-deca5c19e0fca2359519475bb46a7682
		SsoAuthData sso1 = tidata.getSsoAuthData1(); //new SsoAuthData("npg_disqus_user_id_test_1", "Npg Disqus User Test 1", "anthavio@post.cz");
		try {

			//disqus.applications().listUsage().pretty();
			//disqus.forums().listThreads(forum).pretty();

			long thread_id = tidata.getLongThreadId();
			PostCreateMethod createMethod = disqus.posts().create(thread_id, "Test new post " + new Date(), sso1);
			//createMethod.setParent(637986727);
			//createMethod.pretty();
			DisqusResponse<DisqusPost> execute = createMethod.execute();
			DisqusPost response = execute.getResponse();
			Long post_id = response.getId();
			System.out.println(response);

			//ThreadDetailsMethod details = disqus.threads().details(thread_id);
			//System.out.println(details.execute().getResponse().getId());

			//waitForPost(disqus, thread_id, post_id);

		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	private static void waitForPost(Disqus disqus, int thread_id, Integer post_id) throws InterruptedException {
		boolean exist = false;
		while (!exist) {
			List<DisqusPost> allPosts = disqus.threads().listAllPosts(thread_id);
			//List<Integer> ids = new ArrayList<Integer>();
			for (DisqusPost disqusPost : allPosts) {
				if (disqusPost.getId().intValue() == post_id) {
					System.out.println("Hurray! Post is in listing " + post_id);
					System.out.println(new Date());
					exist = true;
					break;
				}
			}
			Thread.sleep(1000);
		}
	}

}

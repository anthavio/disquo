package net.anthavio.disquo;

import java.util.Date;

import net.anthavio.disquo.api.DisqusApi;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;

public class SsoTest {

	public static void main(String[] args) {
		TestInputData tidata = TestInputData.load("disqus-nature.properties");
		DisqusApi disqus = new DisqusApi(tidata.getApplicationKeys(), tidata.getUrl());

		String forum = tidata.getForum();
		//int sso_user_id = 36593632; //npg_disqus_user_id_test_1 = test-npg-deca5c19e0fca2359519475bb46a7682
		SsoAuthData sso1 = tidata.getSsoAuthData1(); //new SsoAuthData("npg_disqus_user_id_test_1", "Npg Disqus User Test 1", "anthavio@post.cz");
		try {

			//disqus.applications().listUsage().pretty();
			//disqus.forums().listThreads(forum).pretty();

			long thread_id = tidata.getLongThreadId();
			DisqusResponse<DisqusPost> execute = disqus.posts().create(sso1, tidata.getSecretKey(), thread_id,
					"Test new post " + new Date(), null);
			//createMethod.setParent(637986727);
			//createMethod.pretty();
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

}

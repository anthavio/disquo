package net.anthavio.disquo;

import net.anthavio.disquo.api.DisqusApi;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;

public class DisqusApiTest {

	public static void main(String[] args) {
		TestInputData tidata = TestInputData.load("disqus-test.properties");
		DisqusApi disqus = new DisqusApi(tidata.getApplicationKeys(), tidata.getUrl());
		//disqus.getSender().
		//DisqusResponse<DisqusThread> value = disqus.threads().details(100, null);
		//System.out.println(value);

		//ListThreadsBuilder builder = disqus.threads().list();
		//List<DisqusThread> threads = builder.forum("testforum").include(ThreadState.open, ThreadState.closed).execute()
		//		.getResponse();
		/*
		List<DisqusThread> threads = disqus.threads().set(1092529193, 1045227591, 1807462718, 2853108846l).getResponse();
		for (DisqusThread thread : threads) {
			System.out.println(thread);
		}
		
		if (true)
			return;
		*/
		//tidata.getAccessToken()
		DisqusResponse<DisqusPost> post = disqus.posts().update(tidata.getAccessToken(), 1516808009, "Different pokus");
		System.out.println(post);

		//System.out.println(response.getResponse());
		disqus.close();
	}
}

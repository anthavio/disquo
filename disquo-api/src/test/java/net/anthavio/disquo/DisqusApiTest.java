package net.anthavio.disquo;

import java.util.List;
import java.util.Map;

import net.anthavio.disquo.api.DisqusApi;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.impl.HttpClient4Config;

public class DisqusApiTest {

	public static void main(String[] args) {
		TestInputData tidata = TestInputData.load("disqus-test.properties");
		HttpClient4Config config = new HttpClient4Config(tidata.getUrl());
		DisqusApi disqus = new DisqusApi(tidata.getApplicationKeys(), config);
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
		//postid=1516808009
		//tidata.getAccessToken()

		DisqusResponse<List<Map>> created = disqus.imports().list(tidata.getAccessToken(), "dajc", null);
		System.out.println(created);

		//disqus.users();
		//System.out.println(forum2);

		//System.out.println(response.getResponse());
		disqus.close();
	}
}

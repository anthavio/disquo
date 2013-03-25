package com.anthavio.disquo;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.auth.OauthAuthenticator;

/**
 * 
 * @author martin.vanek
 *
 */
public class OauthTest {

	public static void main(String[] args) {
		try {
			TestInputData tidata = TestInputData.load("disqus-dajc.properties");
			Disqus disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());

			//ThreadListMethod list = disqus.threads().list(tidata.getForum());
			//list.pretty();
			OauthAuthenticator oauth = new OauthAuthenticator(disqus);
			//TokenResponse token_client = oauth.access_token_client("dajc_mod1", "kokosak");
			//System.out.println(token_client);
			//oauth.get_oauth_code("http://nature.github.com/", "x", "y");
			disqus.posts().create(934555607, "brekeke");

		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}

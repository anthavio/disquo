package net.anthavio.disquo.api.auth;

import net.anthavio.disquo.api.DisqusApi;
import net.anthavio.disquo.api.DisqusApplicationKeys;
import net.anthavio.httl.HttlRequest.Method;
import net.anthavio.httl.auth.OAuth2;
import net.anthavio.httl.auth.OAuth2Builder;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusOAuth2 {

	private final OAuth2 oauth2;

	public DisqusOAuth2(DisqusApi api, String callbackUrl) {
		DisqusApplicationKeys keys = api.getApplicationKeys();
		oauth2 = new OAuth2Builder(api.getSender()).setAuthUrl("https://disqus.com/api/oauth/2.0/authorize/")
				.setTokenUrl("https://disqus.com/api/oauth/2.0/access_token/").setTokenHttpMethod(Method.POST)
				.setClientId(keys.getApiKey()).setClientSecret(keys.getApiSecret()).setRedirectUri(callbackUrl).build();
	}

	public OAuth2 getOAuth2() {
		return oauth2;
	}
}

package net.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.TokenResponse;
import net.anthavio.httl.auth.OAuth2;
import net.anthavio.httl.auth.OAuth2Builder;

/**
 * 
 * @author martin.vanek
 *
 */
public class Examples {

	private static String forumName = "test";

	private static int threadId = 333;

	public static void main(String[] args) {
		paging();
	}

	/**
	 * 
	 */
	private static void basic() {
		/*
		To obtain Application keys
		  1. Visit http://disqus.com/api/applications/ and Log in to Disqus or Create an Account
		  2. Visit http://disqus.com/api/applications/ and Register new application
		  3. Use generated "Public Key" and "Secret Key" (Access Token is optional)
		*/
		DisqusApplicationKeys keys = new DisqusApplicationKeys("...api_key...", "...secret_key...");
		//Construct Disqus API client
		DisqusApi disqus = new DisqusApi(keys);
		//Use Fluent Builder to gather method parameters and call execute to get Response 
		DisqusResponse<List<DisqusPost>> response = disqus.posts().list(threadId, null);
		//Get list of Posts from Response
		List<DisqusPost> posts = response.getResponse();
		for (DisqusPost post : posts) {
			//Do whatever you want to...
			String text = post.getAuthor().getName() + " posted " + post.getMessage();
			System.out.println(text);
		}
		disqus.close();
	}

	private static void paging() {

		//For details visit http://disqus.com/api/docs/cursors/

		DisqusApplicationKeys keys = new DisqusApplicationKeys("...api_key...", "...secret_key...");
		DisqusApi disqus = new DisqusApi(keys);
		String cursor = null;
		do {
			DisqusPage page = new DisqusPage(cursor);
			DisqusResponse<List<DisqusPost>> response = disqus.posts().list(threadId, page);
			List<DisqusPost> posts = response.getResponse();
			for (DisqusPost post : posts) {
				//process post...
			}
			cursor = response.getCursor().getNext();
		} while (cursor != null);
		disqus.close();
	}

	private static void join() {

		DisqusApplicationKeys keys = new DisqusApplicationKeys("...api_key...", "...secret_key...");
		DisqusApi disqus = new DisqusApi(keys);
		//Get basic thread details
		DisqusResponse<DisqusThread> slimResponse = disqus.threads().details(threadId);
		//Field forum is just a String with Forum name
		assertThat(slimResponse.getResponse().getForum()).isInstanceOf(String.class);

		//Use addRelated(...) to tell API to join Forum details into returned Thread object 
		DisqusResponse<DisqusThread> joinedResponse = disqus.threads().details(threadId, Related.forum);
		//Field forum is now Bean with many fields
		assertThat(joinedResponse.getResponse().getForum()).isInstanceOf(DisqusForum.class);
		disqus.close();
	}

	/**
	 * For details visit http://disqus.com/api/docs/auth/
	 * 
	 */
	private static void authentication() {

		//While reading or listing API operations does not usually need authentication, writing calls must be authenticated

		//Authenticating as the Account Owner

		//Application access_token can be used when creating Disqus API client optionaly
		DisqusApplicationKeys keys = new DisqusApplicationKeys("...api_key...", "...secret_key...", "...access_token...");
		DisqusApi disqus = new DisqusApi(keys);
		//Application identity (Account of the user who owns Application) is used to create post
		DisqusResponse<DisqusPost> response = disqus.posts().createBuilder(threadId, "Hello world " + new Date()).execute();

		String yourCallbackUrl = "http://www.changeme.com/disqus_will_redirect_user_here_after_login";

		//You can use OauthAuthenticator to construct correct calls 
		OAuth2 oauth = new OAuth2Builder().setAuthorizationUrl("/api/oauth/2.0/authorize/")
				.setTokenEndpoint(disqus.getSender(), "/api/oauth/2.0/access_token/").setClientId(keys.getApiKey())
				.setClientSecret(keys.getApiSecret()).setRedirectUri(yourCallbackUrl).build();

		//Server-Side OAuth

		String disqusOauthUrl = oauth.getAuthorizationUrl("read,write", "some-random-to-check");
		//Use HttpServletResponse to redirect user to Disqus Login page - servletResponse.sendRedirect(disqusOauthUrl);

		//If the user Logs in successfully on Disqus site, he will redirected back to the yourCallbackUrl with code http parameter
		String code = "Get me from the HttpServletRequest..."; //servletRequest.getParameter("code")
		//Call Disqus to convert "code" into "token" and possibly store returned TokenResponse in HttpSession 
		TokenResponse tokenResponse = oauth.access(code).get(TokenResponse.class);
		//Use access_token to authenticate calls as user 
		String userAccessToken = tokenResponse.getAccess_token();
		//User identity is used to create post
		disqus.posts().create(Identity.access(userAccessToken), threadId, "Hello world " + new Date(), null);

		//Single Sign-On Authentication

		//This is available only to premium accounts
		SsoAuthData ssoauth = new SsoAuthData("custom-12345-id", "Firstname", "Surname");
		//SSO User identity is used to create post
		disqus.posts().create(ssoauth, keys.getApiSecret(), threadId, "Hello world " + new Date(), null);

		disqus.close();
	}
}

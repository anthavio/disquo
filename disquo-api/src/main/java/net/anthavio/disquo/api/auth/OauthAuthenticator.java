package net.anthavio.disquo.api.auth;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusServerException;
import net.anthavio.disquo.api.response.TokenResponse;
import net.anthavio.httl.HttlSender.Parameters;
import net.anthavio.httl.HttlRequest;
import net.anthavio.httl.HttlResponse;
import net.anthavio.httl.util.Cutils;
import net.anthavio.httl.util.HttpHeaderUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * @author martin.vanek
 * 
 */
public class OauthAuthenticator {

	private static final String URL_ACCESS_TOKEN = "/api/oauth/2.0/access_token/";

	private final String publicKey;

	private final String secretKey;

	private final Disqus disqus;

	public OauthAuthenticator(Disqus disqus) {
		if (disqus == null) {
			throw new NullArgumentException("disqus");
		}
		this.disqus = disqus;
		this.publicKey = disqus.getApplicationKeys().getApiKey();
		this.secretKey = disqus.getApplicationKeys().getApiSecret();
	}

	public String getOauthRequestUrl(String callbackUrl) {
		if (StringUtils.isBlank(callbackUrl)) {
			throw new IllegalArgumentException("Blank callback url");
		}
		if (callbackUrl.startsWith("http://") == false) {
			throw new IllegalArgumentException("callback url must start with http://");
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", this.publicKey));
		params.add(new BasicNameValuePair("scope", "read,write"));
		params.add(new BasicNameValuePair("response_type", "code"));
		params.add(new BasicNameValuePair("redirect_uri", callbackUrl));

		String query = URLEncodedUtils.format(params, "utf-8");
		return "https://disqus.com/api/oauth/2.0/authorize/?" + query;
	}

	/**
	 * OAuth code -> Disqus access_token
	 * 
	 * POST https://disqus.com/api/oauth/2.0/access_token/?
	 * grant_type=authorization_code& client_id=PUBLIC_KEY&
	 * client_secret=SECRET_KEY&
	 * redirect_uri=http://www.example.com/oauth_redirect& code=CODE
	 * 
	 * Response: Json with access_token
	 * (http://disqus.com/api/docs/auth/#response)
	 * 
	 * { "access_token": "c2d06abacfbb40179e47f62f06546ea9", "refresh_token":
	 * "9182211bf2f746a4b5c5b1e3766443d6", "expires_in": 2592000, "username":
	 * "batman" "user_id": "947103743" }
	 */
	public TokenResponse getAccessTokenForCode(String redirectUrl, String code) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("grant_type", "authorization_code");
		params.put("client_id", this.publicKey);
		params.put("client_secret", this.secretKey);
		params.put("redirect_uri", redirectUrl);
		params.put("code", code);

		HttlResponse response = null;
		try {
			HttlRequest request = this.disqus.getSender().POST(URL_ACCESS_TOKEN).params(params)
					.header("Content-Type", "application/json").build();
			response = this.disqus.getSender().execute(request);
			if (response.getHttpStatusCode() != 200) {
				throw new DisqusServerException(response.getHttpStatusCode(), 0, "Oauth Error: "
						+ HttpHeaderUtil.readAsString(response));
			}
			InputStream stream = response.getStream();
			return this.disqus.parse(stream, TokenResponse.class);
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		} finally {
			Cutils.close(response);
		}
	}

	/**
	 * Documentation http://disqus.com/api/docs/auth/
	 * 
	 * Redirect URL hostname must be in Applications > Settings > Domains
	 * 
	 * GET https://disqus.com/api/oauth/2.0/authorize/? client_id=PUBLIC_KEY&
	 * scope=read,write& response_type=code&
	 * redirect_uri=http://www.example.com/
	 * oauth_redirect?customparam=customvalue
	 * 
	 * Opens Disquis page "Xyz is asking permission to access your account"
	 * Allow/No thanks Or Disqus Login Page
	 * 
	 * Allow -> POST https://disqus.com/api/oauth/2.0/grant/ username=ususususus
	 * password=papapapapa csrfmiddlewaretoken=ctctctctctctct redirect to
	 * redirect_uri?code=zxzxzxzxzxzxzx
	 * 
	 * No thanks -> POST https://disqus.com/api/oauth/2.0/deny/
	 * csrfmiddlewaretoken=rtatartaratratrta redirect to
	 * redirect_uri?error=access_denied
	 * 
	 */
	/*
	public void get_oauth_code(String redirectUrl, String username, String password) {

		GetRequest request = new GetRequest(getOauthRequestUrl(redirectUrl));
		String loginPage = this.disqus.getSender().extract(request, String.class).getBody();
		System.out.println(loginPage);
		Pattern pattern = Pattern.compile("<input type='hidden' name='csrfmiddlewaretoken' value='([^\"]*)' />");
		Matcher matcher = pattern.matcher(loginPage.toString());
		String csrfToken;
		if (matcher.find()) {
			csrfToken = matcher.group(1);
		} else {
			// System.out.println(loginPage);
			throw new IllegalStateException("Cannot find csrfmiddlewaretoken");
		}

		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("csrfmiddlewaretoken", csrfToken);
		params2.put("username", username);
		params2.put("password", password);
		ExtractedBodyResponse<String> extracted = this.disqus.getSender().POST("/api/oauth/2.0/grant/").parameters(params2)
				.extract(String.class);
		System.out.println(extracted.getBody());

		// Redirect with "code" parameter in url shoud be obtained
	}
	*/

	/**
	 * http://disqus.com/api/docs/auth/
	 * 
	 * POST https://disqus.com/api/oauth/2.0/access_token/? grant_type=password&
	 * client_secret=SECRET_KEY& client_id=PUBLIC_KEY& scope=read,write
	 * 
	 * Response: Json with access_token
	 * (http://disqus.com/api/docs/auth/#response)
	 * 
	 * { "access_token": "c2d06abacfbb40179e47f62f06546ea9", "refresh_token":
	 * "9182211bf2f746a4b5c5b1e3766443d6", "expires_in": 2592000, "username":
	 * "batman" "user_id": "947103743" }
	 */
	public TokenResponse getAccessTokenForUser(String username, String password) {
		Parameters params = new Parameters();
		params.add("grant_type", "password");
		params.add("client_id", this.publicKey);
		params.add("client_secret", this.secretKey);
		params.add("scope", "read,write");

		String baseAuth = username + ":" + password;
		String base64password = new String(Base64.encodeBase64(baseAuth.getBytes(Charset.forName("utf-8"))));
		HttlResponse response = null;
		try {
			response = this.disqus.getSender().POST(URL_ACCESS_TOKEN).header("Authorization", "Basic " + base64password)
					.execute();
			InputStream stream = response.getStream();
			if (response.getHttpStatusCode() == 200) {
				return this.disqus.parse(stream, TokenResponse.class);
			} else {
				throw new DisqusServerException(response.getHttpStatusCode(), -1, HttpHeaderUtil.readAsString(response));
			}
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		} finally {
			Cutils.close(response);
		}

		/*
		 * HttpPost httpPost = new HttpPost("/api/oauth/2.0/access_token/");
		 * //single request authentication UsernamePasswordCredentials
		 * credentials = new UsernamePasswordCredentials(username, password);
		 * Header header = BasicScheme.authenticate(credentials, "US-ASCII",
		 * false); header.getName(); header.getValue();
		 * httpPost.addHeader(BasicScheme.authenticate(credentials, "US-ASCII",
		 * false)); httpPost.setEntity(new UrlEncodedFormEntity(params,
		 * Charset.forName("utf-8"))); HttpResponse response =
		 * disqus.call(httpPost);
		 * 
		 * String string = EntityUtils.toString(response.getEntity());
		 * System.out.println(string);
		 */
	}

	/**
	 * Refreshing OAuth Tokens
	 * 
	 * You will need to obtain a new access_token after expires_in. To do this,
	 * you will need to hit the authorize endpoint once again, but with a
	 * different set of parameters:
	 */
	public TokenResponse access_token_refresh(String refresh_token) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("grant_type", "password");
		params.put("client_id", this.publicKey);
		params.put("client_secret", this.secretKey);
		params.put("refresh_token", refresh_token);

		InputStream stream = null;
		try {
			HttlResponse response = disqus.getSender().POST(URL_ACCESS_TOKEN).params(params).execute();
			if (response.getHttpStatusCode() != 200) {
				throw new DisqusServerException(response.getHttpStatusCode(), 0, "Some Oauth Error");
			}
			stream = response.getStream();
			return this.disqus.parse(stream, TokenResponse.class);
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		} finally {
			Cutils.close(stream);
		}
	}

}

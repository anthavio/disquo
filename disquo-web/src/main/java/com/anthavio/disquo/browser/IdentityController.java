package com.anthavio.disquo.browser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.anthavio.disquo.api.auth.OauthAuthenticator;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusUser;
import com.anthavio.disquo.api.response.TokenResponse;
import com.anthavio.disquo.api.users.UserDetailsMethod;
import com.anthavio.disquo.browser.UserIdentity.Type;

/**
 * 
 * @author martin.vanek
 *
 */
@Controller
//@SessionAttributes({ AuthController.USER_IDENTITY })
public class IdentityController extends ControllerBase {

	static final String USER_IDENTITY = "USER_IDENTITY";

	private OauthAuthenticator oauth;

	@PostConstruct
	public void init() {
		this.oauth = new OauthAuthenticator(this.disqus);
	}

	public String getSsoRemoteAuth(SsoAuthData sso) {
		String remote_auth_s3 = SsoAuthenticator.remote_auth_s3(sso, this.disqus.getApplicationKeys().getSecretKey());
		return remote_auth_s3;
	}

	public String getApiKey() {
		return this.disqus.getApplicationKeys().getApiKey();
	}

	@RequestMapping(value = "identity", method = RequestMethod.GET)
	public ModelAndView viewIdentity(@ModelAttribute(USER_IDENTITY) UserIdentity identity) {
		ModelAndView modelAndView = new ModelAndView("disqus/identity");
		return modelAndView;
	}

	@RequestMapping(value = "identity", method = RequestMethod.POST)
	public ModelAndView postIdentity(@ModelAttribute(USER_IDENTITY) UserIdentity identity, BindingResult binding) {
		ModelAndView modelAndView = new ModelAndView("redirect:/disqus/identity");
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			return null;
		}
		if (identity.getType() == null) {
			disqus.setEnforceDefaultToken(false);
			disqus.setDefaultToken(null);
			identity.setDisqusUser(null);
		} else {
			//try new identity on this method only
			UserDetailsMethod dmethod = disqus.users().details();
			disqus.setEnforceDefaultToken(false);
			String token;
			if (identity.getType() == Type.sso) {
				token = SsoAuthenticator.remote_auth_s3(identity.getSso(), disqus.getApplicationKeys().getSecretKey());
				dmethod.setRemoteAuth(token);
			} else if (identity.getType() == Type.oauth) {
				token = identity.getOauth().getAccess_token();
				dmethod.setAccessToken(token);
			} else if (identity.getType() == Type.application) {
				token = identity.getApplicationToken();
				dmethod.setAccessToken(token);
			} else {
				throw new IllegalArgumentException("Unsupported type " + identity.getType());
			}

			DisqusResponse<DisqusUser> response = dmethod.execute();
			identity.setDisqusUser(response.getResponse());
			//if disqus call succeded, make this identity default
			disqus.setDefaultToken(token);
			disqus.setEnforceDefaultToken(true);
		}
		return modelAndView;
	}

	@RequestMapping(value = "oauth/request", method = RequestMethod.GET)
	public void oauthRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String callbackUrl = getCallbackURL(request).toString();
		String oauthUrl = this.oauth.getOauthRequestUrl(callbackUrl);
		//redirect user to Disqus authorization/login page
		response.sendRedirect(oauthUrl);
	}

	/**
	 * Disqus will redirect to this url
	 */
	@RequestMapping(value = "oauth/callback", method = RequestMethod.GET)
	public String oauthCallback(@RequestParam(required = false) String code,
			@RequestParam(required = false) String error, HttpServletRequest request,
			@ModelAttribute(USER_IDENTITY) UserIdentity identity) {

		if (StringUtils.isNotBlank(error)) {
			System.err.println("error " + error);
			//error=access_denied;
		}
		if (StringUtils.isNotBlank(code)) {
			URL callbackUrl = getCallbackURL(request);
			TokenResponse tokenResponse = this.oauth.getAccessToken(callbackUrl.toString(), code);
			identity.setOauth(tokenResponse);
		}
		return "redirect:/disqus/identity";
	}

	private URL getCallbackURL(HttpServletRequest request) {
		try {
			URL requestUrl = new URL(request.getRequestURL().toString());

			URL oauthCallbackUrl = new URL(requestUrl.getProtocol(), requestUrl.getHost(), requestUrl.getPort(),
					request.getContextPath() + "/disqus/oauth/callback");
			return oauthCallbackUrl;
		} catch (MalformedURLException mux) {
			throw new UnhandledException(mux);
		}

	}

}

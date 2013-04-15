package com.anthavio.disquo.browser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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

import com.anthavio.disquo.api.DisqusApplicationKeys;
import com.anthavio.disquo.api.auth.OauthAuthenticator;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusUser;
import com.anthavio.disquo.api.response.TokenResponse;
import com.anthavio.disquo.api.users.UserDetailsMethod;
import com.anthavio.disquo.browser.UserIdentityForm.Type;
import com.anthavio.httl.util.Cutils;

/**
 * 
 * @author martin.vanek
 *
 */
@Controller
public class SettingsController extends ControllerBase {

	//static final String USER_IDENTITY = "USER_IDENTITY";

	@RequestMapping(value = "appkeys", method = RequestMethod.GET)
	public ModelAndView appkeys() {
		ModelAndView modelAndView = new ModelAndView("disqus/appkeys");
		DisqusApplicationKeys appKeys;

		if (session.getDriver() != null) {
			appKeys = session.getDriver().getApplicationKeys();
		} else {
			appKeys = new DisqusApplicationKeys();
		}
		modelAndView.addObject("AppKeysForm", appKeys);
		return modelAndView;
	}

	@RequestMapping(value = "appkeys", method = RequestMethod.POST)
	public ModelAndView appkeys(@ModelAttribute("AppKeysForm") DisqusApplicationKeys appKeys, BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("disqus/appkeys");

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			return modelAndView;
		}

		if (Cutils.isBlank(appKeys.getApiKey())) {
			return modelAndView; //empty key is not an answer!
		}

		session.setApplicationKeys(appKeys); //also initializes Disqus driver

		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "identity", method = RequestMethod.GET)
	public ModelAndView viewIdentity() {
		ModelAndView modelAndView = new ModelAndView("disqus/identity");
		UserIdentityForm identity = session.getIdentity();
		if (identity == null) {
			identity = new UserIdentityForm();
			SsoAuthData sso = new SsoAuthData("654321_id_123456", "John Doe", "example@example.com");
			identity.setSso(sso);
			if (session.getApplicationKeys() != null) {
				String accessToken = session.getApplicationKeys().getAccessToken();
				identity.setApplicationToken(accessToken);
			}
			//identity.setType(Type.sso);
			session.setIdentity(identity);
		}
		modelAndView.addObject("identity", identity);
		return modelAndView;
	}

	@RequestMapping(value = "identity", method = RequestMethod.POST)
	public ModelAndView postIdentity(@ModelAttribute("identity") UserIdentityForm identity, BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("redirect:/disqus/identity");
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			return null;
		}

		if (identity.getType() == null) {
			session.getDriver().setEnforceDefaultToken(false);
			session.getDriver().setDefaultToken(null);
			session.setIdentity(null);
		} else {
			//try new identity on this method only
			UserDetailsMethod dmethod = session.getDriver().users().details();
			session.getDriver().setEnforceDefaultToken(false);
			String token;
			if (identity.getType() == Type.sso) {
				token = SsoAuthenticator.remote_auth_s3(identity.getSso(), session.getDriver().getApplicationKeys()
						.getApiSecret());
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
			//identity.setDisqusUser(response.getResponse());
			session.setIdentity(identity);
			session.getIdentity().setDisqusUser(response.getResponse());
			//if disqus call succeded, make this identity default
			session.getDriver().setDefaultToken(token);
			session.getDriver().setEnforceDefaultToken(true);
		}
		return modelAndView;
	}

	@RequestMapping(value = "oauth/request", method = RequestMethod.GET)
	public void oauthRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String callbackUrl = getCallbackURL(request).toString();
		OauthAuthenticator oauth = new OauthAuthenticator(session.getDriver());
		String oauthUrl = oauth.getOauthRequestUrl(callbackUrl);
		//redirect user to Disqus authorization/login page
		response.sendRedirect(oauthUrl);
	}

	/**
	 * Disqus will redirect to this url
	 */
	@RequestMapping(value = "oauth/callback", method = RequestMethod.GET)
	public String oauthCallback(@RequestParam(required = false) String code,
			@RequestParam(required = false) String error, HttpServletRequest request) {

		if (StringUtils.isNotBlank(error)) {
			System.err.println("error " + error);
			//error=access_denied;
		}
		if (StringUtils.isNotBlank(code)) {
			URL callbackUrl = getCallbackURL(request);
			OauthAuthenticator oauth = new OauthAuthenticator(session.getDriver());
			TokenResponse tokenResponse = oauth.getAccessToken(callbackUrl.toString(), code);
			session.getIdentity().setOauth(tokenResponse);
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

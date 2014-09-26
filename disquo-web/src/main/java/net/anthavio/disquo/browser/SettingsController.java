package net.anthavio.disquo.browser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApplicationKeys;
import net.anthavio.disquo.api.auth.DisqusOAuth2;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.auth.SsoAuthenticator;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.disquo.api.response.TokenResponse;
import net.anthavio.disquo.browser.UserIdentityForm.Type;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

		if (StringUtils.isBlank(appKeys.getApiKey())) {
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
	public ModelAndView postIdentity(@ModelAttribute("identity") UserIdentityForm identityForm, BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("redirect:/disqus/identity");
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			return null;
		}

		Identity identity;
		if (identityForm.getType() == null) {
			session.getDriver().setIdentity(null);
			session.setIdentity(null);
		} else {
			//try new identity on this method only
			if (identityForm.getType() == Type.sso) {
				String remote_token = SsoAuthenticator.remote_auth_s3(identityForm.getSso(), session.getDriver()
						.getApplicationKeys().getApiSecret());
				identity = Identity.remote(remote_token);
			} else if (identityForm.getType() == Type.oauth) {
				String access_token = identityForm.getOauth().getAccess_token();
				identity = Identity.access(access_token);
			} else if (identityForm.getType() == Type.application) {
				identity = Identity.access(identityForm.getApplicationToken());
			} else {
				throw new IllegalArgumentException("Unsupported type " + identityForm.getType());
			}
			//check identity with call
			DisqusResponse<DisqusUser> response = session.getDriver().users().details(identity);

			//if disqus call succeded, make this identity default
			session.setIdentity(identityForm);
			session.getIdentity().setDisqusUser(response.getResponse());
			session.getDriver().setIdentity(identity);
		}
		return modelAndView;
	}

	@RequestMapping(value = "oauth/request", method = RequestMethod.GET)
	public void oauthRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String callbackUrl = getCallbackURL(request).toString();
		DisqusOAuth2 oAuth2 = new DisqusOAuth2(session.getDriver(), callbackUrl);
		String oauthUrl = oAuth2.getOAuth2().getAuthorizationUrl("read,write", "whtever");
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
			String callbackUrl = getCallbackURL(request).toString();
			DisqusOAuth2 oAuth2 = new DisqusOAuth2(session.getDriver(), callbackUrl);
			TokenResponse tokenResponse = oAuth2.getOAuth2().access(code).get(TokenResponse.class);
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

package com.anthavio.disquo.simulator.rest;

import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.anthavio.disquo.api.ArgumentConfig;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.DisqusMethodConfig.Auth;
import com.anthavio.disquo.api.DisqusServerException;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusUser;
import com.anthavio.disquo.simulator.CopyServant;
import com.anthavio.disquo.simulator.JaxRsUtil;
import com.anthavio.disquo.simulator.SearchService;
import com.anthavio.disquo.simulator.StoreService;
import com.anthavio.disquo.simulator.StoreService.DisqusApplication;

public abstract class BaseRestEndpoint {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected StoreService store;

	@Autowired
	protected SearchService search;

	@Autowired
	protected CopyServant copier;

	protected DisqusApplication validate(MultivaluedMap<String, String> params, DisqusMethodConfig mconfig) {
		//validate parameters
		ArgumentConfig[] arguments = mconfig.getArguments();
		Set<String> names = params.keySet();
		for (String name : names) {
			ArgumentConfig aconfig = getArgConfig(arguments, name);
			if (aconfig == null) {
				throw new DisqusServerException(400, 2, "Invalid argument, '" + name + "'");
			}
		}
		//validate authentication
		if (mconfig.getAuthentication() == Auth.REQUIRED) {
			String remote_auth = JaxRsUtil.OptStr(params, "remote_auth");
			String access_token = JaxRsUtil.OptStr(params, "access_token");
			if (remote_auth == null && access_token == null) {
				throw new DisqusServerException(401, 4, "You must provide an authenticated user for this method");
			}
		}
		return getApplication(params);
	}

	private ArgumentConfig getArgConfig(ArgumentConfig[] arguments, String name) {
		for (ArgumentConfig aconfig : arguments) {
			if (aconfig.getName().equals(name)) {
				return aconfig;
			}
		}
		return null;
	}

	/**
	 * This method always returns something or throws Exception
	 * 
	 * Fallback to application user is other is not found is allowed. 
	 * All methods except create post that needs real user.
	 * 
	 */
	protected DisqusUser getAuthor(MultivaluedMap<String, String> form, DisqusMethodConfig mconfig,
			DisqusApplication application) {
		return getAuthor(form, mconfig, application, true);
	}

	/**
	 * Can return null
	 */
	protected DisqusUser getAuthor(MultivaluedMap<String, String> form, DisqusMethodConfig methodConfig,
			DisqusApplication application, boolean appUserFallback) {
		DisqusUser author = null;
		Auth auth = methodConfig.getAuthentication();
		if (auth == Auth.REQUIRED || auth == Auth.ALLOWED) {
			String access_token = JaxRsUtil.OptStr(form, "access_token");
			String remote_auth = JaxRsUtil.OptStr(form, "remote_auth");
			if (remote_auth != null) {
				// sso user
				SsoAuthData authData;
				try {
					authData = SsoAuthenticator.decode_remote_auth(remote_auth, application.getKeys().getApiSecret());
				} catch (Exception x) {
					return null; //skip the cheater
				}
				author = this.store.getUserByEmail(authData.getEmail());
				if (author == null) {
					author = this.store.createUser(authData.getEmail(), authData.getFullName(), null);
				}

			} else if (StringUtils.isNotBlank(access_token)) {
				// api user or oauth user
				author = this.store.getUserByAccessToken(access_token);
				if (author == null) {
					throw new DisqusServerException(403, 18, "Access token is not valid");
				}
			}
		}
		if (author == null) {
			if (auth == Auth.REQUIRED) {
				throw new DisqusServerException(401, 4, "You must provide an authenticated user for this method");

			} else if (auth == Auth.ALLOWED) {
				if (author == null && appUserFallback) {
					author = application.getUser(); //use application user then...
				}
			}
		}

		return author;
	}

	protected DisqusApplication getApplication(MultivaluedMap<String, String> form) {
		String api_key = JaxRsUtil.OptStr(form, "api_key");
		String secret_key = JaxRsUtil.OptStr(form, "secret_key");
		DisqusApplication application = null;
		if (api_key != null) {
			application = this.store.getApplicationByApiKey(api_key);
		} else if (secret_key != null) {
			application = this.store.getApplicationBySecretKey(secret_key);
		}

		if (application == null) {
			throw new DisqusServerException(403, 5, "Invalid API key");
		} else {
			return application;
		}
	}

	protected Response getResponse(DisqusResponse<?> dr) {
		Response response = Response.status(200).type(MediaType.APPLICATION_JSON).entity(dr).build();
		return response;
	}

	protected Response getErrorResponse(DisqusServerException dsx) {
		this.logger.warn("Semihandled exception", dsx);
		DisqusResponse<String> dr = new DisqusResponse<String>(dsx.getDisqusCode(), dsx.getMessage(), null);
		return Response.status(dsx.getHttpCode()).type(MediaType.APPLICATION_JSON).entity(dr).build();
	}

	protected Response getErrorResponse(Exception x) {
		if (x instanceof DisqusServerException) {
			return getErrorResponse((DisqusServerException) x);
		}
		this.logger.warn("Unhandled exception", x);
		DisqusResponse<String> dr = new DisqusResponse<String>(15, x.getMessage(), null);
		return Response.status(500).type(MediaType.APPLICATION_JSON).entity(dr).build();
	}

}

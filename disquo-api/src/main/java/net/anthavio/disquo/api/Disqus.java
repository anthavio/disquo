package net.anthavio.disquo.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.anthavio.disquo.api.DisqusMethod.Parameter;
import net.anthavio.disquo.api.DisqusMethodConfig.Http;
import net.anthavio.disquo.api.applications.DisqusApplicationsGroup;
import net.anthavio.disquo.api.auth.SsoAuthenticator;
import net.anthavio.disquo.api.blacklists.DisqusBlacklistsGroup;
import net.anthavio.disquo.api.category.DisqusCategoryGroup;
import net.anthavio.disquo.api.exports.DisqusExportsGroup;
import net.anthavio.disquo.api.forums.DisqusForumsGroup;
import net.anthavio.disquo.api.imports.DisqusImportsGroup;
import net.anthavio.disquo.api.posts.DisqusPostsGroup;
import net.anthavio.disquo.api.reactions.DisqusReactionsGroup;
import net.anthavio.disquo.api.reports.DisqusReportsGroup;
import net.anthavio.disquo.api.response.DisqusCursor;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusFilterDeserializer;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusForumDeserializer;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusPostDeserializer;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusThreadDeserializer;
import net.anthavio.disquo.api.threads.DisqusThreadsGroup;
import net.anthavio.disquo.api.trends.DisqusTrendsGroup;
import net.anthavio.disquo.api.users.DisqusUsersGroup;
import net.anthavio.disquo.api.whitelists.DisqusWhitelistsGroup;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.anthavio.httl.GetRequest;
import net.anthavio.httl.HttpClient4Sender;
import net.anthavio.httl.HttpSender;
import net.anthavio.httl.HttpSender.Multival;
import net.anthavio.httl.util.Cutils;
import net.anthavio.httl.util.HttpHeaderUtil;
import net.anthavio.httl.PostRequest;
import net.anthavio.httl.SenderRequest;
import net.anthavio.httl.SenderResponse;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Disqus {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	private final String apiPath;

	private final DisqusApplicationKeys keys;

	private final HttpSender sender;

	protected final ObjectMapper mapper;

	private String defaultToken = null; //access_token or remote_token

	private boolean defaultTokenIsRemoteToken; //internal 

	private boolean enforceDefaultToken = false; //use defaultToken all the time

	private boolean strictParameters = true; //verify every parameter for it's validity before call

	public Disqus(DisqusApplicationKeys keys) {
		this(keys, null, null);
	}

	public Disqus(DisqusApplicationKeys keys, String apiUrl) {
		this(keys, apiUrl, null);
	}

	public Disqus(DisqusApplicationKeys keys, HttpSender sender) {
		this(keys, null, sender);
	}

	public Disqus(DisqusApplicationKeys keys, String apiUrl, HttpSender sender) {
		if (keys == null) {
			throw new NullArgumentException("keys");
		}
		this.keys = keys;

		URL url;
		try {
			if (apiUrl == null) {
				apiUrl = "https://disqus.com/api/3.0";
			}
			url = new URL(apiUrl);
			this.apiPath = url.getPath();

		} catch (MalformedURLException mux) {
			throw new IllegalArgumentException("URL is invalid " + apiUrl, mux);
		}

		if (sender == null) {
			String siteUrl = url.getProtocol() + "://" + url.getHost();
			if (url.getPort() != -1) {
				siteUrl += ":" + url.getPort();
			}
			this.sender = new HttpClient4Sender(siteUrl);
		} else {
			this.sender = sender;
		}

		this.mapper = initJackson();
	}

	public void close() {
		try {
			this.sender.close();
		} catch (Exception x) {
			//ignore
		}
	}

	public HttpSender getSender() {
		return this.sender;
	}

	public DisqusApplicationKeys getApplicationKeys() {
		return this.keys;
	}

	public String getDefaultToken() {
		return this.defaultToken;
	}

	/**
	 * Makes application token to be default authentication token
	 */
	public void setUseApplicationToken(boolean value) {
		if (value) {
			String appToken = this.keys.getAccessToken();
			if (StringUtils.isBlank(appToken)) {
				throw new IllegalStateException("Application token is not set");
			} else {
				this.defaultToken = appToken;
				this.defaultTokenIsRemoteToken = false;
			}
		} else {//value==false
			this.defaultToken = null;
		}
	}

	/**
	 * Enforces usage of default authentication token over the token provided by resource
	 * Default token must be set before calling this method
	 */
	public void setEnforceDefaultToken(boolean value) {
		if (value && StringUtils.isBlank(this.defaultToken)) {
			throw new IllegalStateException("Default token is not set");
		}
		this.enforceDefaultToken = value;
	}

	/**
	 * Sets @param token as default authentication token for methods that needs to be authenticated. 
	 * This default token is used only when explicit authentication is NOT present in resource
	 * 
	 * If @parem token is null or empty string, tehn default token is erased
	 * 
	 * @param token - remote_token or access_token
	 */
	public void setDefaultToken(String token) {
		if (StringUtils.isBlank(token)) {
			this.defaultToken = null;
			return;
		}
		this.defaultToken = token;
		try {
			//try if it is remote_token
			SsoAuthenticator.decode_remote_auth(token, this.keys.getApiSecret());
			this.defaultTokenIsRemoteToken = true;
		} catch (IllegalArgumentException iax) {
			this.defaultTokenIsRemoteToken = false;
		}
	}

	/**
	 * If parameter validation before call to Disqus is performed.
	 */
	public boolean getStrictParameters() {
		return this.strictParameters;
	}

	/**
	 * Enforces parameter validation before call to Disqus is performed.
	 */
	public void setStrictParameters(boolean value) {
		this.strictParameters = value;
	}

	public DisqusCategoryGroup category() {
		return new DisqusCategoryGroup(this);
	}

	public DisqusPostsGroup posts() {
		return new DisqusPostsGroup(this);
	}

	public DisqusThreadsGroup threads() {
		return new DisqusThreadsGroup(this);
	}

	public DisqusForumsGroup forums() {
		return new DisqusForumsGroup(this);
	}

	public DisqusReactionsGroup reactions() {
		return new DisqusReactionsGroup(this);
	}

	public DisqusUsersGroup users() {
		return new DisqusUsersGroup(this);
	}

	public DisqusBlacklistsGroup blacklists() {
		return new DisqusBlacklistsGroup(this);
	}

	public DisqusWhitelistsGroup whitelists() {
		return new DisqusWhitelistsGroup(this);
	}

	public DisqusApplicationsGroup applications() {
		return new DisqusApplicationsGroup(this);
	}

	public DisqusImportsGroup imports() {
		return new DisqusImportsGroup(this);
	}

	public DisqusExportsGroup exports() {
		return new DisqusExportsGroup(this);
	}

	public DisqusReportsGroup reports() {
		return new DisqusReportsGroup(this);
	}

	public DisqusTrendsGroup trends() {
		return new DisqusTrendsGroup(this);
	}

	/**
	 * Calls Disqus API and parses response. Response is closed automaticaly. 
	 * DisqusServerException is throw when non OK response is returned.
	 * DisqusException is thrown when any other Exception is encountered.
	 * 
	 */
	protected <B extends DisqusMethod<?, T>, T> DisqusResponse<T> callApi(DisqusMethod<B, T> resource, Class<T> type) {
		try {
			InputStream stream = this.callApi(resource).getStream();
			try {
				return this.parseResponse(stream, type);
			} finally {
				Cutils.close(stream);
			}
		} catch (IOException iox) {
			throw new DisqusException(iox);
		}
	}

	/**
	 * Complete request (authentication token if needed) and call Disqus API
	 * 
	 * Caller is responsible for closing returned SenderResponse
	 */
	protected <B extends DisqusMethod<?, T>, T> SenderResponse callApi(DisqusMethod<B, T> resource) {
		DisqusMethodConfig config = resource.getConfig();
		boolean tokenSet = resource.isAuthenticated();

		if (this.enforceDefaultToken) {
			if (StringUtils.isBlank(this.defaultToken)) {
				throw new IllegalStateException("Default token is not set");
			}
			if (tokenSet) {
				this.logger.warn("Default token is used instead of explicit token");
			}
			if (this.defaultTokenIsRemoteToken) {
				resource.setRemoteAuth(this.defaultToken);
			} else {
				resource.setAccessToken(this.defaultToken);
			}

		} else {//token is NOT enforced

			switch (config.getAuthentication()) {
			case ALLOWED:
				if (tokenSet == false) {
					//XXX if Preferred method is SSO then throw exception
				}
			case REQUIRED:
				if (tokenSet == false) {
					if (this.defaultToken != null) {
						this.logger.debug("Default token is used for authentication");
						resource.setAccessToken(this.defaultToken);
					} else {
						this.logger.warn("User identity not found for " + resource);
					}
				}
				break;
			case NONE:
				//NONE means NONE
				break;
			}
		}
		String urlFragment = config.getUrlFragment();
		OutputFormat output = resource.getOutputFormat();
		urlFragment = urlFragment + "." + output;

		Http httpMethod = config.getHttpMethod();
		try {
			return this.callApi(httpMethod, urlFragment, resource.getParameters());
		} catch (IOException iox) {
			throw new DisqusException(iox);
		}
	}

	/**
	 * api_key parameter is added to and the http request is sent to disqus api rest endpoint.
	 * 
	 * Caller is responsible for closing returned SenderResponse
	 */
	protected SenderResponse callApi(Http method, String urlFragment, List<Parameter> params) throws IOException {
		if (StringUtils.isBlank(urlFragment)) {
			throw new IllegalArgumentException("Resource url is invalid: '" + urlFragment + "'");
		}

		if (params == null) {
			params = new LinkedList<Parameter>();
		}
		params.add(new Parameter("api_key", this.keys.getApiKey()));
		Multival parameters = new Multival();
		for (Parameter parameter : params) {
			parameters.add(parameter.getName(), parameter.getValue());
		}

		String urlPath = this.apiPath + urlFragment;

		SenderRequest request;
		if (method == Http.GET) {
			request = new GetRequest(urlPath).setParameters(parameters);
		} else {
			request = new PostRequest(urlPath).setParameters(parameters);
		}

		SenderResponse response = this.sender.execute(request);

		if (response.getHttpStatusCode() != 200) {
			throwException(response);
		}

		return response;
	}

	/**
	 * Hackish way to use internal Jackson ObjectMapper
	 */
	public <T> T parse(InputStream stream, Class<T> type) throws IOException {
		return this.mapper.readValue(stream, type);
	}

	/**
	 * Disqus returns highly "polymorphic" responses and this method deals with them
	 */
	public <T> DisqusResponse<T> parseResponse(InputStream inputStream, Class<T> valueType) throws IOException {
		if (inputStream == null) {
			throw new NullArgumentException("inputStream");
		}
		JsonFactory factory = this.mapper.getFactory();
		JsonParser parser = factory.createJsonParser(inputStream);
		JsonToken firstToken = parser.nextToken();
		if (firstToken != JsonToken.START_OBJECT) {
			throw new DisqusException("First token is '" + firstToken + "' instead of expected '" + JsonToken.START_OBJECT
					+ "'");
		}
		int code = -1;
		DisqusCursor cursor = null;
		Object response = null;
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String fieldname = parser.getCurrentName();
			JsonToken token = parser.nextToken();
			if ("code".equals(fieldname)) {
				code = Integer.parseInt(parser.getText());
				if (code != 0) {
					//? throw exception or only log warning
					this.logger.warn("Response code is " + code);
				}
			} else if ("cursor".equals(fieldname)) {
				cursor = parser.readValueAs(DisqusCursor.class);
			} else if ("response".equals(fieldname)) {

				if (token == JsonToken.START_ARRAY) {
					List<T> values = new ArrayList<T>();
					while (parser.nextToken() != JsonToken.END_ARRAY) {
						T item = parser.readValueAs(valueType);
						values.add(item);
					}
					response = values;
				} else if (token == JsonToken.START_OBJECT) {
					response = parser.readValueAs(valueType);

				} else if (token == JsonToken.VALUE_STRING) {
					response = parser.getText();
				} else {
					throw new DisqusException("Unexpected token: '" + token + "'");
				}
			} else {
				throw new DisqusException("Unexpected element: '" + fieldname + "'");
			}
		}
		return new DisqusResponse(code, response, cursor);
	}

	/**
	 * Parse Disqus JSON response and throw DisqusException
	 */
	private void throwException(SenderResponse response) throws DisqusException {
		String statusLine = response.getHttpStatusMessage() + " " + response.getHttpStatusMessage();
		String contentHeader = response.getFirstHeader("Content-Type");
		if (contentHeader == null || !contentHeader.startsWith("application/json")) {
			//Not a JSON response
			try {
				String errResponeTxt = HttpHeaderUtil.readAsString(response);
				throw new DisqusException(statusLine + "\n" + errResponeTxt);
			} catch (IOException iox) {
				logger.warn("Failed to read error resonse", iox);
			} finally {
				Cutils.close(response);
			}
			throw new DisqusException(statusLine);
		}

		InputStream stream = null;
		try {
			stream = response.getStream();
			if (stream != null) {
				Map<String, Object> jsonData = this.mapper.readValue(stream, Map.class);
				Integer code = (Integer) jsonData.get("code");
				String message = (String) jsonData.get("response");
				throw new DisqusServerException(response.getHttpStatusCode(), code, message);
			}
		} catch (IOException iox) {
			throw new DisqusException(statusLine, iox);
		} finally {
			Cutils.close(response);
		}
		throw new DisqusException(statusLine);
	}

	/**
	 * Initialize Jackson ObjectMapper to conform it Disqus way
	 */
	protected ObjectMapper initJackson() {
		SimpleModule module = new SimpleModule("DisqusModule");
		module.addDeserializer(DisqusForum.class, new DisqusForumDeserializer());
		module.addDeserializer(DisqusThread.class, new DisqusThreadDeserializer());
		module.addDeserializer(DisqusPost.class, new DisqusPostDeserializer());
		module.addDeserializer(DisqusFilter.class, new DisqusFilterDeserializer());

		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		module.addSerializer(Date.class, new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
					JsonProcessingException {
				String formattedDate = dateFormat.format(value);
				jgen.writeString(formattedDate);
			}
		});
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		mapper.setDateFormat(dateFormat);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * Access to internal Jackson ObjectMapper. Can be used to customize 
	 */
	public ObjectMapper getJacksonMapper() {
		return mapper;
	}

}

package com.anthavio.disquo.api;

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

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anthavio.disquo.api.DisqusMethod.Parameter;
import com.anthavio.disquo.api.DisqusMethodConfig.Http;
import com.anthavio.disquo.api.applications.DisqusApplicationsGroup;
import com.anthavio.disquo.api.auth.SsoAuthenticator;
import com.anthavio.disquo.api.blacklists.DisqusBlacklistsGroup;
import com.anthavio.disquo.api.category.DisqusCategoryGroup;
import com.anthavio.disquo.api.exports.DisqusExportsGroup;
import com.anthavio.disquo.api.forums.DisqusForumsGroup;
import com.anthavio.disquo.api.imports.DisqusImportsGroup;
import com.anthavio.disquo.api.posts.DisqusPostsGroup;
import com.anthavio.disquo.api.reactions.DisqusReactionsGroup;
import com.anthavio.disquo.api.reports.DisqusReportsGroup;
import com.anthavio.disquo.api.response.DisqusCursor;
import com.anthavio.disquo.api.response.DisqusFilter;
import com.anthavio.disquo.api.response.DisqusFilterDeserializer;
import com.anthavio.disquo.api.response.DisqusForum;
import com.anthavio.disquo.api.response.DisqusForumDeserializer;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusPostDeserializer;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.response.DisqusThreadDeserializer;
import com.anthavio.disquo.api.threads.DisqusThreadsGroup;
import com.anthavio.disquo.api.trends.DisqusTrendsGroup;
import com.anthavio.disquo.api.users.DisqusUsersGroup;
import com.anthavio.disquo.api.whitelists.DisqusWhitelistsGroup;
import com.anthavio.hatatitla.Cutils;
import com.anthavio.hatatitla.GetRequest;
import com.anthavio.hatatitla.HttpClient4Sender;
import com.anthavio.hatatitla.HttpSender;
import com.anthavio.hatatitla.HttpSender.Multival;
import com.anthavio.hatatitla.PostRequest;
import com.anthavio.hatatitla.SenderRequest;
import com.anthavio.hatatitla.SenderResponse;
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

	private final JsonFactory factory = new JsonFactory();

	protected final ObjectMapper mapper = new ObjectMapper(this.factory);

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

		initJackson();
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

	public void setEnforceDefaultToken(boolean value) {
		if (value && StringUtils.isBlank(this.defaultToken)) {
			throw new IllegalStateException("Default token is not set");
		}
		this.enforceDefaultToken = value;
	}

	public void setDefaultToken(String defaultToken) {
		if (StringUtils.isBlank(defaultToken)) {
			this.defaultToken = null;
			return;
		}

		this.defaultToken = defaultToken;
		try {
			SsoAuthenticator.decode_remote_auth(defaultToken, this.keys.getSecretKey());
			this.defaultTokenIsRemoteToken = true;
		} catch (IllegalArgumentException iax) {
			this.defaultTokenIsRemoteToken = false;
		}
	}

	public boolean getStrictParameters() {
		return this.strictParameters;
	}

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

	public <T> T parse(InputStream stream, Class<T> type) throws IOException {
		return this.mapper.readValue(stream, type);
	}

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

	private void throwException(SenderResponse response) {
		String statusLine = response.getHttpStatusMessage() + " " + response.getHttpStatusMessage();
		String contentHeader = response.getFirstHeader("Content-Type");
		if (!"application/json".equals(contentHeader)) {
			//Not a JSON response
			Cutils.close(response);
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

	private void initJackson() {
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
		this.mapper.registerModule(module);
		this.mapper.setDateFormat(dateFormat);
		//
		this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}

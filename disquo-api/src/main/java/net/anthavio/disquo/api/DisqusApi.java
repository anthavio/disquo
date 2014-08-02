package net.anthavio.disquo.api;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.anthavio.httl.HttlBuilderInterceptor;
import net.anthavio.httl.HttlExecutionChain;
import net.anthavio.httl.HttlExecutionInterceptor;
import net.anthavio.httl.HttlParameterSetter.ConfigurableParamSetter;
import net.anthavio.httl.HttlRequest;
import net.anthavio.httl.HttlRequestBuilders.HttlRequestBuilder;
import net.anthavio.httl.HttlResponse;
import net.anthavio.httl.HttlSender;
import net.anthavio.httl.SenderBuilder;
import net.anthavio.httl.api.HttlApiBuilder;
import net.anthavio.httl.impl.HttpUrlConfig;
import net.anthavio.httl.inout.Jackson2Unmarshaller;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Client driver based on ApiBuilder
 * 
 * @author martin.vanek
 *
 */
public class DisqusApi implements Closeable {

	private final DisqusApplicationKeys keys;

	private final HttlSender sender;

	protected final ObjectMapper mapper;

	private final ApiApplications appsApi;

	private final ApiThreads threadsApi;

	private final ApiCategories categoryApi;

	private final ApiWhitelists whitelistsApi;

	private final ApiBlacklists blacklistsApi;

	private final ApiPosts postsApi;

	public DisqusApi(DisqusApplicationKeys keys) {
		this(keys, null, null);
	}

	public DisqusApi(DisqusApplicationKeys keys, String apiUrl) {
		this(keys, apiUrl, null);
	}

	public DisqusApi(DisqusApplicationKeys keys, SenderBuilder config) {
		this(keys, null, config);
	}

	public DisqusApi(final DisqusApplicationKeys keys, String apiUrl, SenderBuilder config) {
		if (keys == null) {
			throw new IllegalArgumentException("Null DisqusApplicationKeys");
		}
		this.keys = keys;

		URL url;
		try {
			if (apiUrl == null) {
				apiUrl = "https://disqus.com/api/3.0";
			}
			url = new URL(apiUrl);

		} catch (MalformedURLException mux) {
			throw new IllegalArgumentException("URL is invalid " + apiUrl, mux);
		}

		if (config == null) {
			String siteUrl = url.getProtocol() + "://" + url.getHost();
			if (url.getPort() != -1) {
				siteUrl += ":" + url.getPort();
			}
			siteUrl += url.getPath();
			config = new HttpUrlConfig(siteUrl);
		}

		//config.setHeader("Content-Type", "application/json; charset=utf-8");
		//config.setHeader("Accept", "application/json");
		//Accept-Charset: utf-8
		//User-Agent: Hatatitla

		config.setParamSetter(new ConfigurableParamSetter("yyyy-MM-dd HH:mm:ss.SSS")); //2010-06-01 12:21:47.000

		config.addBuilderInterceptor(new HttlBuilderInterceptor() {

			@Override
			public void onBuild(HttlRequestBuilder<?> builder) {
				builder.param("api_key", keys.getApiKey());
				//builder.param("access_token", keys.getAccessToken()); //XXX Build AccessTokenLookup abstraction
			}
		});

		config.addExecutionInterceptor(new HttlExecutionInterceptor() {

			@Override
			public HttlResponse intercept(HttlRequest request, HttlExecutionChain chain) throws IOException {

				HttlResponse response = chain.next(request);

				if (response.getHttpStatusCode() != 200) {
					Disqus.throwException(response, mapper);
				}
				return response;
			}
		});

		this.mapper = Disqus.buildJacksonMapper();
		Jackson2Unmarshaller factory = new Jackson2Unmarshaller(mapper);
		config.addResponseUnmarshaller(factory, "application/json");
		this.sender = config.build();
		this.appsApi = HttlApiBuilder.build(ApiApplications.class, sender);
		this.threadsApi = HttlApiBuilder.build(ApiThreads.class, sender);
		this.categoryApi = HttlApiBuilder.build(ApiCategories.class, sender);
		this.whitelistsApi = HttlApiBuilder.build(ApiWhitelists.class, sender);
		this.blacklistsApi = HttlApiBuilder.build(ApiBlacklists.class, sender);
		this.postsApi = HttlApiBuilder.build(ApiPosts.class, sender);
	}

	public void close() {
		try {
			this.sender.close();
		} catch (Exception x) {
			//ignore
		}
	}

	public HttlSender getSender() {
		return this.sender;
	}

	public DisqusApplicationKeys getApplicationKeys() {
		return this.keys;
	}

	public ApiApplications applications() {
		return appsApi;
	}

	public ApiThreads threads() {
		return threadsApi;
	}

	public ApiCategories categories() {
		return categoryApi;
	}

	public ApiWhitelists whitelists() {
		return whitelistsApi;
	}

	public ApiBlacklists blacklists() {
		return blacklistsApi;
	}

	public ApiPosts posts() {
		return postsApi;
	}
}

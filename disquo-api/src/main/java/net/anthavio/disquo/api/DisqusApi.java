package net.anthavio.disquo.api;

import java.io.Closeable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.anthavio.httl.HttlBuilder;
import net.anthavio.httl.HttlBuilderVisitor;
import net.anthavio.httl.HttlExecutionChain;
import net.anthavio.httl.HttlExecutionFilter;
import net.anthavio.httl.HttlParameterSetter.ConfigurableParamSetter;
import net.anthavio.httl.HttlRequest;
import net.anthavio.httl.HttlRequestBuilders.HttlRequestBuilder;
import net.anthavio.httl.HttlResponse;
import net.anthavio.httl.HttlSender;
import net.anthavio.httl.SenderConfigurer;
import net.anthavio.httl.api.HttlApiBuilder;
import net.anthavio.httl.marshall.Jackson2Unmarshaller;

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

	private final ApiForums forumsApi;

	private final ApiUsers usersApi;

	private final ApiExports exportApi;

	private final ApiImports importApi;

	public DisqusApi(DisqusApplicationKeys keys) {
		this(keys, null, null);
	}

	public DisqusApi(DisqusApplicationKeys keys, String apiUrl) {
		this(keys, apiUrl, null);
	}

	public DisqusApi(DisqusApplicationKeys keys, SenderConfigurer config) {
		this(keys, null, config);
	}

	private DisqusApi(final DisqusApplicationKeys keys, String apiUrl, SenderConfigurer config) {
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
			config = HttlBuilder.httpUrl(siteUrl).sender();
		}

		//config.setHeader("Content-Type", "application/json; charset=utf-8");
		//config.setHeader("Accept", "application/json");
		//Accept-Charset: utf-8
		//User-Agent: Hatatitla

		config.setParamSetter(new ConfigurableParamSetter("yyyy-MM-dd HH:mm:ss.SSS")); //2010-06-01 12:21:47.000

		config.addBuilderVisitor(new HttlBuilderVisitor() {

			@Override
			public void visit(HttlRequestBuilder<?> builder) {
				builder.param("api_key", keys.getApiKey());
				//builder.param("access_token", keys.getAccessToken()); //XXX Build AccessTokenLookup abstraction
			}
		});

		config.addExecutionFilter(new HttlExecutionFilter() {

			@Override
			public HttlResponse filter(HttlRequest request, HttlExecutionChain chain) throws IOException {

				HttlResponse response = chain.next(request);

				if (response.getHttpStatusCode() != 200) {
					Disqus.throwException(response, mapper);
				}
				return response;
			}
		});

		this.mapper = Disqus.buildJacksonMapper();
		Jackson2Unmarshaller jackson = new Jackson2Unmarshaller(mapper);
		config.setUnmarshaller(jackson);
		this.sender = config.build();

		this.appsApi = HttlApiBuilder.build(ApiApplications.class, sender);
		this.threadsApi = HttlApiBuilder.build(ApiThreads.class, sender);
		this.categoryApi = HttlApiBuilder.build(ApiCategories.class, sender);
		this.whitelistsApi = HttlApiBuilder.build(ApiWhitelists.class, sender);
		this.blacklistsApi = HttlApiBuilder.build(ApiBlacklists.class, sender);
		this.postsApi = HttlApiBuilder.build(ApiPosts.class, sender);
		this.forumsApi = HttlApiBuilder.build(ApiForums.class, sender);
		this.usersApi = HttlApiBuilder.build(ApiUsers.class, sender);
		this.exportApi = HttlApiBuilder.build(ApiExports.class, sender);
		this.importApi = HttlApiBuilder.build(ApiImports.class, sender);
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

	public ApiForums forums() {
		return forumsApi;
	}

	public ApiUsers users() {
		return usersApi;
	}

	public ApiExports exports() {
		return exportApi;
	}

	public ApiImports imports() {
		return importApi;
	}
}

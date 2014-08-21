package net.anthavio.disquo.browser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.anthavio.disquo.api.DisqusApi;
import net.anthavio.disquo.api.DisqusApplicationKeys;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.auth.SsoAuthenticator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * In cloud, all session values must be strictly Sereializable because cloud server instances 
 * can be started/stopped at any moment and user session will be migrated to another server instance as cloud provider wants.
 * 
 * @author martin.vanek
 *
 */
@Component(value = "SessionData")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DisquoSessionData implements Serializable {

	private static Logger logger = LoggerFactory.getLogger(DisquoSessionData.class);

	private static final long serialVersionUID = 1L;

	private DisqusApplicationKeys applicationKeys;

	private transient DisqusApi driver; //cannot be Serializable

	private UserIdentityForm identity;

	@PreDestroy
	public void close() {
		if (driver != null) {
			driver.close();
		}
	}

	@PostConstruct
	public void init() {
		String keysResource = System.getProperty("disquo-keyfile", "disquo-browser.properties");
		DefaultResourceLoader loader = new DefaultResourceLoader();
		Resource resource = loader.getResource(keysResource);

		InputStream stream = null;
		try {
			stream = resource.getInputStream();
		} catch (IOException iox) {
			logger.info("Cannot load Disqus application from " + keysResource + ": " + iox.getMessage());
		}

		if (stream != null) {
			Properties properties = new Properties();
			try {
				logger.info("Disqus application keys from " + keysResource);
				properties.load(stream);
				applicationKeys = DisqusApplicationKeys.init(properties, "disqus_api_key", "disqus_api_secret",
						"disqus_access_token");
				driver = new DisqusApi(applicationKeys);
			} catch (Exception x) {
				logger.warn("Oh crap!", x);
			} finally {
				try {
					stream.close();
				} catch (IOException iox) {
					//completely ignore
				}
			}
		}
	}

	public DisqusApi getDriver() {
		return driver;
	}

	public String getSsoRemoteAuth() {
		SsoAuthData sso = identity.getSso();
		if (sso == null) {
			throw new IllegalArgumentException("SSO identity is not set");
		}
		String apiSecret = driver.getApplicationKeys().getApiSecret();
		if (apiSecret == null) {
			throw new IllegalArgumentException("API secret key is not set");
		}
		return SsoAuthenticator.remote_auth_s3(sso, apiSecret);
	}

	public String getSsoRemoteAuth(SsoAuthData sso) {
		String remote_auth_s3 = SsoAuthenticator.remote_auth_s3(sso, this.driver.getApplicationKeys().getApiSecret());
		return remote_auth_s3;
	}

	public String getApiKey() {
		return getApplicationKeys().getApiKey();
	}

	public DisqusApplicationKeys getApplicationKeys() {
		return applicationKeys;
	}

	public void setApplicationKeys(DisqusApplicationKeys applicationKeys) {
		this.applicationKeys = applicationKeys;
		if (applicationKeys != null) {
			if (this.driver != null) {
				driver.close();
			}
			driver = new DisqusApi(applicationKeys);
		}
	}

	public UserIdentityForm getIdentity() {
		return identity;
	}

	public void setIdentity(UserIdentityForm identity) {
		this.identity = identity;
	}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		//init transient Disqus
		if (applicationKeys != null) {
			driver = new DisqusApi(applicationKeys);
		}
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
	}
	/*
			private void readObjectNoData() throws ObjectStreamException {

			}
		*/
}

package com.anthavio.disquo.browser;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusApplicationKeys;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;

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

	private static final long serialVersionUID = 1L;

	private DisqusApplicationKeys applicationKeys;

	private transient Disqus driver; //cannot be Serializable

	private UserIdentityForm identity;

	@PreDestroy
	public void close() {
		if (driver != null) {
			driver.close();
		}
	}

	@PostConstruct
	public void init() {
		/*
		InputStream stream = getClass().getClassLoader().getResourceAsStream("disquo-browser.properties");
		if (stream != null) {
			Properties properties = new Properties();
			try {
				properties.load(stream);
				DisqusApplicationKeys keys = DisqusApplicationKeys.init(properties, "disqus_api_key", "disqus_api_secret",
						"disqus_access_token");
				driver = new Disqus(keys);
			} catch (Exception x) {
				//nevermind...
				x.printStackTrace();
			}
		}
		*/
	}

	public Disqus getDriver() {
		return driver;
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
			driver = new Disqus(applicationKeys);
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
			driver = new Disqus(applicationKeys);
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

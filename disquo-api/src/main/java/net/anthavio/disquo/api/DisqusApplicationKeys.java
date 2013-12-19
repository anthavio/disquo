package net.anthavio.disquo.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;

import com.anthavio.httl.util.Cutils;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusApplicationKeys implements Serializable {

	private static final long serialVersionUID = 1L;

	private String apiKey;

	private String apiSecret;

	private String accessToken;

	public static DisqusApplicationKeys load(String resource) {
		return load(resource, "api_key", "api_secret", "access_token");
	}

	public static DisqusApplicationKeys load(String resource, String publicKeyProperty, String secretKeyProperty) {
		return load(resource, publicKeyProperty, secretKeyProperty, null);
	}

	public static DisqusApplicationKeys load(String resource, String publicKeyProperty, String secretKeyProperty,
			String accessTokenProperty) {
		InputStream stream = null;
		if (resource.startsWith("file:")) {
			File file = new File(resource.substring(5));
			try {
				stream = new FileInputStream(file);
			} catch (FileNotFoundException x) {
				Cutils.close(stream);
				throw new IllegalArgumentException("File not found " + resource);
			}
		} else {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			stream = contextClassLoader.getResourceAsStream(resource);
			if (stream == null) {
				throw new IllegalArgumentException("Resource not found " + resource);
			}
		}
		Properties props = new Properties();
		try {
			props.load(stream);
		} catch (IOException iox) {
			throw new IllegalArgumentException("Failed to load resource " + resource, iox);
		} finally {
			Cutils.close(stream);
		}
		return init(props, publicKeyProperty, secretKeyProperty, accessTokenProperty);
	}

	public static DisqusApplicationKeys init(Properties props, String publicKeyProperty, String secretKeyProperty,
			String accessTokenProperty) {
		String publicKey = props.getProperty(publicKeyProperty);
		if (publicKey == null) {
			throw new IllegalArgumentException("Property " + publicKeyProperty + " does not exist");
		}
		String secretKey = props.getProperty(secretKeyProperty);
		if (secretKey == null) {
			throw new IllegalArgumentException("Property " + secretKeyProperty + " does not exist");
		}
		String accessToken = null;
		if (accessTokenProperty != null) {
			accessToken = props.getProperty(accessTokenProperty);
			if (accessToken == null) {
				throw new IllegalArgumentException("Property " + accessTokenProperty + " does not exist");
			}
		}
		return new DisqusApplicationKeys(publicKey, secretKey, accessToken);
	}

	public DisqusApplicationKeys() {
		//default - setters to setup keys
	}

	/**
	 * sets accessToken to null to disallow application authenticated calls (end user token must be provided)
	 */
	public DisqusApplicationKeys(String apiKey, String secretKey) {
		this(apiKey, secretKey, null);
	}

	public DisqusApplicationKeys(String apiKey, String secretKey, String accessToken) {
		if (StringUtils.isBlank(apiKey)) {
			throw new NullArgumentException(apiKey);
		}
		this.apiKey = apiKey;

		if (StringUtils.isBlank(secretKey)) {
			throw new NullArgumentException(secretKey);
		}
		this.apiSecret = secretKey;

		//allow null token
		this.accessToken = accessToken;
	}

	public String getApiKey() {
		return this.apiKey;
	}

	public String getApiSecret() {
		return this.apiSecret;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "DisqusApplicationKeys [apiKey=" + apiKey + ", apiSecret=" + apiSecret + ", accessToken=" + accessToken
				+ "]";
	}

}

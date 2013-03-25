package com.anthavio.disquo.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.UnhandledException;

import com.anthavio.disquo.api.ArgumentConfig.ArgType;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.hatatitla.Cutils;
import com.anthavio.hatatitla.HttpHeaderUtil;
import com.anthavio.hatatitla.SenderResponse;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

/**
 * 
 * @author vanek
 * 
 */
public abstract class DisqusMethod<T> {

	//XXX this is done for every new instance so we may cache it...
	@SuppressWarnings("unchecked")
	protected Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	private final Disqus disqus;

	private final DisqusMethodConfig config;

	private List<Parameter> parameters = new LinkedList<Parameter>();

	public static class Parameter {

		private final String name;

		private final String value;

		public Parameter(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.name + "=" + this.value;
		}
	}

	private OutputFormat outputFormat = OutputFormat.json; //default is JSON

	public DisqusMethod(Disqus disqus, DisqusMethodConfig config) {
		if (disqus == null) {
			throw new NullArgumentException("disqus");
		}
		this.disqus = disqus;

		if (config == null) {
			throw new NullArgumentException("config");
		}
		this.config = config;
	}

	public DisqusMethodConfig getConfig() {
		return this.config;
	}

	public OutputFormat getOutputFormat() {
		return this.outputFormat;
	}

	public void setOutputFormat(OutputFormat outputFormat) {
		if (outputFormat == null) {
			throw new NullArgumentException("outputFormat");
		}
		this.outputFormat = outputFormat;
	}

	/**
	 * Sets caller identity as access_token (OAuth)
	 */
	public DisqusMethod<T> setAccessToken(String access_token) {
		this.parameters.add(new Parameter("access_token", access_token));
		return this;
	}

	/**
	 * Sets caller identity as sso remote_auth token
	 */
	public DisqusMethod<T> setRemoteAuth(String userId, String username, String email) {
		SsoAuthData authData = new SsoAuthData(userId, username, email);
		this.setRemoteAuth(authData);
		return this;
	}

	/**
	 * Sets caller identity as sso remote_auth token
	 */
	public DisqusMethod<T> setRemoteAuth(SsoAuthData ssoAuthData) {
		if (ssoAuthData == null) {
			throw new NullArgumentException("ssoAuthData");
		}
		String remote_auth = SsoAuthenticator.remote_auth_s3(ssoAuthData, this.disqus.getApplicationKeys().getSecretKey());
		this.parameters.add(new Parameter("remote_auth", remote_auth));
		return this;
	}

	/**
	 * Sets caller identity as sso remote_auth token
	 */
	public DisqusMethod<T> setRemoteAuth(String remote_auth) {
		if (StringUtils.isBlank(remote_auth)) {
			throw new IllegalArgumentException("remote_auth is blank");
		}
		this.parameters.add(new Parameter("remote_auth", remote_auth));
		return this;
	}

	public boolean isAuthenticated() {
		return findParameter("access_token") != null || findParameter("remote_auth") != null;
	}

	protected List<Parameter> getParameters() {
		return this.parameters;
	}

	public DisqusResponse<T> execute() {
		validate();
		return this.disqus.callApi(this, this.type);
	}

	public Map<String, Object> execute(Class<?> responseType) {
		InputStream stream = call().getStream();
		try {
			Map<String, Object> jsonData = this.disqus.mapper.readValue(stream, Map.class);
			return jsonData;
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		} finally {
			Cutils.close(stream);
		}
	}

	public SenderResponse call() {
		validate();
		return this.disqus.callApi(this);
	}

	/**
	 * Prints response for debugging purposes
	 * 
	 * @return Map parsed reposnse
	 */
	public Map<String, Object> pretty() {
		return print(true);
	}

	/**
	 * Prints response for debugging purposes
	 * 
	 * @return Map parsed reposnse
	 */
	public Map<String, Object> print(boolean pretty) {
		SenderResponse response = call();
		try {
			String string = HttpHeaderUtil.readAsString(response);
			Map<String, Object> json = this.disqus.mapper.readValue(string, Map.class);
			if (pretty) {
				string = this.disqus.mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(json);
				//mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				//mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			}
			System.out.println(string);//XXX System.out.println()
			return json;
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		} finally {
			Cutils.close(response);
		}
	}

	private void validate() {
		//config.validate(method);
		ArgumentConfig[] arguments = this.config.getArguments();
		for (ArgumentConfig aconfig : arguments) {
			validateArgument(aconfig);
		}
	}

	private void validateArgument(ArgumentConfig aconfig) {
		if (aconfig.getIsRequired()) {
			boolean exist = false;
			String rname = aconfig.getName();
			for (Parameter parameter : this.parameters) {
				String name = parameter.name;
				//starts with is needed for Queryable identifiers for user/thread
				if (name.equals(rname) || name.startsWith(rname)) {
					exist = true;
					//String value = parameter[1];
					//TODO check for value type, range, ...
					break;
				}
			}
			if (!exist) {
				throw new DisqusException("Required parameter '" + rname + "' not found");
			}
		}
	}

	private ArgumentConfig getArgConfig(String name) {
		for (ArgumentConfig aconfig : this.config.getArguments()) {
			if (aconfig.getName().equals(name)) {
				return aconfig;
			}
		}
		return null;
	}

	protected Parameter findParameter(String name) {
		for (Parameter parameter : this.parameters) {
			if (parameter.name.equals(name)) {
				return parameter;
			}
		}
		return null;
	}

	private static final String[] EMPTY = new String[0];

	private String[] findValues(String name) {
		List<String> values = new LinkedList<String>();
		for (Parameter parameter : this.parameters) {
			if (parameter.name.equals(name)) {
				values.add(parameter.value);
			}
		}
		if (values.size() != 0) {
			return values.toArray(new String[values.size()]);
		} else {
			return EMPTY;
		}
	}

	/**
	 * Map based type unsafe parameter setter
	 */
	public void addParams(Map<String, Object> params) {
		Set<Entry<String, Object>> entrySet = params.entrySet();
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			addParam(key, value);
		}
	}

	public void addParam(String name, Object value) {
		if (value == null) {
			throw new NullArgumentException("Parameter '" + name + "' value");
		}

		ArgumentConfig aconfig = getArgConfig(name);
		if (aconfig == null) {
			if (disqus.getStrictParameters()) {
				throw new IllegalArgumentException("Parameter '" + name + "' is not allowed for "
						+ this.config.getUrlFragment());
			} else {
				aconfig = new ArgumentConfig("_unused_", ArgType.STRING, false, true);
			}
		}

		if (value instanceof Collection<?>) {
			addParams(name, (Collection<?>) value);

		} else if (value.getClass().isArray()) {
			Class<?> componentType = value.getClass().getComponentType();
			//might rather to conver it...
			if (componentType.isPrimitive()) {
				int length = Array.getLength(value);
				for (int i = 0; i < length; ++i) {
					Object item = Array.get(value, i);
					addParam(name, item);
				}
			} else {
				addParams(name, (Object[]) value);
			}
		} else {

			//check value type and format
			try {
				value = aconfig.getType().validate(value);
			} catch (Exception x) {
				throw new IllegalArgumentException("Parameter '" + name + "' value is wrong '" + value + "'", x);
			}

			//convert to String
			String pvalue;
			if (value instanceof Date) {
				pvalue = new SimpleDateFormat(Disqus.DATE_FORMAT).format((Date) value);

			} else if (value instanceof Vote) {
				pvalue = String.valueOf(((Vote) value).value);

			} else if (value instanceof Queryable) {
				Queryable queryable = (Queryable) value;
				String query = queryable.getQuery();
				if (query != null) {
					name = name + ":" + query;
				}
				pvalue = queryable.getValue();

			} else if (value instanceof Serializable) {
				//Boring value. Just toString it
				pvalue = String.valueOf(value);
				if (StringUtils.isBlank(pvalue)) {
					throw new IllegalArgumentException("Parameter '" + name + "' value is blank");
				}

			} else {
				throw new IllegalArgumentException("Parameter '" + name + "' value is of unsupported type " + value.getClass());
			}

			if (aconfig.getIsMultivalue()) {
				//check if value already exists
				String[] values = findValues(name);
				boolean exist = false;
				for (String evalue : values) {
					if (evalue.equals(pvalue)) {
						exist = true;
						break;
					}
				}
				if (exist) {
					throw new IllegalArgumentException("Parameter '" + name + "' value '" + pvalue + "' already exist");
				} else {
					this.parameters.add(new Parameter(name, pvalue));
				}
			} else { // non multi value
				Parameter parameter = findParameter(name);
				if (parameter != null) {
					throw new IllegalArgumentException("Parameter '" + name + "' value '" + pvalue + "' already exist");
					//parameter[1] = pvalue;
				} else {
					this.parameters.add(new Parameter(name, pvalue));
				}
			}

		}

	}

	protected void addParams(String name, Collection<?> list) {
		ArgumentConfig aconfig = getArgConfig(name);
		if (aconfig == null) {
			throw new IllegalArgumentException("Parameter '" + name + "' is not allowed for " + this.config.getUrlFragment());
		} else if (!aconfig.getIsMultivalue()) {
			throw new IllegalArgumentException("Parameter '" + name + "' must not be multiple");
		}
		if (list != null && list.size() != 0) {
			boolean allNull = true;
			for (Object value : list) {
				if (value != null) {
					allNull = false;
					addParam(name, value);
					//parameters.add(new String[] { name, String.valueOf(value) });
				}
			}
			if (allNull && aconfig.getIsRequired()) {
				throw new NullArgumentException(name);
			}
		} else {
			if (aconfig.getIsRequired()) {
				throw new NullArgumentException(name);
			}
		}
	}

	protected void addParams(String name, Object[] array) {
		ArgumentConfig aconfig = getArgConfig(name);
		if (aconfig == null) {
			throw new IllegalArgumentException("Parameter '" + name + "' is not allowed for " + this.config.getUrlFragment());
		} else if (!aconfig.getIsMultivalue()) {
			throw new IllegalArgumentException("Parameter '" + name + "' must not be multiple");
		}
		if (array != null && array.length != 0) {
			boolean allNull = true;
			for (Object value : array) {
				if (value != null) {
					allNull = false;
					addParam(name, value);
					//parameters.add(new String[] { name, String.valueOf(value) });
				}
			}
			if (allNull && aconfig.getIsRequired()) {
				throw new NullArgumentException(name);
			}
		} else {
			if (aconfig.getIsRequired()) {
				throw new NullArgumentException(name);
			}
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + this.config.getUrlFragment() + " " + getParameters();
	}
}

package net.anthavio.disquo.api.auth;

import net.anthavio.httl.api.RestVar;

/**
 * 
 * @author vanek
 *
 */
@RestVar
public class AnonymousData {

	@RestVar(name = "author_name", required = true)
	private String name;

	@RestVar(name = "author_email", required = true)
	private String email;

	@RestVar(name = "author_url")
	private String url;

	AnonymousData() {
		//marshallig
	}

	public AnonymousData(String name, String email, String url) {
		this.name = name;
		this.email = email;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

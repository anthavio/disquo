package com.anthavio.disquo.api.auth;

import com.anthavio.disquo.api.response.JsonStringBuilder;

/**
 * 
 * @author martin.vanek
 *
 */
public class SsoAuthData {

	private String userId;

	private String fullName;

	private String email;

	public SsoAuthData() {
		//
	}

	public SsoAuthData(String userId, String fullName, String email) {
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}

}

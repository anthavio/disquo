package com.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;

/**
 * 
 * @author martin.vanek
 *
 */
public class AuthenticationTest {

	@Test
	public void ssoToken() {
		String apiSecret = "12345678901234567890";
		SsoAuthData authIn = new SsoAuthData("user-123456-ěščřžýáíéůú", "ěščřžýáíéůú ĚŠČŘŽÝÁÍÉŮÚ", "ěščřžýáíéůú@gege.com");
		String remote_auth_s3 = SsoAuthenticator.remote_auth_s3(authIn, apiSecret);
		SsoAuthData authOut = SsoAuthenticator.decode_remote_auth(remote_auth_s3, apiSecret);
		assertThat(authIn.getUserId()).isEqualTo(authOut.getUserId());
		assertThat(authIn.getFullName()).isEqualTo(authOut.getFullName());
		assertThat(authIn.getEmail()).isEqualTo(authOut.getEmail());
	}
}

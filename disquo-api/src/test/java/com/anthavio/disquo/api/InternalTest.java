package com.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;

import org.apache.commons.lang.UnhandledException;
import org.fest.assertions.api.Fail;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import com.anthavio.disquo.TestInputData;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.api.auth.SsoAuthenticator;
import com.anthavio.hatatitla.HttpSender;
import com.anthavio.hatatitla.JavaHttpResponse;
import com.anthavio.hatatitla.SenderRequest;
import com.anthavio.hatatitla.SenderResponse;
import com.anthavio.hatatitla.HttpSender.Multival;

/**
 * 
 * @author martin.vanek
 *
 */
public class InternalTest {

	@Test
	public void testSsoToken() {
		SsoAuthData ssoIn = new SsoAuthData("npg_disqus_user_id_test_1", "Npg Disqus User Test 1", "anthavio@post.cz");
		TestInputData tidata = TestInputData.load("disqus-dajc.properties");
		String remote_auth = SsoAuthenticator.remote_auth_s3(ssoIn, tidata.getSecretKey());
		SsoAuthData ssoOut = SsoAuthenticator.decode_remote_auth(remote_auth, tidata.getSecretKey());

		assertThat(ssoOut.getUserId()).isEqualTo(ssoIn.getUserId());
		assertThat(ssoOut.getFullName()).isEqualTo(ssoIn.getFullName());
		assertThat(ssoOut.getEmail()).isEqualTo(ssoIn.getEmail());
	}

	@Test
	public void test_print() {
		Disqus disqus = getDisqus(200, "{\"code\" : 0, \"response\" : [\"We are testing this\"] }");
		disqus.applications().listUsage().pretty();
	}

	@Test
	public void test_error_response() {
		Disqus disqus = getDisqus(500, "{\"code\" : 15, \"response\" : \"We are testing this\" }");
		try {
			disqus.applications().listUsage().execute();
			Fail.fail("Simulated DisqusServerException must be thrown");
		} catch (DisqusServerException dsx) {
			assertThat(dsx.getHttpCode()).isEqualTo(500);
			assertThat(dsx.getDisqusCode()).isEqualTo(15);
			assertThat(dsx.getMessage()).contains("We are testing this");
		}
	}

	private Disqus getDisqus(int httpCode, String responseJson) {
		HttpSender sender = Mockito.mock(HttpSender.class);
		//String source = "{\"code\" : 0, \"response\" : {} }";
		InputStream stream = new ByteArrayInputStream(responseJson.getBytes(Charset.forName("utf-8")));
		String reason;
		if (httpCode == 200) {
			reason = "OK - artificial";
		} else {
			reason = "ERROR - artificial";
		}
		Multival headers = new Multival();
		headers.add("Content-Type", "application/json");
		SenderResponse response = new JavaHttpResponse(httpCode, reason, headers, stream,
				Mockito.mock(HttpURLConnection.class));
		try {
			SenderRequest request = Mockito.any(SenderRequest.class);
			Mockito.when(sender.execute(request)).thenReturn(response);
		} catch (IOException iox) {
			throw new UnhandledException(iox);
		}

		TestInputData tidata = TestInputData.load("disqus-dajc.properties");
		Disqus disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl(), sender);
		return disqus;
	}
}

package net.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;
import net.anthavio.disquo.TestInputData;
import net.anthavio.disquo.api.auth.SsoAuthData;
import net.anthavio.disquo.api.auth.SsoAuthenticator;
import net.anthavio.httl.HttlSender;
import net.anthavio.httl.util.MockTransConfig;
import net.anthavio.httl.util.MockTransport;

import org.fest.assertions.api.Fail;
import org.testng.annotations.Test;

/**
 * 
 * @author martin.vanek
 *
 */
public class InternalTest {

	@Test
	public void testSsoToken() {
		SsoAuthData ssoIn = new SsoAuthData("XYZ-123465789-Q", "Anthavio Lenz", "anthavio@example.com");
		TestInputData tidata = TestInputData.load("disqus-test.properties");
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
		MockTransport transport = new MockTransport(httpCode, "application/json; charset=utf-8", responseJson);
		HttlSender sender = new MockTransConfig(transport).sender().build();
		TestInputData tidata = TestInputData.load("disqus-test.properties");
		Disqus disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl(), sender);
		return disqus;
	}
}

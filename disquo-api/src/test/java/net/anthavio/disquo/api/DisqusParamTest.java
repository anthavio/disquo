package net.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;
import net.anthavio.httl.HttlBuilder;
import net.anthavio.httl.HttlRequest;
import net.anthavio.httl.SenderConfigurer;
import net.anthavio.httl.util.MockTransport;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@Test
public class DisqusParamTest {

	DisqusApi disqus;
	MockTransport mock;

	@BeforeTest
	public void setup() {
		mock = HttlBuilder.transport("x.y.z").mock().build();
		mock.setStaticResponse(200, "application/json", "{\"code\" : 0, \"response\" : null }");
		SenderConfigurer config = HttlBuilder.sender(mock);
		disqus = new DisqusApi(new DisqusApplicationKeys("publicKey", "secretKey", "accessToken"), config);
	}

	public void correct_params() {
		this.disqus.applications().listUsage("zxzxzx", 12345, 666);

		HttlRequest request = mock.getLastRequest();
		assertThat(request.getParameters().size()).isEqualTo(3 + 1);
		assertThat(request.getParameters().getFirst("access_token")).isEqualTo("zxzxzx");
		assertThat(request.getParameters().getFirst("application")).isEqualTo("12345");
		assertThat(request.getParameters().getFirst("days")).isEqualTo("666");

		this.disqus.threads().details(123);
		request = mock.getLastRequest();
		//System.out.println(request.getParameters());
		//empty related=[]
		assertThat(request.getParameters().size()).isEqualTo(2 + 1);
		assertThat(request.getParameters().getFirst("thread")).isEqualTo("123");

	}

}

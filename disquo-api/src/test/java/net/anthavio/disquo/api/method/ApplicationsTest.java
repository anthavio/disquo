package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.testng.annotations.Test;

public class ApplicationsTest extends DisqusMethodTest {

	@Test
	public void applications() {
		disqus.applications().listUsage();
		assertThat(getParameters().size()).isEqualTo(1 + 1); //access_token and api_key

	}

	@Test
	public void imports() {
		disqus.imports().details("token", "forum", "group");
		assertThat(getParameters().size()).isEqualTo(3 + 1);
	}

	@Test
	public void exports() {
		disqus.exports().exportForum("token", "forum");
		assertThat(getParameters().size()).isEqualTo(2 + 1);

	}
}

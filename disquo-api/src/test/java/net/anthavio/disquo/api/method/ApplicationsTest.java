package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;
import net.anthavio.disquo.api.DisqusApi.Identity;
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
		disqus.imports().details(Identity.access("zxzxzx"), "forum", "group");
		assertThat(getParameters().size()).isEqualTo(3 + 1);
	}

	@Test
	public void exports() {
		disqus.exports().exportForum(Identity.access("zxzxzx"), "forum");
		assertThat(getParameters().size()).isEqualTo(2 + 1);

	}
}

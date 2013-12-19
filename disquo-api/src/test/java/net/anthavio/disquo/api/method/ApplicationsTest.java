package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import net.anthavio.disquo.api.DisqusMethodTest;
import net.anthavio.disquo.api.OutputFormat;
import net.anthavio.disquo.api.applications.DisqusApplicationsGroup;
import net.anthavio.disquo.api.applications.ListUsageMethod;

import org.testng.annotations.Test;


public class ApplicationsTest extends DisqusMethodTest {

	@Test
	public void applications() {
		DisqusApplicationsGroup xapplications = disqus.applications();

		ListUsageMethod listUsage = xapplications.listUsage();
		listUsage.setApplication(1);
		listUsage.setDays(1);
		assertThat(getParameters(listUsage).size()).isEqualTo(2);

		//default is json
		assertThat(listUsage.getOutputFormat()).isEqualTo(OutputFormat.json);
		//change to rss
		listUsage.setOutputFormat(OutputFormat.rss);
		assertThat(listUsage.getOutputFormat()).isEqualTo(OutputFormat.rss);

		//default
		assertThat(listUsage.isAuthenticated()).isFalse();
		//change
		listUsage.setRemoteAuth("uid", "uname", "email@email.ie");
		assertThat(listUsage.isAuthenticated()).isTrue();
	}
}

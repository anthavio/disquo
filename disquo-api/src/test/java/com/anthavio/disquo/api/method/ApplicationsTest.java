package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.OutputFormat;
import com.anthavio.disquo.api.applications.DisqusApplicationsGroup;
import com.anthavio.disquo.api.applications.ListUsageMethod;

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

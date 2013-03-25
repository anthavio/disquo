package com.anthavio.disquo.api;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Collection;
import java.util.LinkedList;

import org.fest.assertions.api.Fail;
import org.testng.annotations.Test;

import com.anthavio.disquo.api.ArgumentConfig;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusApplicationKeys;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.applications.ListUsageMethod;
import com.anthavio.disquo.api.threads.ThreadDetailsMethod;

@Test
public class DisqusParamTest {

	Disqus disqus = new Disqus(new DisqusApplicationKeys("publicKey", "secretKey", "accessToken"));

	public void correct_params() {
		ListUsageMethod listUsage = this.disqus.applications().listUsage();
		listUsage.addParam("days", "666");
		//replace older value of siglevalue parameter
		assertThat(listUsage.getParameters().size()).isEqualTo(1);
		assertThat(listUsage.getParameters().get(0).getName()).isEqualTo("days");
		assertThat(listUsage.getParameters().get(0).getValue()).isEqualTo("666");

		ThreadDetailsMethod details = this.disqus.threads().details(123);
		assertThat(details.getParameters().size()).isEqualTo(1);
		assertThat(details.getParameters().get(0).getName()).isEqualTo("thread");
		assertThat(details.getParameters().get(0).getValue()).isEqualTo("123");

		details.addRelated(Related.forum);
		details.addRelated(Related.author);
		assertThat(details.getParameters().size()).isEqualTo(3);
	}

	@Test
	public void invalid_param_null() {
		ThreadDetailsMethod details = this.disqus.threads().details(0);
		try {
			details.setForum(null);
			Fail.fail("Null parameter value must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains("forum");
			assertThat(iax.getMessage()).contains("value must not be null");
		}
	}

	@Test
	public void invalid_param_blank() {
		ThreadDetailsMethod details = this.disqus.threads().details(0);
		try {
			details.setForum("");
			Fail.fail("Blank parameter value must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains("forum");
			assertThat(iax.getMessage()).contains("value is blank");
		}
	}

	@Test
	public void invalid_param_rewrite() {
		ListUsageMethod method = this.disqus.applications().listUsage();
		method.addParam("days", 333);
		try {
			method.addParam("days", 334);
			Fail.fail("Rewrite of parameter must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains("days");
			assertThat(iax.getMessage()).contains("already exist");
		}
	}

	@Test
	public void invalid_param_name() {
		ListUsageMethod method = this.disqus.applications().listUsage();
		String silly_name = System.currentTimeMillis() + "";
		try {
			method.addParam(silly_name, "value");
			Fail.fail("Silly named parameter must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains(silly_name);
			assertThat(iax.getMessage()).contains("is not allowed");
		}
	}

	@Test
	public void invalid_param_required() {
		ThreadDetailsMethod method = this.disqus.threads().details(123);
		try {
			method.addParam("thread", null);
			Fail.fail("Null value for reqired argument must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains("thread");
			assertThat(iax.getMessage()).contains("must not be null");
		}
	}

	@Test
	public void invalid_param_value_integer() {
		ListUsageMethod method = this.disqus.applications().listUsage();
		//convertible string -> int
		method.addParam("days", "13");
		try {
			method.addParam("days", "NonInteger");
			Fail.fail("Non convertible value must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains("days");
			assertThat(iax.getMessage()).contains("value is wrong");
		}
	}

	@Test
	public void invalid_param_serializable() {
		ListUsageMethod method = this.disqus.applications().listUsage();
		ArgumentConfig aconfig = method.getConfig().getArguments()[0];
		try {
			method.addParam(aconfig.getName(), new Object());
			Fail.fail("Unserializable value must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains(aconfig.getName());
			assertThat(iax.getMessage()).contains("value is wrong");
		}
	}

	@Test
	public void invalid_param_multi_array() {
		ListUsageMethod method = this.disqus.applications().listUsage();
		ArgumentConfig aconfig = method.getConfig().getArguments()[0];
		try {
			method.addParam(aconfig.getName(), new String[] { "x", "y" });
			Fail.fail("value array must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains(aconfig.getName());
			assertThat(iax.getMessage()).contains("must not be multiple");
		}
	}

	@Test
	public void invalid_param_multi_collection() {
		ListUsageMethod method = this.disqus.applications().listUsage();
		ArgumentConfig aconfig = method.getConfig().getArguments()[0];
		try {
			Collection<String> list = new LinkedList<String>();
			list.add("x");
			list.add("y");
			method.addParam(aconfig.getName(), list);
			Fail.fail("value list must not be accepted");
		} catch (IllegalArgumentException iax) {
			assertThat(iax.getMessage()).contains(aconfig.getName());
			assertThat(iax.getMessage()).contains("must not be multiple");
		}
	}
}

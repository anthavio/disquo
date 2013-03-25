package com.anthavio.disquo.api.config;

import junit.framework.TestCase;

import org.junit.Test;

import com.anthavio.disquo.api.config.DisqusConfig;

public class DisqusConfigTest {
	@Test
	public void disqusDisabled() {
		DisqusConfig config = new DisqusConfig(false, "someforum name",
				"public", "secret", "token", true, 50);
		TestCase.assertFalse(config.isDisqusEnabled());
		TestCase.assertNotNull(config.getDisqusForumShortname());
		TestCase.assertNotNull(config.getDisqusPublicKey());
		TestCase.assertNotNull(config.getDisqusSecretKey());
	}

	@Test(expected = IllegalStateException.class)
	public void disqusEnabledForumNull() {
		new DisqusConfig(true, null, "public", "secret", "token", true, 50);
	}

	@Test(expected = IllegalStateException.class)
	public void publickeyNull() {
		new DisqusConfig(true, "forum", null, "secret", "token", true, 50);
	}

	@Test(expected = IllegalStateException.class)
	public void secretkeyEmpty() {
		new DisqusConfig(true, "forum", "public", "", "token", true, 50);
	}

	@Test(expected = IllegalStateException.class)
	public void tokenEmpty() {
		new DisqusConfig(true, "forum", "public", "secret", "", true, 50);
	}

	@Test(expected = IllegalStateException.class)
	public void allNull() {
		new DisqusConfig(true, null, null, null, null, true, 50);
	}

	@Test
	public void allSet() {
		DisqusConfig disqusConfig = new DisqusConfig(true, "forum", "public",
				"private", "token", false, 50);
		TestCase.assertTrue(disqusConfig.isDisqusEnabled());
		TestCase.assertEquals("forum", disqusConfig.getDisqusForumShortname());
		TestCase.assertEquals("public", disqusConfig.getDisqusPublicKey());
		TestCase.assertEquals("private", disqusConfig.getDisqusSecretKey());
		TestCase.assertEquals(0, disqusConfig.getDevMode());
		TestCase.assertEquals(50, disqusConfig.getCommentsPerPage());
	}

	@Test
	public void disqusDevModeEnabled() {
		DisqusConfig disqusConfig = new DisqusConfig(true, "forum", "public",
				"private", "token", true, 50);
		TestCase.assertEquals(1, disqusConfig.getDevMode());
	}
}

package com.anthavio.disquo.api.config;

import java.security.InvalidParameterException;

import junit.framework.TestCase;

import org.junit.Test;

import com.anthavio.disquo.api.config.DisqusCacheConfig;

public class DisqusCacheConfigTest {
	@Test(expected = InvalidParameterException.class)
	public void error() {
		DisqusCacheConfig.Builder("someUniueIdenfier").build();
	}

	@Test(expected = InvalidParameterException.class)
	public void errorUniqueIdentifierEmpty() {
		DisqusCacheConfig.Builder("").build();
	}

	@Test(expected = InvalidParameterException.class)
	public void errorUniqueIdentifierNull() {
		DisqusCacheConfig.Builder(null).build();
	}

	@Test(expected = InvalidParameterException.class)
	public void only_commentCacheTimeInMins_set() {
		DisqusCacheConfig.Builder("someUniueIdenfier")
				.commentCacheTimeInMins(10).build();
	}

	@Test(expected = InvalidParameterException.class)
	public void mostCommentedExtendedCacheTimeInMinsNotSet() {
		DisqusCacheConfig.Builder("polopoly").commentCacheTimeInMins(10)
				.commentCountCacheTimeInMins(10)
				.commentCountExtendedCacheTimeInMins(10)
				.mostCommentedCacheTimeInMins(2).build();
	}

	@Test(expected = InvalidParameterException.class)
	public void mostCommentedCacheTimeInMinsNotSet() {
		DisqusCacheConfig.Builder("polopoly").commentCacheTimeInMins(10)
				.commentCountCacheTimeInMins(10)
				.commentCountExtendedCacheTimeInMins(10)
				.mostCommentedExtendedCacheTimeInMins(2).build();
	}

	@Test(expected = InvalidParameterException.class)
	public void commentCountExtendedCacheTimeInMinsNotSet() {
		DisqusCacheConfig.Builder("polopoly").commentCacheTimeInMins(10)
				.commentCountCacheTimeInMins(10)
				.mostCommentedCacheTimeInMins(2)
				.mostCommentedExtendedCacheTimeInMins(2).build();
	}

	@Test(expected = InvalidParameterException.class)
	public void commentCountCacheTimeInMinsNotSet() {
		DisqusCacheConfig.Builder("polopoly").commentCacheTimeInMins(10)
				.commentCountExtendedCacheTimeInMins(10)
				.mostCommentedCacheTimeInMins(2)
				.mostCommentedExtendedCacheTimeInMins(2).build();
	}

	@Test(expected = InvalidParameterException.class)
	public void commentCacheTimeInMinsNotSet() {
		DisqusCacheConfig.Builder("polopoly").commentCountCacheTimeInMins(10)
				.commentCountExtendedCacheTimeInMins(10)
				.mostCommentedCacheTimeInMins(2)
				.mostCommentedExtendedCacheTimeInMins(2).build();
	}

	@Test
	public void allSetAndHappy() {
		DisqusCacheConfig config = DisqusCacheConfig.Builder("polopoly")
				.commentCacheTimeInMins(10).commentCountCacheTimeInMins(10)
				.commentCountExtendedCacheTimeInMins(10)
				.mostCommentedCacheTimeInMins(2)
				.mostCommentedExtendedCacheTimeInMins(2).build();
		TestCase.assertFalse(config.isIndividualCommentCountCacheEnabled());
	}

	@Test
	public void allSetAndHappyCheckIndividualCommentCountCache() {
		DisqusCacheConfig config = DisqusCacheConfig.Builder("polopoly")
				.commentCacheTimeInMins(10).commentCountCacheTimeInMins(10)
				.commentCountExtendedCacheTimeInMins(10)
				.mostCommentedCacheTimeInMins(2)
				.mostCommentedExtendedCacheTimeInMins(2)
				.individualCommentCountCacheEnabled().build();
		TestCase.assertTrue(config.isIndividualCommentCountCacheEnabled());
	}
}

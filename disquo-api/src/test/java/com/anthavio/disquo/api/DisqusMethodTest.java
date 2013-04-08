package com.anthavio.disquo.api;

import java.util.List;

import org.testng.annotations.BeforeClass;

import com.anthavio.disquo.TestInputData;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusMethodTest {

	protected Disqus disqus;

	protected List<String[]> getParameters(DisqusMethod method) {
		return method.getParameters();
	}

	@BeforeClass
	public void setup() {

		TestInputData tidata = TestInputData.load("disqus.properties");
		disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());
	}

}

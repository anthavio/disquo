package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.TestInputData;
import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;

import org.testng.annotations.BeforeClass;


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

		TestInputData tidata = TestInputData.load("disqus-test.properties");
		disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());
	}

}

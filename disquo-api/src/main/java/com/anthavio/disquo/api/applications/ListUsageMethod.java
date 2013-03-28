package com.anthavio.disquo.api.applications;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;

/**
 * Returns the API usage per day for this application.
 * 
 * http://disqus.com/api/docs/applications/listUsage/
 * 
 * @author martin.vanek
 *
 */
public class ListUsageMethod extends DisqusMethod<ListUsageMethod, Object> {

	public ListUsageMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Applications.listUsage);
	}

	public ListUsageMethod setApplication(long application) {
		addParam("application", application);
		return this;
	}

	/**
	 * @param 0 means 1 day
	 */
	public ListUsageMethod setDays(int days) {
		addParam("days", days);
		return this;
	}

	@Override
	protected ListUsageMethod getB() {
		return this;
	}

}

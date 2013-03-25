package com.anthavio.disquo.api.applications;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * http://disqus.com/api/docs/applications/
 * 
 * @author martin.vanek
 *
 */
public class DisqusApplicationsGroup extends DisqusFeatureGroup {

	public DisqusApplicationsGroup(Disqus disqus) {
		super(disqus);
	}

	public ListUsageMethod listUsage() {
		return new ListUsageMethod(disqus);
	}
}

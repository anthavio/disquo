package net.anthavio.disquo.api.applications;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusFeatureGroup;

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

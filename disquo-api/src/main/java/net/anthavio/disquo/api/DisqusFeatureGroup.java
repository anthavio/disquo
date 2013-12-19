package net.anthavio.disquo.api;

import org.apache.commons.lang.NullArgumentException;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusFeatureGroup {

	protected final Disqus disqus;

	public DisqusFeatureGroup(Disqus disqus) {
		if (disqus == null) {
			throw new NullArgumentException("disqus");
		}
		this.disqus = disqus;
	}

}

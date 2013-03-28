package com.anthavio.disquo.api.imports;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusImportsGroup extends DisqusFeatureGroup {

	public DisqusImportsGroup(Disqus disqus) {
		super(disqus);
	}

	public ImportListMethod list(String forum) {
		return new ImportListMethod(disqus).setForum(forum);
	}

	public ImportDetailsMethod details(String forum, long group) {
		return new ImportDetailsMethod(disqus).setForum(forum).setGroup(group);
	}

}

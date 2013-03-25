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
		ImportListMethod method = new ImportListMethod(disqus);
		method.setForum(forum);
		return method;
	}

	public ImportDetailsMethod details(String forum, long group) {
		ImportDetailsMethod method = new ImportDetailsMethod(disqus);
		method.setForum(forum);
		method.setGroup(group);
		return method;
	}

}

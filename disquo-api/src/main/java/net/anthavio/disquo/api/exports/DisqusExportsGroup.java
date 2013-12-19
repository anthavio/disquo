package net.anthavio.disquo.api.exports;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusExportsGroup extends DisqusFeatureGroup {

	public DisqusExportsGroup(Disqus disqus) {
		super(disqus);
	}

	public ExportForumMethod exportForum(String forum) {
		return new ExportForumMethod(disqus).setForum(forum);
	}

}

package com.anthavio.disquo.api.exports;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.forums.BaseForumMethod;

/**
 * 
 * @author martin.vanek
 *
 */
public class ExportForumMethod extends BaseForumMethod<Object> {

	public ExportForumMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Exports.exportForum);
	}

}

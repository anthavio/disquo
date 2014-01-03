package net.anthavio.disquo.api.exports;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.forums.BaseForumMethod;

/**
 * 
 * @author martin.vanek
 *
 */
public class ExportForumMethod extends BaseForumMethod<ExportForumMethod, Object> {

	public ExportForumMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Exports.exportForum);
	}

	@Override
	protected ExportForumMethod getSelf() {
		return this;
	}

}

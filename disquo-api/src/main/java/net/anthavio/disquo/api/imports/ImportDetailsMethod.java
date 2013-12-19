package net.anthavio.disquo.api.imports;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.forums.BaseForumMethod;
import net.anthavio.disquo.api.response.DisqusImportDetails;

/**
 * 
 * @author martin.vanek
 *
 */
public class ImportDetailsMethod extends BaseForumMethod<ImportDetailsMethod, DisqusImportDetails> {

	public ImportDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Imports.details);
	}

	public ImportDetailsMethod setGroup(long group) {
		addParam("group", group);
		return this;
	}

	@Override
	protected ImportDetailsMethod getB() {
		return this;
	}

}

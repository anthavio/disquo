package com.anthavio.disquo.api.imports;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.forums.BaseForumMethod;
import com.anthavio.disquo.api.response.DisqusImportDetails;

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

package com.anthavio.disquo.api.imports;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.forums.BaseForumListMethod;
import com.anthavio.disquo.api.response.DisqusImportDetails;

/**
 * 
 * @author martin.vanek
 *
 */
public class ImportListMethod extends BaseForumListMethod<ImportListMethod, DisqusImportDetails> {

	public ImportListMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Imports.list);
	}

	public ImportListMethod setCursor(String cursor) {
		addParam("cursor", cursor);
		return this;
	}

	@Override
	protected ImportListMethod getB() {
		return this;
	}

}

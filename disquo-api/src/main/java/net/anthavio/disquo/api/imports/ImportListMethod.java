package net.anthavio.disquo.api.imports;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.forums.BaseForumListMethod;
import net.anthavio.disquo.api.response.DisqusImportDetails;

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
	protected ImportListMethod getSelf() {
		return this;
	}

}

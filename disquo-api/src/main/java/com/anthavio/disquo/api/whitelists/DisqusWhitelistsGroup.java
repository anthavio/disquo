package com.anthavio.disquo.api.whitelists;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * http://disqus.com/api/docs/whitelists/
 * 
 * @author martin.vanek
 *
 */
public class DisqusWhitelistsGroup extends DisqusFeatureGroup {

	public DisqusWhitelistsGroup(Disqus disqus) {
		super(disqus);
	}

	public WhitelistList list(String forum) {
		return new WhitelistList(disqus).setForum(forum);
	}

	public WhitelistAdd add(String forum) {
		WhitelistAdd method = new WhitelistAdd(disqus);
		method.setForum(forum);
		return method;
	}

	public WhitelistRemove remove(String forum) {
		WhitelistRemove method = new WhitelistRemove(disqus);
		method.setForum(forum);
		return method;
	}
}

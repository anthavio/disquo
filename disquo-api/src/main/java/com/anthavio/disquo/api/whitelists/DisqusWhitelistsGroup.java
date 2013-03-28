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
		return new WhitelistAdd(disqus).setForum(forum);
	}

	public WhitelistRemove remove(String forum) {
		return new WhitelistRemove(disqus).setForum(forum);
	}
}

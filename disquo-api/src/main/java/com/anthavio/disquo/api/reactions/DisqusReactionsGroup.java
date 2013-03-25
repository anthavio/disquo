package com.anthavio.disquo.api.reactions;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;

/**
 * http://disqus.com/api/docs/reactions/
 * 
 * @author martin.vanek
 *
 */
public class DisqusReactionsGroup extends DisqusFeatureGroup {

	public DisqusReactionsGroup(Disqus disqus) {
		super(disqus);
	}

	public ReactionListMethod list(String forum) {
		return new ReactionListMethod(disqus).setForum(forum);
	}

	public ReactionDetailsMethod details(String forum, long reaction) {
		return new ReactionDetailsMethod(disqus).setForum(forum).setReaction(reaction);
	}

	public ReactionRemoveMethod remove(String forum, long... reaction) {
		return new ReactionRemoveMethod(disqus).setForum(forum).addReaction(reaction);
	}

	public ReactionRestoreMethod restore(String forum, long... reaction) {
		return new ReactionRestoreMethod(disqus).setForum(forum).addReaction(reaction);
	}

}

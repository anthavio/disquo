package com.anthavio.disquo.api.reactions;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class ReactionDetailsMethod extends DisqusMethod<DisqusPost> {

	public ReactionDetailsMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Reactions.details);
	}

	public ReactionDetailsMethod setReaction(long reaction) {
		addParam("reaction", reaction);
		return this;
	}

	public ReactionDetailsMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

}

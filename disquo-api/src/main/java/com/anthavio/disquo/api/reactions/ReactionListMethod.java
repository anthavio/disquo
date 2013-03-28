package com.anthavio.disquo.api.reactions;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusCursorPostsMethod;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusPost;

/**
 * http://disqus.com/api/docs/reactions/list/
 * 
 * @author martin.vanek
 *
 */
public class ReactionListMethod extends DisqusCursorPostsMethod<ReactionListMethod, DisqusPost> {

	public ReactionListMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Reactions.list);
	}

	public ReactionListMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ReactionListMethod setReaction(long reaction) {
		addParam("reaction", reaction);
		return this;
	}

	@Override
	protected ReactionListMethod getB() {
		return this;
	}

}

package net.anthavio.disquo.api.reactions;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorPostsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusPost;

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
	protected ReactionListMethod getSelf() {
		return this;
	}

}

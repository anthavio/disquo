package net.anthavio.disquo.api.reactions;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 *
 */
public class ReactionDetailsMethod extends DisqusMethod<ReactionDetailsMethod, DisqusPost> {

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

	@Override
	protected ReactionDetailsMethod getB() {
		return this;
	}

}

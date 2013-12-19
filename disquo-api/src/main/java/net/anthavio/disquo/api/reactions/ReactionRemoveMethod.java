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
public class ReactionRemoveMethod extends DisqusMethod<ReactionRemoveMethod, DisqusPost> {

	public ReactionRemoveMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Reactions.remove);
	}

	public ReactionRemoveMethod addReaction(long... reaction) {
		addParam("reaction", reaction);
		return this;
	}

	public ReactionRemoveMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ReactionRemoveMethod getB() {
		return this;
	}

}

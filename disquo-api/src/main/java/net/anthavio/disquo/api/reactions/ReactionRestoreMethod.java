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
public class ReactionRestoreMethod extends DisqusMethod<ReactionRestoreMethod, DisqusPost> {

	public ReactionRestoreMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Reactions.restore);
	}

	public ReactionRestoreMethod addReaction(long... reaction) {
		addParam("reaction", reaction);
		return this;
	}

	public ReactionRestoreMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected ReactionRestoreMethod getSelf() {
		return this;
	}

}

package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.ArgumentConfig.Vote;
import net.anthavio.disquo.api.response.DisqusVotePost;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostVoteMethod extends BaseSinglePostMethod<PostVoteMethod, DisqusVotePost> {

	public PostVoteMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.vote);
	}

	public PostVoteMethod setVote(Vote vote) {
		addParam("vote", vote);
		return this;
	}

	@Override
	protected PostVoteMethod getB() {
		return this;
	}

}

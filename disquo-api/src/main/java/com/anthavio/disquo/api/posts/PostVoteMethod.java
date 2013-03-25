package com.anthavio.disquo.api.posts;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.response.DisqusVotePost;

/**
 * 
 * @author martin.vanek
 *
 */
public class PostVoteMethod extends BaseSinglePostMethod<DisqusVotePost> {

	public PostVoteMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.vote);
	}

	public PostVoteMethod setVote(Vote vote) {
		addParam("vote", vote);
		return this;
	}

}

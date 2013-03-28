package com.anthavio.disquo.api.threads;

import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusVoteThread;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadVoteMethod extends BaseSingleThreadMethod<ThreadVoteMethod, DisqusVoteThread> {

	public ThreadVoteMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.vote);
	}

	public ThreadVoteMethod setVote(Vote vote) {
		addParam("vote", vote);
		return this;
	}

	@Override
	protected ThreadVoteMethod getB() {
		return this;
	}

}

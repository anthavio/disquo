package net.anthavio.disquo.api.posts;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusCursorPostsMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.QThread;
import net.anthavio.disquo.api.response.DisqusPost;

/**
 * 
 * @author martin.vanek
 * 
 */
public class PostListMethod extends DisqusCursorPostsMethod<PostListMethod, DisqusPost> {

	public PostListMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Posts.list);
	}

	public PostListMethod addCategory(long... category) {
		addParam("category", category);
		return this;
	}

	public PostListMethod addThread(long... thread) {
		addParam("thread", thread);
		return this;
	}

	public PostListMethod addThread(QThread... thread) {
		addParam("thread", thread);
		return this;
	}

	public PostListMethod addForum(String... forum) {
		addParam("forum", forum);
		return this;
	}

	@Override
	protected PostListMethod getB() {
		return this;
	}

}

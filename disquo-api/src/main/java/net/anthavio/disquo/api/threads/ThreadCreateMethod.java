package net.anthavio.disquo.api.threads;

import java.util.Date;

import net.anthavio.disquo.api.Disqus;
import net.anthavio.disquo.api.DisqusMethod;
import net.anthavio.disquo.api.DisqusMethodConfig;
import net.anthavio.disquo.api.response.DisqusThread;


/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadCreateMethod extends DisqusMethod<ThreadCreateMethod, DisqusThread> {

	public ThreadCreateMethod(Disqus disqus) {
		super(disqus, DisqusMethodConfig.Threads.create);
	}

	public ThreadCreateMethod setCategory(int category) {
		addParam("category", category);
		return this;
	}

	public ThreadCreateMethod setForum(String forum) {
		addParam("forum", forum);
		return this;
	}

	public ThreadCreateMethod setTitle(String title) {
		addParam("title", title);
		return this;
	}

	public ThreadCreateMethod setUrl(String url) {
		addParam("url", url);
		return this;
	}

	public ThreadCreateMethod setDate(Date date) {
		addParam("date", date);
		return this;
	}

	public ThreadCreateMethod setMessage(String message) {
		addParam("message", message);
		return this;
	}

	public ThreadCreateMethod setIdentifier(String identifier) {
		addParam("identifier", identifier);
		return this;
	}

	public ThreadCreateMethod setSlug(String slug) {
		addParam("slug", slug);
		return this;
	}

	@Override
	protected ThreadCreateMethod getB() {
		return this;
	}

}

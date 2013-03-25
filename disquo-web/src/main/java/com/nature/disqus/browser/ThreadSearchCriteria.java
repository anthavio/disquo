package com.nature.disqus.browser;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.anthavio.disquo.api.ArgumentConfig.ThreadState;
import com.anthavio.disquo.api.response.JsonStringBuilder;

/**
 * 
 * @author martin.vanek
 *
 */
public class ThreadSearchCriteria {

	private String forum;

	private Long category;

	private Long thread;

	private Long author;

	private Date since;

	private List<ThreadState> includes = new LinkedList<ThreadState>();

	public boolean isNotEmpty() {
		return StringUtils.isNotEmpty(forum) || category != null || thread != null || author != null || since != null;
	}

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}

	public ThreadSearchCriteria() {
	}

	public Long getCategory() {
		return category;
	}

	public void setCategory(Long category) {
		this.category = category;
	}

	public Long getThread() {
		return thread;
	}

	public void setThread(Long thread) {
		this.thread = thread;
	}

	public String getForum() {
		return forum;
	}

	public void setForum(String forum) {
		this.forum = forum;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public List<ThreadState> getIncludes() {
		return includes;
	}

	public void setIncludes(List<ThreadState> states) {
		includes = states;
	}

	public Long getAuthor() {
		return author;
	}

	public void setAuthor(Long author) {
		this.author = author;
	}

}

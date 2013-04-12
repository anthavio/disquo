package com.anthavio.disquo.browser;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.ThreadState;

/**
 * 
 * @author martin.vanek
 *
 */
@SessionAttributes({ DisqusController.THREAD_CRITERIA, DisqusController.POST_CRITERIA, DisqusController.INDEX_CRITERIA })
public class ControllerBase {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected DisquoSessionData session;

	@ModelAttribute(DisqusController.POST_CRITERIA)
	public PostSearchCriteria initPostSearchCriteria() {
		PostSearchCriteria criteria = new PostSearchCriteria();
		criteria.setForum(null);
		criteria.setCategory(null);
		criteria.setSince(null);
		List<PostState> states = new ArrayList<PostState>();
		states.addAll(PostState.CALL);
		criteria.setIncludes(states);

		return criteria;
	}

	@ModelAttribute(DisqusController.THREAD_CRITERIA)
	public ThreadSearchCriteria initThreadSearchCriteria() {
		ThreadSearchCriteria criteria = new ThreadSearchCriteria();
		criteria.setForum(null);
		criteria.setCategory(null);
		criteria.setAuthor(null);
		criteria.setSince(null);
		List<ThreadState> states = new ArrayList<ThreadState>();
		states.addAll(ThreadState.CALL);
		criteria.setIncludes(states);
		return criteria;
	}

	@ModelAttribute(DisqusController.INDEX_CRITERIA)
	public IndexSearchCriteria initIndexSearchCriteria() {
		return new IndexSearchCriteria();
	}

}

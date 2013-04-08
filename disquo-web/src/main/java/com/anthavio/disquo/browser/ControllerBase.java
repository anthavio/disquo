package com.anthavio.disquo.browser;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.ThreadState;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.auth.SsoAuthData;
import com.anthavio.disquo.browser.UserIdentity.Type;

/**
 * 
 * @author martin.vanek
 *
 */
@SessionAttributes({ DisqusController.THREAD_CRITERIA, DisqusController.POST_CRITERIA, DisqusController.INDEX_CRITERIA,
		IdentityController.USER_IDENTITY })
public class ControllerBase {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected Disqus disqus;

	@ModelAttribute(IdentityController.USER_IDENTITY)
	public UserIdentity initUserIdentity() throws MalformedURLException {
		//OAuth callback URL

		UserIdentity identity = new UserIdentity();
		SsoAuthData sso = new SsoAuthData("654321_id_123456", "John Doe", "example@example.com");
		identity.setSso(sso);
		String accessToken = disqus.getApplicationKeys().getAccessToken();
		identity.setApplicationToken(accessToken);
		identity.setType(Type.sso);
		return identity;
	}

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

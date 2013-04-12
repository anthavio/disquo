package com.anthavio.disquo.browser;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.ArgumentConfig.ThreadState;
import com.anthavio.disquo.api.ArgumentConfig.Vote;
import com.anthavio.disquo.api.DisqusException;
import com.anthavio.disquo.api.DisqusMethod;
import com.anthavio.disquo.api.DisqusServerException;
import com.anthavio.disquo.api.QUser;
import com.anthavio.disquo.api.blacklists.BlacklistRemove;
import com.anthavio.disquo.api.forums.ForumListCategoriesMethod;
import com.anthavio.disquo.api.forums.ForumListUsersMethod;
import com.anthavio.disquo.api.imports.ImportListMethod;
import com.anthavio.disquo.api.posts.HighlightMethod;
import com.anthavio.disquo.api.posts.PostApproveMethod;
import com.anthavio.disquo.api.posts.PostRemoveMethod;
import com.anthavio.disquo.api.posts.PostReportMethod;
import com.anthavio.disquo.api.posts.PostRestoreMethod;
import com.anthavio.disquo.api.posts.PostSpamMethod;
import com.anthavio.disquo.api.posts.PostUnhighlightMethod;
import com.anthavio.disquo.api.posts.PostUpdateMethod;
import com.anthavio.disquo.api.posts.PostVoteMethod;
import com.anthavio.disquo.api.response.DisqusCategory;
import com.anthavio.disquo.api.response.DisqusFilter;
import com.anthavio.disquo.api.response.DisqusForum;
import com.anthavio.disquo.api.response.DisqusImportDetails;
import com.anthavio.disquo.api.response.DisqusModerator;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.response.DisqusUser;
import com.anthavio.disquo.api.threads.ThreadCloseMethod;
import com.anthavio.disquo.api.threads.ThreadDetailsMethod;
import com.anthavio.disquo.api.threads.ThreadListMethod;
import com.anthavio.disquo.api.threads.ThreadListPostsMethod;
import com.anthavio.disquo.api.threads.ThreadOpenMethod;
import com.anthavio.disquo.api.threads.ThreadRemoveMethod;
import com.anthavio.disquo.api.threads.ThreadRestoreMethod;
import com.anthavio.disquo.api.threads.ThreadSubscribeMethod;
import com.anthavio.disquo.api.threads.ThreadUnsubscribeMethod;
import com.anthavio.disquo.api.threads.ThreadUpdateMethod;
import com.anthavio.disquo.api.threads.ThreadVoteMethod;
import com.anthavio.disquo.api.users.UserListForumsMethod;
import com.anthavio.disquo.api.users.UserListPostsMethod;
import com.anthavio.spring.web.MultiFormatDateEditor;

/**
 * 
 * @author martin.vanek
 *
 */
@Controller
//@SessionAttributes({ DisqusController.THREAD_CRITERIA, DisqusController.POST_CRITERIA, AuthController.SSO_IDENTITY })
public class DisqusController extends ControllerBase {

	private Logger log = LoggerFactory.getLogger(getClass());

	static final String THREAD_CRITERIA = "THREAD_CRITERIA";
	static final String POST_CRITERIA = "POST_CRITERIA";

	static final String INDEX_CRITERIA = "INDEX_CRITERIA";

	public DisqusController() {
	}

	@InitBinder
	public void initBinder(final WebDataBinder dataBinder, final Locale locale) {
		//must be first
		dataBinder.setAutoGrowNestedPaths(true);

		MultiFormatDateEditor dateEditor = new MultiFormatDateEditor("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
		dataBinder.registerCustomEditor(Date.class, dateEditor);

		//this works with conjunction thread.html form
		dataBinder.registerCustomEditor(DisqusThread.class, "category", new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(text);
			}

			@Override
			public String getAsText() {
				DisqusCategory category = (DisqusCategory) getValue();
				return String.valueOf(category.getId());
			}
		});
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("disqus/index");
		return modelAndView;
	}

	/**
	 * Search index page
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ModelAndView indexSearch(@ModelAttribute(INDEX_CRITERIA) IndexSearchCriteria criteria, BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("disqus/index");

		if (binding.hasErrors()) {
			return modelAndView;
		}

		if (StringUtils.isNotEmpty(criteria.getForumShort())) {
			return new ModelAndView("redirect:/disqus/forum/" + criteria.getForumShort());
		} else if (StringUtils.isNotEmpty(criteria.getUserName())) {
			return new ModelAndView("redirect:/disqus/user/" + criteria.getUserName());
		}

		return modelAndView;
	}

	@RequestMapping(value = "forums", method = RequestMethod.GET)
	public ModelAndView forums(@RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/forums");

		UserListForumsMethod listForums = this.session.getDriver().users().listForums();

		//listForums.setAccessToken(this.disqus.getDriver().getApplicationKeys().getAccessToken());

		if (StringUtils.isNotBlank(cursor)) {
			listForums.setCursor(cursor);
		}
		DisqusResponse<List<DisqusForum>> response = listForums.execute();
		modelAndView.addObject("forums", response);
		return modelAndView;
	}

	@RequestMapping(value = "forum/{forum}", method = RequestMethod.GET)
	public ModelAndView forumDetail(@PathVariable String forum, @RequestParam(required = false) String importsCursor,
			Model model) {
		ModelAndView modelAndView = new ModelAndView("disqus/forum");

		DisqusResponse<DisqusForum> details = this.session.getDriver().forums().details(forum).addRelated(Related.author)
				.execute();
		modelAndView.addObject("forum", details.getResponse());

		ForumListCategoriesMethod listCategories = this.session.getDriver().forums().listCategories(forum);
		listCategories.setLimit(100);
		listCategories.setOrder(Order.desc);
		DisqusResponse<List<DisqusCategory>> categories = listCategories.execute();
		modelAndView.addObject("categories", categories);

		try {
			ImportListMethod mimports = this.session.getDriver().imports().list(forum);
			if (StringUtils.isNotBlank(importsCursor)) {
				mimports.setCursor(importsCursor);
			}
			DisqusResponse<List<DisqusImportDetails>> imports = mimports.execute();
			modelAndView.addObject("imports", imports);

			DisqusResponse<List<DisqusModerator>> moderators = this.session.getDriver().forums().listModerators(forum)
					.execute();
			modelAndView.addObject("moderators", moderators);

			DisqusResponse<List<DisqusFilter>> blacklist = this.session.getDriver().blacklists().list(forum).execute();
			modelAndView.addObject("blacklist", blacklist);

			DisqusResponse<List<DisqusFilter>> whitelist = this.session.getDriver().whitelists().list(forum).execute();
			modelAndView.addObject("whitelist", whitelist);

		} catch (DisqusServerException dsx) {
			if (dsx.getDisqusCode() != 4 && dsx.getDisqusCode() != 22) { //You do not have write privileges on forum ...
				throw dsx;
			}
			DisqusResponse<String> errorResponse = new DisqusResponse<String>(dsx.getDisqusCode(), dsx.getMessage(), null);
			modelAndView.addObject("imports", errorResponse);
			modelAndView.addObject("moderators", errorResponse);
			modelAndView.addObject("blacklist", errorResponse);
			modelAndView.addObject("whitelist", errorResponse);
		}

		return modelAndView;
	}

	/**
	 * List Users in Forum
	 */
	@RequestMapping(value = "forum/{forum}/users", method = RequestMethod.GET)
	public ModelAndView userList(@PathVariable String forum, @RequestParam(required = false) String usersCursor,
			Model model) {

		ForumListUsersMethod musers = session.getDriver().forums().listUsers(forum);
		musers.setLimit(100);
		if (StringUtils.isNotBlank(usersCursor)) {
			musers.setCursor(usersCursor);
		}
		DisqusResponse<List<DisqusUser>> users = musers.execute();
		model.addAttribute("users", users);

		return forumDetail(forum, null, model);

	}

	/*
		@RequestMapping(value = "forum/{forum}", method = RequestMethod.POST)
		public ModelAndView forumUpdate(@PathVariable String forum, @ModelAttribute @Validated DisqusForum forumX,
				BindingResult binding) {
			ModelAndView modelAndView = new ModelAndView("disqus/forum");
			if (binding.hasErrors()) {
				logger.error(forum + " " + binding.getAllErrors());
				//			modelAndView.addObject("details", DisqusResponse<DisqusForum>)
				return modelAndView;
			}
			return modelAndView;
		}
	*/

	@RequestMapping(value = "forum/{forum}/filters", method = RequestMethod.GET)
	public ModelAndView filterList(@PathVariable String forum, Model model) {
		ModelAndView modelAndView = new ModelAndView("disqus/filters");

		DisqusResponse<DisqusForum> details = this.session.getDriver().forums().details(forum).addRelated(Related.author)
				.execute();
		modelAndView.addObject("forum", details.getResponse());

		DisqusResponse<List<DisqusFilter>> blacklist = this.session.getDriver().blacklists().list(forum).execute();
		modelAndView.addObject("blacklist", blacklist);

		DisqusResponse<List<DisqusFilter>> whitelist = this.session.getDriver().whitelists().list(forum).execute();
		modelAndView.addObject("whitelist", whitelist);
		return modelAndView;
	}

	@RequestMapping(value = "forum/{forum}/blacklist/remove/{filterId}", method = RequestMethod.GET)
	public ModelAndView removeBlacklist(@PathVariable String forum, @PathVariable Long filterId, Model model) {
		BlacklistRemove remove = session.getDriver().blacklists().remove(forum);
		//we don't know type of item, so download blacklist
		List<DisqusFilter> blacklist = session.getDriver().blacklists().list(forum).execute().getResponse();
		for (DisqusFilter filter : blacklist) {
			if (filter.getId().longValue() == filterId.longValue()) {
				if (filter.getType().equals("domain")) {
					remove.addDomain(filter.getValue());
				} else if (filter.getType().equals("word")) {
					remove.addWord(filter.getValue());
				} else if (filter.getType().equals("ip")) {
					remove.addIp(filter.getValue());
				} else if (filter.getType().equals("user")) {
					remove.addUser(filter.getUser().getUsername());
				} else if (filter.getType().equals("email")) {
					remove.addEmail(filter.getValue());
				}
			}
		}
		DisqusResponse<List<DisqusFilter>> response = remove.execute();
		response.getResponse();//nothing is returned
		return filterList(forum, model);
	}

	@RequestMapping(value = "category/{categoryId}", method = RequestMethod.GET)
	public ModelAndView categoryDetail(@PathVariable long categoryId, @RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/category");
		DisqusResponse<DisqusCategory> response = this.session.getDriver().category().details(categoryId).execute();
		modelAndView.addObject("category", response.getResponse());

		//CategoryListThreadsMethod threadsMethod = disqus.category().listThreads(categoryId);
		ThreadListMethod threadsListMethod = this.session.getDriver().threads().list();
		threadsListMethod.addCategory(categoryId);
		threadsListMethod.addRelated(Related.author, Related.category);
		threadsListMethod.addInclude(ThreadState.ALL);
		threadsListMethod.setLimit(100);
		if (StringUtils.isNotBlank(cursor)) {
			threadsListMethod.setCursor(cursor);
		}
		DisqusResponse<List<DisqusThread>> lastThreads = threadsListMethod.execute();
		modelAndView.addObject("lastThreads", lastThreads);

		return modelAndView;
	}

	@RequestMapping(value = "user/{user}", method = RequestMethod.GET)
	public ModelAndView userDetail(@PathVariable String user, @RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/user");

		//user may be userName or userId
		QUser quser;
		try {
			long userId = Long.parseLong(user);
			quser = QUser.build(userId);
		} catch (NumberFormatException nfx) {
			quser = QUser.build(user);
		}

		DisqusResponse<DisqusUser> details = this.session.getDriver().users().details(quser).execute();
		modelAndView.addObject("user", details.getResponse());

		DisqusResponse<List<DisqusForum>> ownedForums = this.session.getDriver().users().listForums(quser).execute();
		modelAndView.addObject("ownedForums", ownedForums);

		UserListPostsMethod listPostsMethod = this.session.getDriver().users().listPosts(quser);
		listPostsMethod.addRelated(Related.thread);
		listPostsMethod.addInclude(PostState.ALL);
		listPostsMethod.setLimit(100);
		if (cursor != null) {
			listPostsMethod.setCursor(cursor);
		}
		DisqusResponse<List<DisqusPost>> lastPosts = listPostsMethod.execute();
		modelAndView.addObject("lastPosts", lastPosts);

		return modelAndView;
	}

	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.GET)
	public ModelAndView threadDetail(@PathVariable long threadId, @RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/thread");

		ThreadDetailsMethod detailsMethod = this.session.getDriver().threads().details(threadId);
		detailsMethod.addRelated(Related.forum, Related.author, Related.category);
		DisqusResponse<DisqusThread> response = detailsMethod.execute();
		DisqusThread thread = response.getResponse();
		modelAndView.addObject("thread", thread);

		List<DisqusCategory> categories = this.session.getDriver().category()
				.list(((DisqusForum) thread.getForum()).getId()).execute().getResponse();
		modelAndView.addObject("categories", categories);

		ThreadListPostsMethod postsMethod = this.session.getDriver().threads().listPosts(threadId);
		postsMethod.addInclude(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted,
				PostState.flagged);
		if (cursor != null) {
			postsMethod.setCursor(cursor);
		}
		postsMethod.setLimit(100);
		DisqusResponse<List<DisqusPost>> lastPosts = postsMethod.execute();
		modelAndView.addObject("lastPosts", lastPosts);

		return modelAndView;
	}

	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.POST)
	public ModelAndView threadUpdate(@PathVariable long threadId, @ModelAttribute DisqusThread thread,
			BindingResult binding) {
		ModelAndView modelAndView = new ModelAndView("disqus/thread");
		if (binding.hasErrors()) {
			return modelAndView;
		}
		//create update method
		ThreadUpdateMethod method = this.session.getDriver().threads().update(threadId);

		method.setTitle(thread.getTitle());

		String message = thread.getMessage();
		if (StringUtils.isNotEmpty(message)) {
			//Disqus is wrapping message: <p>message</p> so remove p tags if present 
			if (message.startsWith("<p>")) {
				message = message.substring(3, message.length() - 4);
			}
			method.setMessage(message);
		}

		method.setCategory(Long.parseLong((String) thread.getCategory()));

		if (thread.getIdentifiers() != null) {
			method.setIdentifier(thread.getIdentifiers().get(0));
		}

		if (StringUtils.isNotEmpty(thread.getLink())) {
			method.setUrl(thread.getLink());
		}

		if (StringUtils.isNotEmpty(thread.getSlug())) {
			method.setSlug(thread.getSlug());
		}

		return threadExecute(threadId, method);
	}

	@RequestMapping(value = "thread/{threadId}", params = "close", method = RequestMethod.POST)
	public ModelAndView threadClose(@PathVariable long threadId) {
		ThreadCloseMethod method = this.session.getDriver().threads().close(threadId);
		return threadExecute(threadId, method);
	}

	@RequestMapping(value = "thread/{threadId}", params = "open", method = RequestMethod.POST)
	public ModelAndView threadOpen(@PathVariable long threadId) {
		ThreadOpenMethod method = this.session.getDriver().threads().open(threadId);
		return threadExecute(threadId, method);
	}

	@RequestMapping(value = "thread/{threadId}", params = "remove", method = RequestMethod.POST)
	public ModelAndView threadDelete(@PathVariable long threadId) {
		ThreadRemoveMethod method = this.session.getDriver().threads().remove(threadId);
		return threadExecute(threadId, method);
	}

	@RequestMapping(value = "thread/{threadId}", params = "restore", method = RequestMethod.POST)
	public ModelAndView threadRestore(@PathVariable long threadId) {
		ThreadRestoreMethod method = this.session.getDriver().threads().restore(threadId);
		return threadExecute(threadId, method);
	}

	@RequestMapping(value = "thread/{threadId}", params = "votePlus", method = RequestMethod.POST)
	public ModelAndView threadVotePlus(@PathVariable long threadId) {
		return threadVote(threadId, Vote.PLUS);
	}

	@RequestMapping(value = "thread/{threadId}", params = "voteMinus", method = RequestMethod.POST)
	public ModelAndView threadVoteMinus(@PathVariable long threadId) {
		return threadVote(threadId, Vote.MINUS);
	}

	@RequestMapping(value = "thread/{threadId}", params = "voteZero", method = RequestMethod.POST)
	public ModelAndView threadVoteZero(@PathVariable long threadId) {
		return threadVote(threadId, Vote.ZERO);
	}

	private ModelAndView threadVote(long threadId, Vote vote) {
		ThreadVoteMethod method = this.session.getDriver().threads().vote(threadId, vote);
		return threadExecute(threadId, method);
	}

	@RequestMapping(value = "thread/{threadId}", params = "subscribe", method = RequestMethod.POST)
	public ModelAndView threadSubscribe(@PathVariable long threadId) {
		String email = "";
		ThreadSubscribeMethod method = this.session.getDriver().threads().subscribe(threadId, email);
		method.execute();
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "unsubscribe", method = RequestMethod.POST)
	public ModelAndView threadUnsubscribe(@PathVariable long threadId) {
		String email = "";
		ThreadUnsubscribeMethod method = this.session.getDriver().threads().unsubscribe(threadId, email);
		return threadExecute(threadId, method);
	}

	private ModelAndView threadExecute(long threadId, DisqusMethod<?, ?> method) {
		DisqusResponse<?> response = method.execute();
		this.log.debug("Response" + response);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	/**
	 * Post Detail
	 */
	@RequestMapping(value = "post/{postId}", method = RequestMethod.GET)
	public ModelAndView postDetail(@PathVariable long postId) {
		ModelAndView modelAndView = new ModelAndView("disqus/post");
		DisqusResponse<DisqusPost> response = this.session.getDriver().posts().details(postId)
				.addRelated(Related.forum, Related.thread).execute();
		modelAndView.addObject("post", response.getResponse());

		return modelAndView;
	}

	@RequestMapping(value = "post/{postId}", params = "update", method = RequestMethod.POST)
	public ModelAndView postUpdate(@PathVariable long postId, @ModelAttribute DisqusPost post, BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("disqus/post");
		if (binding.hasErrors()) {
			return modelAndView;
		}

		PostUpdateMethod method = this.session.getDriver().posts().update(postId, post.getRaw_message());
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "remove", method = RequestMethod.POST)
	public ModelAndView postDelete(@PathVariable long postId) {
		PostRemoveMethod method = this.session.getDriver().posts().remove(postId);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "restore", method = RequestMethod.POST)
	public ModelAndView postRestore(@PathVariable long postId) {
		PostRestoreMethod method = this.session.getDriver().posts().restore(postId);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "approve", method = RequestMethod.POST)
	public ModelAndView postApprove(@PathVariable long postId) {
		PostApproveMethod method = this.session.getDriver().posts().approve(postId);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "report", method = RequestMethod.POST)
	public ModelAndView postReport(@PathVariable long postId) {
		PostReportMethod method = this.session.getDriver().posts().report(postId);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "spam", method = RequestMethod.POST)
	public ModelAndView postSpam(@PathVariable long postId) {
		PostSpamMethod method = this.session.getDriver().posts().spam(postId);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "votePlus", method = RequestMethod.POST)
	public ModelAndView postVotePlus(@PathVariable long postId) {
		return postVote(postId, Vote.PLUS);
	}

	@RequestMapping(value = "post/{postId}", params = "voteMinus", method = RequestMethod.POST)
	public ModelAndView postVoteMinus(@PathVariable long postId) {
		return postVote(postId, Vote.MINUS);
	}

	@RequestMapping(value = "post/{postId}", params = "voteZero", method = RequestMethod.POST)
	public ModelAndView postVoteZero(@PathVariable long postId) {
		return postVote(postId, Vote.ZERO);
	}

	private ModelAndView postVote(long postId, Vote vote) {
		PostVoteMethod method = this.session.getDriver().posts().vote(postId, vote);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "highlight", method = RequestMethod.POST)
	public ModelAndView postHighlight(@PathVariable long postId) {
		HighlightMethod method = this.session.getDriver().posts().highlight(postId);
		return postExecute(postId, method);
	}

	@RequestMapping(value = "post/{postId}", params = "unhighlight", method = RequestMethod.POST)
	public ModelAndView postUnhighlight(@PathVariable long postId) {
		PostUnhighlightMethod method = this.session.getDriver().posts().unhighlight(postId);
		return postExecute(postId, method);
	}

	private ModelAndView postExecute(long postId, DisqusMethod<?, ?> method) {
		DisqusResponse<?> response = method.execute();
		this.log.debug("Post Response" + response);
		return new ModelAndView("redirect:/disqus/post/" + postId);
	}

	/**
	 * View Threads
	 */
	@RequestMapping(value = "threads", method = RequestMethod.GET)
	public ModelAndView threadList(@ModelAttribute(THREAD_CRITERIA) ThreadSearchCriteria criteria, BindingResult binding,
			@RequestParam(required = false) String forum, @RequestParam(required = false) String cursor) {

		ModelAndView modelAndView = new ModelAndView("disqus/threads");
		modelAndView.addObject("ThreadStates", ThreadState.values());

		if (binding.hasErrors()) {
			return modelAndView;
		}

		//parameter overwrites session criteria
		if (StringUtils.isNotEmpty(forum)) {
			criteria.setForum(forum);
		}
		//now use session criteria value
		if (StringUtils.isNotEmpty(criteria.getForum())) {
			try {
				List<DisqusCategory> categories = this.session.getDriver().category().list(criteria.getForum()).execute()
						.getResponse();
				modelAndView.addObject("categories", categories);
			} catch (DisqusServerException dsx) {
				binding
						.addError(new FieldError(binding.getObjectName(), "forum", "Forum " + criteria.getForum() + " not found"));
				return modelAndView;
			}
		}

		ThreadListMethod listMethod = this.session.getDriver().threads().list();
		listMethod.addRelated(Related.author, Related.category);
		listMethod.setLimit(100);
		listMethod.setOrder(Order.desc);

		List<ThreadState> includes = criteria.getIncludes();
		for (ThreadState threadState : includes) {
			listMethod.addInclude(threadState);
		}

		if (StringUtils.isNotEmpty(criteria.getForum())) {
			listMethod.addForum(criteria.getForum());
		}

		if (criteria.getCategory() != null) {
			listMethod.addCategory(criteria.getCategory());
		}

		if (criteria.getThread() != null) {
			listMethod.addThread(criteria.getThread());
		}

		if (criteria.getSince() != null) {
			listMethod.setSince(criteria.getSince());
		}

		if (StringUtils.isNotBlank(cursor)) {
			listMethod.setCursor(cursor);
		}

		//if no criteria are set - use special :moderated
		if (!criteria.isNotEmpty()) {
			listMethod.addForum(":moderated");
			listMethod.setAccessToken(this.session.getDriver().getApplicationKeys().getAccessToken());
		}

		try {
			DisqusResponse<List<DisqusThread>> response = listMethod.execute();
			modelAndView.addObject("threads", response);
		} catch (DisqusException dx) {
			binding.addError(new ObjectError(binding.getObjectName(), "Disqus Error " + dx.getMessage()));
			return modelAndView;
		}

		return modelAndView;
	}

	/**
	 * Search Threads
	 */
	@RequestMapping(value = "threads", method = RequestMethod.POST)
	public ModelAndView threadsSearch(@ModelAttribute(THREAD_CRITERIA) ThreadSearchCriteria criteria,
			BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("disqus/threads");
		if (binding.hasErrors()) {
			return modelAndView;
		}

		return new ModelAndView("redirect:/disqus/threads");

	}

	/**
	 * View Posts
	 
	@RequestMapping(value = "posts", method = RequestMethod.GET)
	public ModelAndView postList(@ModelAttribute(POST_CRITERIA) PostSearchCriteria criteria, BindingResult binding) {
		ModelAndView modelAndView = new ModelAndView("disqus/posts");

		if (criteria.getForum() == null) {
			criteria.setForum("testblogsnnp"); //FIXME externalize this hardcoded forum id
		}

		try {
			List<DisqusCategory> categories = this.disqus.getDriver().category().list(criteria.getForum()).execute().getResponse();
			modelAndView.addObject("categories", categories);
		} catch (DisqusServerException dsx) {
			binding.addError(new FieldError(binding.getObjectName(), "forum", "This forum does not exist"));
			//return modelAndView;
		}

		modelAndView.addObject("criteria", criteria);
		return modelAndView;
	}
	*/

	/**
	 * Search Posts
	 
	@RequestMapping(value = "posts", method = RequestMethod.POST)
	public ModelAndView postsSearch(@ModelAttribute(POST_CRITERIA) PostSearchCriteria criteria, BindingResult binding) {
		ModelAndView modelAndView = new ModelAndView("disqus/posts");

		if (binding.hasErrors()) {
			this.log.info("" + binding.getAllErrors());
			return modelAndView;
		}
		//TODO perform search

		try {
			List<DisqusCategory> categories = this.disqus.getDriver().category().list(criteria.getForum()).execute().getResponse();
			modelAndView.addObject("categories", categories);
		} catch (DisqusServerException dsx) {
			binding.addError(new FieldError(binding.getObjectName(), "forum", "This forum does not exist"));
			return modelAndView;
		}

		modelAndView.addObject("criteria", criteria);
		return modelAndView;
	}
	*/

}

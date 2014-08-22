package net.anthavio.disquo.browser;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import net.anthavio.disquo.api.ApiBlacklists.BlacklistRemoveBuilder;
import net.anthavio.disquo.api.ApiThreads.ListPostsBuilder;
import net.anthavio.disquo.api.ApiThreads.ListThreadsBuilder;
import net.anthavio.disquo.api.ApiThreads.ThreadUpdateBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.ArgumentConfig.Vote;
import net.anthavio.disquo.api.DisqusException;
import net.anthavio.disquo.api.DisqusPage;
import net.anthavio.disquo.api.DisqusServerException;
import net.anthavio.disquo.api.response.DisqusCategory;
import net.anthavio.disquo.api.response.DisqusFilter;
import net.anthavio.disquo.api.response.DisqusForum;
import net.anthavio.disquo.api.response.DisqusId;
import net.anthavio.disquo.api.response.DisqusImportDetails;
import net.anthavio.disquo.api.response.DisqusModerator;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusUser;
import net.anthavio.disquo.api.response.DisqusVotePost;
import net.anthavio.spring.web.MultiFormatDateEditor;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

		DisqusResponse<List<DisqusForum>> response = this.session.getDriver().users().listForums(new DisqusPage(cursor));

		//listForums.setAccessToken(this.disqus.getDriver().getApplicationKeys().getAccessToken());

		modelAndView.addObject("forums", response);
		return modelAndView;
	}

	@RequestMapping(value = "forum/{forum}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody DisqusForum forumJson(@PathVariable String forum) {
		return this.session.getDriver().forums().details(forum, Related.author).getResponse();
	}

	@RequestMapping(value = "forum/{forum}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody JAXBElement<DisqusForum> forumXml(@PathVariable String forum) {
		DisqusForum forumx = forumJson(forum);
		return new JAXBElement<DisqusForum>(new QName("forum"), DisqusForum.class, forumx);
	}

	@RequestMapping(value = "forum/{forum}", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView forumView(@PathVariable String forum, @RequestParam(required = false) String importsCursor,
			Model model) {
		ModelAndView modelAndView = new ModelAndView("disqus/forum");

		DisqusForum forumx = forumJson(forum);
		modelAndView.addObject("forum", forumx);

		DisqusPage page = new DisqusPage(null);
		page.setLimit(100);
		page.setOrder(Order.desc);
		DisqusResponse<List<DisqusCategory>> categories = this.session.getDriver().forums().listCategories(forum, page);
		modelAndView.addObject("categories", categories);

		try {
			DisqusResponse<List<DisqusImportDetails>> imports = this.session.getDriver().imports().list(forum, importsCursor);
			modelAndView.addObject("imports", imports);

			DisqusResponse<List<DisqusModerator>> moderators = this.session.getDriver().forums().listModerators(forum);
			modelAndView.addObject("moderators", moderators);

			DisqusResponse<List<DisqusFilter>> blacklist = this.session.getDriver().blacklists().list(forum);
			modelAndView.addObject("blacklist", blacklist);

			DisqusResponse<List<DisqusFilter>> whitelist = this.session.getDriver().whitelists().list(forum);
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

		DisqusPage page = new DisqusPage(usersCursor);
		page.setLimit(100);
		DisqusResponse<List<DisqusUser>> users = session.getDriver().forums().listUsers(forum, page);
		model.addAttribute("users", users);

		return forumView(forum, null, model);

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

		DisqusResponse<DisqusForum> details = this.session.getDriver().forums().details(forum, Related.author);
		modelAndView.addObject("forum", details.getResponse());

		DisqusResponse<List<DisqusFilter>> blacklist = this.session.getDriver().blacklists().list(forum);
		modelAndView.addObject("blacklist", blacklist);

		DisqusResponse<List<DisqusFilter>> whitelist = this.session.getDriver().whitelists().list(forum);
		modelAndView.addObject("whitelist", whitelist);
		return modelAndView;
	}

	@RequestMapping(value = "forum/{forum}/blacklist/remove/{filterId}", method = RequestMethod.GET)
	public ModelAndView removeBlacklist(@PathVariable String forum, @PathVariable Long filterId, Model model) {
		BlacklistRemoveBuilder remove = session.getDriver().blacklists().removeBuilder(forum);
		//we don't know type of item, so download blacklist
		List<DisqusFilter> blacklist = session.getDriver().blacklists().list(forum).getResponse();
		for (DisqusFilter filter : blacklist) {
			if (filter.getId().longValue() == filterId.longValue()) {
				if (filter.getType().equals("domain")) {
					remove.domain(filter.getValue());
				} else if (filter.getType().equals("word")) {
					remove.word(filter.getValue());
				} else if (filter.getType().equals("ip")) {
					remove.ip(filter.getValue());
				} else if (filter.getType().equals("user")) {
					remove.user(filter.getUser().getUsername());
				} else if (filter.getType().equals("email")) {
					remove.email(filter.getValue());
				}
			}
		}
		remove.execute();
		return filterList(forum, model);
	}

	@RequestMapping(value = "category/{categoryId}", method = RequestMethod.GET)
	public ModelAndView categoryDetail(@PathVariable long categoryId, @RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/category");
		DisqusResponse<DisqusCategory> response = this.session.getDriver().categories().details(categoryId);
		modelAndView.addObject("category", response.getResponse());

		//CategoryListThreadsMethod threadsMethod = disqus.category().listThreads(categoryId);
		ListThreadsBuilder threadsListMethod = this.session.getDriver().threads().list();
		threadsListMethod.category(categoryId);
		threadsListMethod.related(Related.author, Related.category);
		threadsListMethod.include(ThreadState.ALL);
		threadsListMethod.limit(100);
		threadsListMethod.cursor(cursor);
		DisqusResponse<List<DisqusThread>> builder = threadsListMethod.execute();
		modelAndView.addObject("lastThreads", builder);

		return modelAndView;
	}

	@RequestMapping(value = "user/{user}", method = RequestMethod.GET)
	public ModelAndView userDetail(@PathVariable String user, @RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/user");

		//user may be userName or userId
		Long userId = null;
		try {
			userId = Long.parseLong(user);
		} catch (NumberFormatException nfx) {
			//ok
		}

		DisqusResponse<DisqusUser> details;
		if (userId != null) {
			details = this.session.getDriver().users().details(userId);
		} else {
			details = this.session.getDriver().users().details(user);
		}
		modelAndView.addObject("user", details.getResponse());

		DisqusResponse<List<DisqusForum>> forums;
		if (userId != null) {
			forums = this.session.getDriver().users().listForums(userId, null);
		} else {
			forums = this.session.getDriver().users().listForums(user, null);
		}
		modelAndView.addObject("ownedForums", forums);

		DisqusPage page = new DisqusPage(cursor);
		page.setLimit(100);
		DisqusResponse<List<DisqusPost>> lastPosts = this.session.getDriver().users().listPosts(page).user(userId)
				.related(Related.thread).execute();
		modelAndView.addObject("lastPosts", lastPosts);

		return modelAndView;
	}

	/**
	 * JSON representation is piece of cake, requires just MappingJackson2HttpMessageConverter to be registered
	 */
	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody DisqusThread threadJson(@PathVariable long threadId) {
		DisqusResponse<DisqusThread> details = this.session.getDriver().threads()
				.details(threadId, Related.forum, Related.author, Related.category);
		return details.getResponse();
	}

	/**
	 * XML representation requires MarshallingHttpMessageConverter to be reistered
	 * Because Disqus response classes do not have @XmlRootElement annotations, we need to wrap them into JAXBElement<T>
	 * This also requires Jaxb2Marshaller to have supportJaxbElementClass = true
	 */
	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody JAXBElement<DisqusThread> threadJaxb(@PathVariable long threadId) {
		DisqusThread thread = threadJson(threadId);
		return new JAXBElement<DisqusThread>(new QName("thread"), DisqusThread.class, thread);
	}

	@RequestMapping(value = "thread/{threadId}", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView threadView(@PathVariable long threadId, @RequestParam(required = false) String cursor) {
		ModelAndView modelAndView = new ModelAndView("disqus/thread");

		DisqusThread thread = threadJson(threadId);
		modelAndView.addObject("thread", thread);

		List<DisqusCategory> categories = this.session.getDriver().categories()
				.list(((DisqusForum) thread.getForum()).getId()).getResponse();
		modelAndView.addObject("categories", categories);

		ListPostsBuilder postsMethod = this.session.getDriver().threads().listPosts(threadId);
		postsMethod.include(PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted, PostState.flagged);
		postsMethod.cursor(cursor);
		postsMethod.limit(100);
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
		ThreadUpdateBuilder method = this.session.getDriver().threads().update(threadId);
		method.title(thread.getTitle());
		String message = thread.getMessage();
		if (StringUtils.isNotEmpty(message)) {
			//Disqus is wrapping message: <p>message</p> so remove p tags if present 
			if (message.startsWith("<p>")) {
				message = message.substring(3, message.length() - 4);
			}
			method.message(message);
		}

		method.category(Long.parseLong((String) thread.getCategory()));

		if (thread.getIdentifiers() != null) {
			method.identifier(thread.getIdentifiers().get(0));
		}

		if (StringUtils.isNotEmpty(thread.getLink())) {
			method.url(thread.getLink());
		}

		if (StringUtils.isNotEmpty(thread.getSlug())) {
			method.slug(thread.getSlug());
		}

		method.execute();
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "close", method = RequestMethod.POST)
	public ModelAndView threadClose(@PathVariable long threadId) {
		this.session.getDriver().threads().close(threadId);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "open", method = RequestMethod.POST)
	public ModelAndView threadOpen(@PathVariable long threadId) {
		this.session.getDriver().threads().open(threadId);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "remove", method = RequestMethod.POST)
	public ModelAndView threadDelete(@PathVariable long threadId) {
		this.session.getDriver().threads().remove(threadId);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "restore", method = RequestMethod.POST)
	public ModelAndView threadRestore(@PathVariable long threadId) {
		this.session.getDriver().threads().restore(threadId);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
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
		this.session.getDriver().threads().vote(vote, threadId);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "subscribe", method = RequestMethod.POST)
	public ModelAndView threadSubscribe(@PathVariable long threadId) {
		String email = "";
		this.session.getDriver().threads().subscribe(threadId, email);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	@RequestMapping(value = "thread/{threadId}", params = "unsubscribe", method = RequestMethod.POST)
	public ModelAndView threadUnsubscribe(@PathVariable long threadId) {
		String email = "";
		this.session.getDriver().threads().unsubscribe(threadId, email);
		return new ModelAndView("redirect:/disqus/thread/" + threadId);
	}

	/**
	 * Post JSON
	 */
	@RequestMapping(value = "post/{postId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody DisqusPost postJson(@PathVariable long postId) {
		DisqusResponse<DisqusPost> response = this.session.getDriver().posts()
				.details(postId, Related.forum, Related.thread);
		return response.getResponse();
	}

	/**
	 * Post XML
	 */
	@RequestMapping(value = "post/{postId}", method = RequestMethod.GET, headers = "Accept=application/xml")
	public @ResponseBody JAXBElement<DisqusPost> postXml(@PathVariable long postId) {
		DisqusPost post = postJson(postId);
		return new JAXBElement<DisqusPost>(new QName("post"), DisqusPost.class, post);
	}

	/**
	 * Post Detail
	 */
	@RequestMapping(value = "post/{postId}", method = RequestMethod.GET, headers = "Accept=text/html")
	public ModelAndView postView(@PathVariable long postId) {
		ModelAndView modelAndView = new ModelAndView("disqus/post");
		DisqusPost post = postJson(postId);
		modelAndView.addObject("post", post);

		return modelAndView;
	}

	@RequestMapping(value = "post/{postId}", params = "update", method = RequestMethod.POST)
	public ModelAndView postUpdate(@PathVariable long postId, @ModelAttribute DisqusPost post, BindingResult binding) {

		ModelAndView modelAndView = new ModelAndView("disqus/post");
		if (binding.hasErrors()) {
			return modelAndView;
		}

		this.session.getDriver().posts().update(postId, post.getRaw_message());
		return new ModelAndView("redirect:/disqus/post/" + postId);
	}

	@RequestMapping(value = "post/{postId}", params = "remove", method = RequestMethod.POST)
	public ModelAndView postDelete(@PathVariable long postId) {
		DisqusResponse<List<DisqusId>> method = this.session.getDriver().posts().remove(postId);
		return new ModelAndView("redirect:/disqus/post/" + postId);
	}

	@RequestMapping(value = "post/{postId}", params = "restore", method = RequestMethod.POST)
	public ModelAndView postRestore(@PathVariable long postId) {
		DisqusResponse<List<DisqusId>> method = this.session.getDriver().posts().restore(postId);
		return new ModelAndView("redirect:/disqus/post/" + postId);
	}

	@RequestMapping(value = "post/{postId}", params = "approve", method = RequestMethod.POST)
	public ModelAndView postApprove(@PathVariable long postId) {
		DisqusResponse<List<DisqusId>> method = this.session.getDriver().posts().approve(postId);
		return new ModelAndView("redirect:/disqus/post/" + postId);
	}

	@RequestMapping(value = "post/{postId}", params = "report", method = RequestMethod.POST)
	public ModelAndView postReport(@PathVariable long postId) {
		DisqusResponse<DisqusPost> method = this.session.getDriver().posts().report(postId);
		return new ModelAndView("redirect:/disqus/post/" + postId);
	}

	@RequestMapping(value = "post/{postId}", params = "spam", method = RequestMethod.POST)
	public ModelAndView postSpam(@PathVariable long postId) {
		DisqusResponse<List<DisqusId>> method = this.session.getDriver().posts().spam(postId);
		return new ModelAndView("redirect:/disqus/post/" + postId);
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
		DisqusResponse<DisqusVotePost> method = this.session.getDriver().posts().vote(postId, vote);
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
				List<DisqusCategory> categories = this.session.getDriver().categories().list(criteria.getForum()).getResponse();
				modelAndView.addObject("categories", categories);
			} catch (DisqusServerException dsx) {
				binding
						.addError(new FieldError(binding.getObjectName(), "forum", "Forum " + criteria.getForum() + " not found"));
				return modelAndView;
			}
		}

		ListThreadsBuilder listMethod = this.session.getDriver().threads().list();
		listMethod.related(Related.author, Related.category);
		listMethod.limit(100);
		listMethod.order(Order.desc);

		List<ThreadState> includes = criteria.getIncludes();
		for (ThreadState threadState : includes) {
			listMethod.include(threadState);
		}

		if (StringUtils.isNotEmpty(criteria.getForum())) {
			listMethod.forum(criteria.getForum());
		}

		if (criteria.getCategory() != null) {
			listMethod.category(criteria.getCategory());
		}

		if (criteria.getThread() != null) {
			listMethod.thread(criteria.getThread());
		}

		if (criteria.getSince() != null) {
			listMethod.since(criteria.getSince());
		}

		if (StringUtils.isNotBlank(cursor)) {
			listMethod.cursor(cursor);
		}

		//if no criteria are set - use special :moderated
		if (!criteria.isNotEmpty()) {
			listMethod.forum(":moderated");
			listMethod.accessToken(this.session.getDriver().getApplicationKeys().getAccessToken());
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

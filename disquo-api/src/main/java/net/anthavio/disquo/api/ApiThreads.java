package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ApiPosts.VoteSetter;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.ArgumentConfig.Vote;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApi.IdentitySetter;
import net.anthavio.disquo.api.response.DisqusId;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusVoteThread;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlHeaders;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/threads/
 * 
 * @author martin.vanek
 * 
 *
 */
@HttlApi(uri = "/api/3.0/threads/", setters = IdentitySetter.class)
public interface ApiThreads {

	/*
	 * https://disqus.com/api/docs/threads/close/
	 */

	@HttlCall("POST close.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> close(@HttlVar(name = "thread", required = true) long... thread);

	@HttlCall("POST close.json")
	public DisqusResponse<DisqusId[]> close(@HttlVar(required = true) Identity token,
			@HttlVar(name = "thread", required = true) long... thread);

	@HttlCall("POST close.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> close(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	@HttlCall("POST close.json")
	public DisqusResponse<DisqusId[]> close(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	/*
	 * https://disqus.com/api/docs/threads/open/
	 * 
	 */

	@HttlCall("POST open.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> open(@HttlVar(name = "thread", required = true) long... thread);

	@HttlCall("POST open.json")
	public DisqusResponse<DisqusId[]> open(@HttlVar(required = true) Identity token,
			@HttlVar(name = "thread", required = true) long... thread);

	@HttlCall("POST open.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> open(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	@HttlCall("POST open.json")
	public DisqusResponse<DisqusId[]> open(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	/*
	 * https://disqus.com/api/docs/threads/create/
	 */

	@HttlCall("POST create.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusThread> create(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "title", required = true) String title);

	@HttlCall("POST create.json")
	public DisqusResponse<DisqusThread> create(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "title", required = true) String title);

	@HttlCall("POST create.json")
	@HttlHeaders("X!-AUTH: true")
	public CreateThreadBuilder createBuilder(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "title", required = true) String title);

	@HttlCall("POST create.json")
	public CreateThreadBuilder createBuilder(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "title", required = true) String title);

	public static interface CreateThreadBuilder extends HttlCallBuilder<DisqusResponse<DisqusThread>> {

		public CreateThreadBuilder category(@HttlVar("category") long category);

		public CreateThreadBuilder url(@HttlVar("url") String url);

		public CreateThreadBuilder date(@HttlVar("date") Date date);

		public CreateThreadBuilder message(@HttlVar("message") String message);

		public CreateThreadBuilder identifier(@HttlVar("identifier") String identifier);

		public CreateThreadBuilder slug(@HttlVar("slug") String slug);

	}

	/*
	 * https://disqus.com/api/docs/threads/details/
	 */

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusThread> details(@HttlVar("thread") long thread, @HttlVar("related") Related... related);

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusThread> details(@HttlVar(name = "thread:ident", required = true) String threadIdent,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar("related") Related... related);

	/*
	 * https://disqus.com/api/docs/threads/list/
	 */

	//@Operation("GET list.json")
	//public DisqusResponse<List<DisqusThread>> list(@Param(value = "list", setter = DisqusThreadSetter.class) DisqusThread example);

	@HttlCall("GET list.json")
	public ListThreadsBuilder list();

	public static interface ListThreadsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusThread>>> {

		public ListThreadsBuilder category(@HttlVar("category") long... category);

		public ListThreadsBuilder forum(@HttlVar("forum") String... forum);

		public ListThreadsBuilder thread(@HttlVar("thread") long... thread);

		public ListThreadsBuilder thread(@HttlVar("thread:ident") String... threadIdent);

		public ListThreadsBuilder author(@HttlVar("author") long... author);

		public ListThreadsBuilder author(@HttlVar("author:username") String... username);

		public ListThreadsBuilder since(@HttlVar("since") Date since);

		public ListThreadsBuilder related(@HttlVar("related") Related... related);

		public ListThreadsBuilder include(@HttlVar("include") ThreadState... include);

		public ListThreadsBuilder cursor(@HttlVar("cursor") String cursor);

		public ListThreadsBuilder limit(@HttlVar("limit") int limit);

		public ListThreadsBuilder order(@HttlVar("order") Order order);

		public ListThreadsBuilder accessToken(@HttlVar("access_token") String accessToken);

	}

	/*
	 * https://disqus.com/api/docs/threads/listPosts/
	 */

	@HttlCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@HttlVar("thread") long thread);

	@HttlCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@HttlVar("thread:ident") String threadIdent, @HttlVar("forum") String forum);

	public static interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		public ListPostsBuilder since(@HttlVar("since") Date since);

		public ListPostsBuilder related(@HttlVar("related") Related... related);

		public ListPostsBuilder include(@HttlVar("include") PostState... include);

		public ListPostsBuilder cursor(@HttlVar("cursor") String cursor);

		public ListPostsBuilder limit(@HttlVar("limit") int limit);

		public ListPostsBuilder order(@HttlVar("order") Order order);

	}

	/*
	 * https://disqus.com/api/docs/threads/remove/
	 */

	@HttlCall("POST remove.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> remove(@HttlVar("thread") long... thread);

	@HttlCall("POST remove.json")
	public DisqusResponse<DisqusId[]> remove(@HttlVar(required = true) Identity token, @HttlVar("thread") long... thread);

	@HttlCall("POST remove.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> remove(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	@HttlCall("POST remove.json")
	public DisqusResponse<DisqusId[]> remove(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	/*
	 * https://disqus.com/api/docs/threads/restore/
	 */

	@HttlCall("POST restore.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> restore(@HttlVar(name = "thread", required = true) long... thread);

	@HttlCall("POST restore.json")
	public DisqusResponse<DisqusId[]> restore(@HttlVar(required = true) Identity token,
			@HttlVar(name = "thread", required = true) long... thread);

	@HttlCall("POST restore.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusId[]> restore(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	@HttlCall("POST restore.json")
	public DisqusResponse<DisqusId[]> restore(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	/*
	 * https://disqus.com/api/docs/threads/update/
	 */

	@HttlCall("POST update.json")
	@HttlHeaders("X!-AUTH: true")
	public ThreadUpdateBuilder update(@HttlVar("thread") long thread);

	@HttlCall("POST update.json")
	public ThreadUpdateBuilder update(@HttlVar(required = true) Identity token, @HttlVar("thread") long thread);

	public static interface ThreadUpdateBuilder extends HttlCallBuilder<DisqusResponse<DisqusThread>> {

		public ThreadUpdateBuilder category(@HttlVar("category") long category);

		public ThreadUpdateBuilder forum(@HttlVar("forum") String forum);

		public ThreadUpdateBuilder title(@HttlVar("title") String title);

		public ThreadUpdateBuilder url(@HttlVar("url") String url);

		public ThreadUpdateBuilder author(@HttlVar("author:username") String author);

		public ThreadUpdateBuilder author(@HttlVar("author") long author);

		public ThreadUpdateBuilder message(@HttlVar("message") String message);

		public ThreadUpdateBuilder identifier(@HttlVar("identifier") String identifier);

		public ThreadUpdateBuilder slug(@HttlVar("slug") String slug);

	}

	/**
	 * https://disqus.com/api/docs/threads/set/
	 */
	@HttlCall("GET set.json")
	public DisqusResponse<List<DisqusThread>> set(@HttlVar("thread") long... thread);

	@HttlCall("GET set.json")
	public DisqusResponse<List<DisqusThread>> set(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "thread:ident", required = true) String... threadIdent);

	/**
	 * https://disqus.com/api/docs/threads/vote/
	 */

	@HttlCall("POST vote.json")
	public DisqusResponse<DisqusVoteThread> vote(
			@HttlVar(name = "vote", required = true, setter = VoteSetter.class) Vote vote, @HttlVar("thread") long thread);

	@HttlCall("POST vote.json")
	public DisqusResponse<DisqusVoteThread> vote(
			@HttlVar(name = "vote", required = true, setter = VoteSetter.class) Vote vote,
			@HttlVar("threadLident") String threadIdent, @HttlVar("forum") String forum);

	/**
	 * https://disqus.com/api/docs/threads/subscribe/
	 */

	@HttlCall("POST subscribe.json")
	public void subscribe(@HttlVar("thread") long thread, @HttlVar("email") String email);

	/**
	 * https://disqus.com/api/docs/threads/unsubscribe/
	 */

	@HttlCall("POST unsubscribe.json")
	public void unsubscribe(@HttlVar("thread") long thread, @HttlVar("email") String email);
}

package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.response.DisqusId;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.disquo.api.response.DisqusThread;
import net.anthavio.disquo.api.response.DisqusVoteThread;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * https://disqus.com/api/docs/threads/
 * 
 * @author martin.vanek
 * 
 *
 */
@RestApi("/threads/")
public interface ApiThreads {

	/**
	 * https://disqus.com/api/docs/threads/close/
	 */

	@RestCall("POST close.json")
	public DisqusResponse<DisqusId[]> close(@RestVar("thread") long... thread);

	@RestCall("POST close.json")
	public DisqusResponse<DisqusId[]> close(@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "thread:ident", required = true) String... threadIdent);

	/**
	 * https://disqus.com/api/docs/threads/open/
	 * 
	 * https://disqus.com/api/docs/threads/restore/
	 */

	@RestCall("POST open.json")
	public DisqusResponse<DisqusId[]> open(@RestVar("thread") long... thread);

	@RestCall("POST open.json")
	public DisqusResponse<DisqusId[]> open(@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "thread:ident", required = true) String... threadIdent);

	/**
	 * https://disqus.com/api/docs/threads/create/
	 */

	@RestCall("POST create.json")
	public CreateThreadBuilder create(@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "title", required = true) String title);

	public static interface CreateThreadBuilder extends HttlCallBuilder<DisqusResponse<DisqusThread>> {

		public CreateThreadBuilder category(@RestVar("category") long category);

		public CreateThreadBuilder url(@RestVar("url") String url);

		public CreateThreadBuilder date(@RestVar("date") Date date);

		public CreateThreadBuilder identifier(@RestVar("identifier") String identifier);

		public CreateThreadBuilder slug(@RestVar("slug") String slug);

	}

	/**
	 * https://disqus.com/api/docs/threads/details/
	 */

	@RestCall("GET details.json")
	public DisqusResponse<DisqusThread> details(@RestVar("thread") long thread, @RestVar("related") Related... related);

	@RestCall("GET details.json")
	public DisqusResponse<DisqusThread> details(@RestVar(name = "thread:ident", required = true) String threadIdent,
			@RestVar(name = "forum", required = true) String forum, @RestVar("related") Related... related);

	/**
	 * https://disqus.com/api/docs/threads/list/
	 */

	//@Operation("GET list.json")
	//public DisqusResponse<List<DisqusThread>> list(@Param(value = "list", setter = DisqusThreadSetter.class) DisqusThread example);

	@RestCall("GET list.json")
	public ListThreadsBuilder list();

	public static interface ListThreadsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusThread>>> {

		public ListThreadsBuilder category(@RestVar("category") long... category);

		public ListThreadsBuilder forum(@RestVar("forum") String... forum);

		public ListThreadsBuilder thread(@RestVar("thread") long... thread);

		public ListThreadsBuilder thread(@RestVar("thread:ident") String... threadIdent);

		public ListThreadsBuilder author(@RestVar("author") long... author);

		public ListThreadsBuilder author(@RestVar("author:username") String... username);

		public ListThreadsBuilder since(@RestVar("since") Date since);

		public ListThreadsBuilder related(@RestVar("related") Related... related);

		public ListThreadsBuilder include(@RestVar("include") ThreadState... include);

		public ListThreadsBuilder cursor(@RestVar("cursor") String cursor);

		public ListThreadsBuilder limit(@RestVar("limit") int limit);

		public ListThreadsBuilder order(@RestVar("order") Order order);

	}

	/**
	 * https://disqus.com/api/docs/threads/listPosts/
	 */

	@RestCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@RestVar("thread") long thread);

	@RestCall("GET listPosts.json")
	public ListPostsBuilder listPosts(@RestVar("thread:ident") String threadIdent, @RestVar("forum") String forum);

	public static interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		public ListPostsBuilder since(@RestVar("since") Date since);

		public ListPostsBuilder related(@RestVar("related") Related... related);

		public ListPostsBuilder include(@RestVar("include") PostState... include);

		public ListPostsBuilder cursor(@RestVar("cursor") String cursor);

		public ListPostsBuilder limit(@RestVar("limit") int limit);

		public ListPostsBuilder order(@RestVar("order") Order order);

	}

	/**
	 * https://disqus.com/api/docs/threads/remove/
	 */

	@RestCall("POST remove.json")
	public DisqusResponse<DisqusId[]> remove(@RestVar("thread") long... thread);

	@RestCall("POST remove.json")
	public DisqusResponse<DisqusId[]> remove(@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "thread:ident", required = true) String... threadIdent);

	/**
	 * https://disqus.com/api/docs/threads/update/
	 */
	@RestCall("POST update.json")
	public ThreadUpdateBuilder update(@RestVar("thread") long thread);

	public static interface ThreadUpdateBuilder extends HttlCallBuilder<DisqusResponse<DisqusThread>> {

		public ThreadUpdateBuilder category(@RestVar("category") String category);

		public ThreadUpdateBuilder forum(@RestVar("forum") String forum);

		public ThreadUpdateBuilder title(@RestVar("title") String title);

		public ThreadUpdateBuilder url(@RestVar("url") String url);

		public ThreadUpdateBuilder author(@RestVar("author:username") String author);

		public ThreadUpdateBuilder author(@RestVar("author") long author);

		public ThreadUpdateBuilder message(@RestVar("message") String message);

		public ThreadUpdateBuilder identifier(@RestVar("identifier") String identifier);

		public ThreadUpdateBuilder slug(@RestVar("slug") String slug);

	}

	/**
	 * https://disqus.com/api/docs/threads/set/
	 */
	@RestCall("GET set.json")
	public DisqusResponse<List<DisqusThread>> set(@RestVar("thread") long... thread);

	@RestCall("GET set.json")
	public DisqusResponse<List<DisqusThread>> set(@RestVar(name = "forum", required = true) String forum,
			@RestVar(name = "thread:ident", required = true) String... threadIdent);

	/**
	 * https://disqus.com/api/docs/threads/vote/
	 */

	@RestCall("POST vote.json")
	public DisqusResponse<DisqusVoteThread> vote(@RestVar("vote") int vote, @RestVar("thread") long thread);

	@RestCall("POST vote.json")
	public DisqusResponse<DisqusVoteThread> vote(@RestVar("vote") int vote, @RestVar("threadLident") String threadIdent,
			@RestVar("forum") String forum);

	/**
	 * https://disqus.com/api/docs/threads/subscribe/
	 */

	@RestCall("POST subscribe.json")
	public void subscribe(@RestVar("thread") long thread, @RestVar("email") String email);

	/**
	 * https://disqus.com/api/docs/threads/unsubscribe/
	 */

	@RestCall("POST unsubscribe.json")
	public void unsubscribe(@RestVar("thread") long thread, @RestVar("email") String email);
}

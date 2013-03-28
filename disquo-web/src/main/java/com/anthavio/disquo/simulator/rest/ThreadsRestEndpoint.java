package com.anthavio.disquo.simulator.rest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.DisqusServerException;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.response.DisqusUser;
import com.anthavio.disquo.simulator.JaxRsUtil;
import com.anthavio.disquo.simulator.StoreService.DisqusApplication;

@Path("/api/3.0/threads")
@Scope("request")
@Component
public class ThreadsRestEndpoint extends BaseRestEndpoint {

	@GET
	@Produces("text/plain")
	public String get() {
		return "Disqus Threads REST Endpoint simulator";
	}

	@POST
	@Path("create.json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(MultivaluedMap<String, String> params) throws Exception {
		try {

			DisqusMethodConfig mconfig = DisqusMethodConfig.Threads.create;
			DisqusApplication application = validate(params, mconfig);

			String forum = JaxRsUtil.OptStr(params, "forum");
			String title = JaxRsUtil.OptStr(params, "title");
			Long category = JaxRsUtil.OptLong(params, "category");
			String url = JaxRsUtil.OptStr(params, "url");
			Date date = JaxRsUtil.OptDate(params, "date", Disqus.DATE_FORMAT);
			String message = JaxRsUtil.OptStr(params, "message");
			String identifier = JaxRsUtil.OptStr(params, "identifier");
			String slug = JaxRsUtil.OptStr(params, "slug");

			DisqusThread thread = new DisqusThread();
			thread.setForum(forum);
			thread.setTitle(title);
			thread.setCategory(category);
			if (date == null) {
				date = new Date();
			}
			thread.setCreatedAt(new Date());
			//url?
			thread.setMessage(message);
			thread.setIdentifiers(Arrays.asList(identifier));
			thread.setSlug(slug);

			DisqusUser author = getAuthor(params, mconfig, application);
			thread.setAuthor(author);
			//XXX state - we need to have some transient fields for that
			thread.setInitialValues();

			store.createThread(thread);

			DisqusResponse<DisqusThread> response = new DisqusResponse<DisqusThread>(thread);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}

	@GET
	@Path("details.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response details(@Context UriInfo uri) {
		try {
			MultivaluedMap<String, String> params = uri.getQueryParameters();
			validate(params, DisqusMethodConfig.Threads.details);

			long threadId = JaxRsUtil.ReqLong(params, "thread");

			DisqusThread origin = store.getThread(threadId);
			if (origin == null) {
				throw new DisqusServerException(400, 2, "Invalid argument, 'thread' : Unable to find post with id '" + threadId
						+ "'");
			}

			//String forum = JaxRsUtil.OptStr(params, "forum");

			List<Related> related = JaxRsUtil.OptList(params, "related", Related.class);

			DisqusThread copy = copier.copy(origin, related);

			DisqusResponse<DisqusThread> response = new DisqusResponse<DisqusThread>(copy);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}

	@GET
	@Path("list.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@Context UriInfo uri) {
		try {
			MultivaluedMap<String, String> params = uri.getQueryParameters();
			validate(params, DisqusMethodConfig.Threads.list);

			DisqusResponse<List<DisqusThread>> response = search.findThreads(params);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}

	@GET
	@Path("listPosts.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listPosts(@Context UriInfo uri) {
		try {
			MultivaluedMap<String, String> params = uri.getQueryParameters();
			validate(params, DisqusMethodConfig.Threads.listPosts);

			Long thread = JaxRsUtil.ReqLong(params, "thread");
			String forum = JaxRsUtil.OptStr(params, "forum");

			DisqusResponse<List<DisqusPost>> response = search.findPosts(params);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}
}

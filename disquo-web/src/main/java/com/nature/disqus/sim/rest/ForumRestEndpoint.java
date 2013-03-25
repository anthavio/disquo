package com.nature.disqus.sim.rest;

import java.util.Arrays;
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
import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.DisqusServerException;
import com.anthavio.disquo.api.response.DisqusForum;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.response.DisqusUser;
import com.nature.disqus.sim.JaxRsUtil;
import com.nature.disqus.sim.StoreService.DisqusApplication;

/**
 * 
 * @author martin.vanek
 *
 */
@Path("/api/3.0/forums")
@Scope("request")
@Component
public class ForumRestEndpoint extends BaseRestEndpoint {

	@GET
	@Produces("text/plain")
	public String get() {
		return "Disqus Forums REST Endpoint simulator";
	}

	@POST
	@Path("create.json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(MultivaluedMap<String, String> params) throws Exception {
		try {

			DisqusMethodConfig mconfig = DisqusMethodConfig.Forums.create;
			DisqusApplication application = validate(params, mconfig);

			String website = JaxRsUtil.OptStr(params, "website");
			String name = JaxRsUtil.OptStr(params, "name");
			String short_name = JaxRsUtil.OptStr(params, "short_name");

			DisqusUser author = getAuthor(params, mconfig, application);

			DisqusForum forum = new DisqusForum();
			forum.setId(short_name);
			forum.setName(name);
			forum.setAuthor(author);
			forum.setFounder(author.getId());
			forum.setUrl(Arrays.asList(website));

			store.createForum(forum);

			DisqusResponse<DisqusForum> response = new DisqusResponse<DisqusForum>(forum);
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
			validate(params, DisqusMethodConfig.Forums.details);

			String forumId = JaxRsUtil.ReqStr(params, "forum");

			DisqusForum origin = store.getForum(forumId);
			if (origin == null) {
				throw new DisqusServerException(400, 2, "Invalid argument, 'forum' : Unable to find forum with id '" + forumId
						+ "'");
			}

			List<Related> related = JaxRsUtil.OptList(params, "related", Related.class);

			DisqusForum copy = copier.copy(origin, related);

			DisqusResponse<DisqusForum> response = new DisqusResponse<DisqusForum>(copy);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}

	@GET
	@Path("listThreads.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listThreads(@Context UriInfo uri) {
		try {
			MultivaluedMap<String, String> params = uri.getQueryParameters();
			validate(params, DisqusMethodConfig.Forums.listThreads);

			String forumId = JaxRsUtil.ReqStr(params, "forum");

			DisqusResponse<List<DisqusThread>> response = search.findThreads(params);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}

	@GET
	@Path("listPosts.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@Context UriInfo uri) {
		try {
			MultivaluedMap<String, String> params = uri.getQueryParameters();
			validate(params, DisqusMethodConfig.Forums.listPosts);

			String forumId = JaxRsUtil.ReqStr(params, "forum");

			DisqusResponse<List<DisqusPost>> response = search.findPosts(params);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}
}

package com.anthavio.disquo.simulator.rest;

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

import org.apache.commons.lang.StringUtils;
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

/**
 * 
 * @author martin.vanek
 *
 */
@Path("/api/3.0/posts")
@Scope("request")
@Component
public class PostsRestEndpoint extends BaseRestEndpoint {

	@GET
	@Produces("text/plain")
	public String get() {
		return "Disqus Posts REST Endpoint simulator";
	}

	@POST
	@Path("create.json")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(MultivaluedMap<String, String> params) {
		try {
			DisqusMethodConfig mconfig = DisqusMethodConfig.Posts.create;
			DisqusApplication application = validate(params, mconfig);

			Long threadId = JaxRsUtil.OptLong(params, "thread");
			String message = JaxRsUtil.ReqStr(params, "message");
			Long parent = JaxRsUtil.OptLong(params, "parent");
			String author_email = JaxRsUtil.OptStr(params, "author_email");
			String author_name = JaxRsUtil.OptStr(params, "author_name");
			String author_url = JaxRsUtil.OptStr(params, "author_url");
			String state = JaxRsUtil.OptStr(params, "state");
			Date date = JaxRsUtil.OptDate(params, "date", Disqus.DATE_FORMAT);
			String ip_address = JaxRsUtil.OptStr(params, "ip_address");

			DisqusUser author = getAuthor(params, mconfig, application, false);

			if (StringUtils.isNotBlank(author_email)) {
				// anonymous user
				DisqusUser anonymous = store.getUserByEmail(author_email);
				if (anonymous == null) {
					anonymous = store.createUser(author_email, author_name, author_url);
				}
			} else if (author == null) {
				throw new DisqusServerException(400, 2,
						"You must authenticate the user or provide author_name and author_email");
			}

			if (parent != null) {
				DisqusPost detail = store.postDetail(parent);
				if (detail == null) {
					throw new DisqusServerException(400, 2, "Invalid argument, 'parent' : Unable to find post with id '" + parent
							+ "'");
				}
			}
			DisqusThread thread = store.getThread(threadId);
			if (thread == null) {
				throw new DisqusServerException(400, 2, "Invalid argument, 'thread' : Unable to find thread with id '"
						+ threadId + "'");
			}

			DisqusPost post = new DisqusPost();
			post.setAuthor(author);
			post.setThread(threadId);
			post.setParent(parent);
			post.setMessage("<p>" + message + "</p>");
			post.setRaw_message(message);
			post.setIpAddress(ip_address);

			if (date == null) {
				date = new Date();
			}
			post.setCreatedAt(date);

			//XXX state

			post.setInitialValues();

			store.createPost(post);

			DisqusResponse<DisqusPost> response = new DisqusResponse<DisqusPost>(post);
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
			validate(params, DisqusMethodConfig.Posts.details);

			long postId = JaxRsUtil.ReqLong(params, "post");

			DisqusPost origin = store.getPost(postId);
			if (origin == null) {
				throw new DisqusServerException(400, 2, "Invalid argument, 'post' : Unable to find post with id '" + postId
						+ "'");
			}

			List<Related> related = JaxRsUtil.OptList(params, "related", Related.class);
			DisqusPost detail = copier.copy(origin, related);

			DisqusResponse<DisqusPost> response = new DisqusResponse<DisqusPost>(detail);
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
			validate(params, DisqusMethodConfig.Posts.list);
			DisqusResponse<List<DisqusPost>> response = search.findPosts(params);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}
	}
}

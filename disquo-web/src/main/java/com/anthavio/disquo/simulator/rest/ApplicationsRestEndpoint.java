package com.anthavio.disquo.simulator.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.anthavio.disquo.api.DisqusMethodConfig;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.simulator.JaxRsUtil;

/**
 * 
 * @author martin.vanek
 *
 */
@Path("/api/3.0/applications")
@Scope("request")
@Component
public class ApplicationsRestEndpoint extends BaseRestEndpoint {

	@GET
	@Path("listUsage.json")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(@Context UriInfo uri) {
		try {
			MultivaluedMap<String, String> params = uri.getQueryParameters();
			validate(params, DisqusMethodConfig.Applications.listUsage);

			String application = JaxRsUtil.OptStr(params, "application");
			Integer days = JaxRsUtil.OptInt(params, "days");

			DisqusResponse<Object[]> response = new DisqusResponse<Object[]>(0, new Object[] { "2000-01-01T11:11:11", 0 },
					null);
			return getResponse(response);

		} catch (Exception x) {
			return getErrorResponse(x);
		}

	}
}

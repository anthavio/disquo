package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * 
 * @author martin.vanek
 *
 */
public interface ApiApplications {

	@RestCall("GET /api/3.0/applications/listUsage.json")
	public DisqusResponse<List<String[]>> listUsage(@RestVar(name = "access_token", required = true) String token,
			@RestVar("application") Integer application, @RestVar("days") Integer days);
}

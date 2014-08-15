package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;

/**
 * 
 * @author martin.vanek
 *
 */
public interface ApiApplications {

	@HttlCall("GET /api/3.0/applications/listUsage.json")
	public DisqusResponse<List<String[]>> listUsage(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar("application") Integer application, @HttlVar("days") Integer days);
}

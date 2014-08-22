package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApi.IdentitySetter;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlHeaders;
import net.anthavio.httl.api.HttlVar;

/**
 * 
 * @author martin.vanek
 *
 */
@HttlApi(uri = "/api/3.0/applications/", setters = IdentitySetter.class)
public interface ApiApplications {

	@HttlCall("GET listUsage.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<String[]>> listUsage();

	@HttlCall("GET listUsage.json")
	public DisqusResponse<List<String[]>> listUsage(@HttlVar(required = true) Identity token);

	@HttlCall("GET listUsage.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<String[]>> listUsage(@HttlVar("application") Integer application,
			@HttlVar("days") Integer days);

	@HttlCall("GET listUsage.json")
	public DisqusResponse<List<String[]>> listUsage(@HttlVar(required = true) Identity token,
			@HttlVar("application") Integer application, @HttlVar("days") Integer days);
}

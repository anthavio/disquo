package net.anthavio.disquo.api;

import java.util.List;

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
@HttlApi("/applications/")
public interface ApiApplications {

	@HttlCall("GET listUsage.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<String[]>> listUsage();

	@HttlCall("GET listUsage.json")
	public DisqusResponse<List<String[]>> listUsage(@HttlVar(name = "access_token", required = true) String token);

	@HttlCall("GET listUsage.json")
	public DisqusResponse<List<String[]>> listUsage(@HttlVar(name = "access_token", required = true) String token,
			@HttlVar("application") Integer application, @HttlVar("days") Integer days);
}

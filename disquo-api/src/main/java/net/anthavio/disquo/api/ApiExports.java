package net.anthavio.disquo.api;

import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * https://disqus.com/api/docs/exports/
 * 
 * @author vanek
 *
 */
@RestApi("/exports/")
public interface ApiExports {

	@RestCall("POST exportForum.json")
	public DisqusResponse<String> checkUsername(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String forum);
}

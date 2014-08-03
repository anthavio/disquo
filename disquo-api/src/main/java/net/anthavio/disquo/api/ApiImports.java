package net.anthavio.disquo.api;

import java.util.List;
import java.util.Map;

import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.RestApi;
import net.anthavio.httl.api.RestCall;
import net.anthavio.httl.api.RestVar;

/**
 * https://disqus.com/api/docs/imports/
 * 
 * @author vanek
 *
 */
@RestApi("/imports/")
public interface ApiImports {

	@RestCall("GET details.json")
	public DisqusResponse<Map> details(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String forum, @RestVar(name = "group", required = true) String group);

	@RestCall("GET list.json")
	public DisqusResponse<List<Map>> list(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String forum, @RestVar("cursor") String cursor);

}

package net.anthavio.disquo.api;

import java.util.List;
import java.util.Map;

import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/imports/
 * 
 * @author vanek
 *
 */
@HttlApi("/imports/")
public interface ApiImports {

	@HttlCall("GET details.json")
	public DisqusResponse<Map> details(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "group", required = true) String group);

	@HttlCall("GET list.json")
	public DisqusResponse<List<Map>> list(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar("cursor") String cursor);

}

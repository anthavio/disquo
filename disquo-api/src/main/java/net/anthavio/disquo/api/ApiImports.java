package net.anthavio.disquo.api;

import java.util.List;

import net.anthavio.disquo.api.response.DisqusImportDetails;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlHeaders;
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
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<DisqusImportDetails> details(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "group", required = true) String group);

	@HttlCall("GET details.json")
	public DisqusResponse<DisqusImportDetails> details(
			@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "group", required = true) String group);

	@HttlCall("GET list.json")
	@HttlHeaders("X!-AUTH: true")
	public DisqusResponse<List<DisqusImportDetails>> list(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar("cursor") String cursor);

	@HttlCall("GET list.json")
	public DisqusResponse<List<DisqusImportDetails>> list(
			@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar("cursor") String cursor);

}

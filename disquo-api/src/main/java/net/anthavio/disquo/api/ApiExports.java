package net.anthavio.disquo.api;

import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/exports/
 * 
 * @author vanek
 *
 */
@HttlApi("/exports/")
public interface ApiExports {

	/**
	 * https://www.disqus.com/admin/discussions/export/
	 * 
	 * https://help.disqus.com/customer/portal/articles/472149-comments-export
	 */
	@HttlCall("POST exportForum.json")
	public DisqusResponse<Void> exportForum(@HttlVar(name = "access_token", required = true) String access_token,
			@HttlVar(name = "forum", required = true) String forum);
}

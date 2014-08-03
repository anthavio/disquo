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

	/**
	 * https://www.disqus.com/admin/discussions/export/
	 * 
	 * https://help.disqus.com/customer/portal/articles/472149-comments-export
	 */
	@RestCall("POST exportForum.json")
	public DisqusResponse<Void> exportForum(@RestVar(name = "access_token", required = true) String access_token,
			@RestVar(name = "forum", required = true) String forum);
}

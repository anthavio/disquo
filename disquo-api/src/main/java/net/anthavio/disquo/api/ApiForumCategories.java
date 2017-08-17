package net.anthavio.disquo.api;

import net.anthavio.disquo.api.response.DisqusForumCategory;
import net.anthavio.disquo.api.response.DisqusResponse;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlVar;

import java.util.List;

/**
 * https://disqus.com/api/docs/forumCategories/
 *
 * @author vanek
 *
 */
@HttlApi(uri = "/api/3.0/forumCategories")
public interface ApiForumCategories {

    /**
     * Returns forum category details.
     */
    @HttlCall("GET details.json")
    public DisqusResponse<DisqusForumCategory> details(@HttlVar(name = "forumCategory", required = true) Integer forumCategory);

    /**
     * Returns a list of forum categories
     */
    @HttlCall("GET list.json")
    public DisqusResponse<List<DisqusForumCategory>> list();
}

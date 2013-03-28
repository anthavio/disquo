package com.anthavio.disquo.api.forums;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.DisqusFeatureGroup;
import com.anthavio.disquo.api.QUser;

/**
 * http://disqus.com/api/docs/forums/
 * 
 * @author martin.vanek
 *
 */
public class DisqusForumsGroup extends DisqusFeatureGroup {

	public DisqusForumsGroup(Disqus disqus) {
		super(disqus);
	}

	public AddModeratorMethod addModerator(String forum, QUser user) {
		return new AddModeratorMethod(disqus).setForum(forum).setUser(user);
	}

	public RemoveModeratorMethod removeModerator(String forum, QUser user) {
		return new RemoveModeratorMethod(disqus).setForum(forum).setModerator(user);
	}

	public ForumCreateMethod create(String id, String website, String name) {
		return new ForumCreateMethod(disqus).setShortName(id).setName(name).setWebsite(website);
	}

	public ForumDetailsMethod details(String forum) {
		return new ForumDetailsMethod(disqus).setForum(forum);
	}

	public ForumListCategoriesMethod listCategories(String forum) {
		return new ForumListCategoriesMethod(disqus).setForum(forum);
	}

	public ForumListModeratorsMethod listModerators(String forum) {
		return new ForumListModeratorsMethod(disqus).setForum(forum);
	}

	public ForumListPostsMethod listPosts(String forum) {
		return new ForumListPostsMethod(disqus).setForum(forum);
	}

	public ForumListThreadsMethod listThreads(String forum) {
		return new ForumListThreadsMethod(disqus).setForum(forum);
	}

	public ForumListUsersMethod listUsers(String forum) {
		return new ForumListUsersMethod(disqus).setForum(forum);
	}

	public ForumListMostActiveUsersMethod listMostActiveUsers(String forum) {
		return new ForumListMostActiveUsersMethod(disqus).setForum(forum);
	}

	public ForumListMostLikedUsersMethod listMostLikedUsers(String forum) {
		return new ForumListMostLikedUsersMethod(disqus).setForum(forum);
	}

}

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
		AddModeratorMethod method = new AddModeratorMethod(disqus);
		method.setForum(forum);
		method.setUser(user);
		return method;
	}

	public RemoveModeratorMethod removeModerator(String forum, QUser user) {
		RemoveModeratorMethod method = new RemoveModeratorMethod(disqus);
		method.setForum(forum);
		method.setModerator(user);
		return method;
	}

	public ForumCreateMethod create(String id, String website, String name) {
		return new ForumCreateMethod(disqus).setShortName(id).setName(name).setWebsite(website);
	}

	public ForumDetailsMethod details(String forum) {
		ForumDetailsMethod method = new ForumDetailsMethod(disqus);
		method.setForum(forum);
		return method;
	}

	public ForumListCategoriesMethod listCategories(String forum) {
		ForumListCategoriesMethod method = new ForumListCategoriesMethod(disqus);
		method.setForum(forum);
		return method;
	}

	public ForumListModeratorsMethod listModerators(String forum) {
		ForumListModeratorsMethod method = new ForumListModeratorsMethod(disqus);
		method.setForum(forum);
		return method;
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

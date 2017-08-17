package net.anthavio.disquo.api;

import java.util.Date;
import java.util.List;

import net.anthavio.disquo.api.ApiThreads.ListThreadsBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.ArgumentConfig.ThreadState;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusApi.IdentitySetter;
import net.anthavio.disquo.api.response.*;
import net.anthavio.httl.api.HttlApi;
import net.anthavio.httl.api.HttlCall;
import net.anthavio.httl.api.HttlCallBuilder;
import net.anthavio.httl.api.HttlHeaders;
import net.anthavio.httl.api.HttlVar;

/**
 * https://disqus.com/api/docs/forums/
 * 
 * @author martin.vanek
 *
 */
@HttlApi(uri = "/api/3.0/forums/", setters = IdentitySetter.class)
public interface ApiForums {

	/*
	 * https://disqus.com/api/docs/forums/addModerator/
	 */

	@HttlCall("POST addModerator.json")
	@HttlHeaders("X!-AUTH: true")
	DisqusResponse<DisqusId> addModerator(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "user:username", required = true) String username);

	@HttlCall("POST addModerator.json")
	DisqusResponse<DisqusId> addModerator(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "user:username", required = true) String username);

	@HttlCall("POST addModerator.json")
	@HttlHeaders("X!-AUTH: true")
	DisqusResponse<DisqusId> addModerator(@HttlVar(name = "forum", required = true) String forum,
			@HttlVar(name = "user", required = true) long user);

	@HttlCall("POST addModerator.json")
	DisqusResponse<DisqusId> addModerator(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String forum, @HttlVar(name = "user", required = true) long user);

	/*
	 * https://disqus.com/api/docs/forums/removeModerator/
	 */
	//use listModerators to get moderator id
	@HttlCall("POST removeModerator.json")
	DisqusResponse<DisqusId> removeModerator(@HttlVar(required = true) Identity token,
			@HttlVar(name = "moderator", required = true) long moderator);

	@HttlCall("POST removeModerator.json")
	@HttlHeaders("X!-AUTH: true")
	DisqusResponse<DisqusId> removeModerator(@HttlVar(name = "moderator", required = true) long moderator);

	/*
	 * https://disqus.com/api/docs/forums/create/
	 */

	@HttlCall("POST create.json")
	DisqusResponse<DisqusForum> create(@HttlVar(required = true) Identity token,
			@HttlVar(name = "name", required = true) String name,
			@HttlVar(name = "short_name", required = true) String short_name,
			@HttlVar(name = "website", required = true) String website);

	@HttlCall("POST create.json")
	@HttlHeaders("X!-AUTH: true")
	DisqusResponse<DisqusForum> create(@HttlVar(name = "name", required = true) String name,
			@HttlVar(name = "short_name", required = true) String short_name,
			@HttlVar(name = "website", required = true) String website);

	/*
	 * https://disqus.com/api/docs/forums/details/
	 */

	@HttlCall("GET details.json")
	DisqusResponse<DisqusForum> details(@HttlVar(name = "forum", required = true) String short_name,
											  @HttlVar("related") Related... related);

	@HttlCall("GET listCategories.json")
	DisqusResponse<List<DisqusCategory>> listCategories(
			@HttlVar(name = "forum", required = true) String short_name, @HttlVar DisqusPage page);

	//list...

	@HttlCall("GET listModerators.json")
	@HttlHeaders("X!-AUTH: true")
	DisqusResponse<List<DisqusModerator>> listModerators(
			@HttlVar(name = "forum", required = true) String short_name);

	@HttlCall("GET listModerators.json")
	DisqusResponse<List<DisqusModerator>> listModerators(@HttlVar(required = true) Identity token,
			@HttlVar(name = "forum", required = true) String short_name);

	@HttlCall("GET listMostActiveUsers.json")
	DisqusResponse<List<DisqusUser>> listMostActiveUsers(
			@HttlVar(name = "forum", required = true) String short_name, @HttlVar DisqusPage page);

	@HttlCall("GET listMostLikedUsers.json")
	DisqusResponse<List<DisqusUser>> listMostLikedUsers(
			@HttlVar(name = "forum", required = true) String short_name, @HttlVar DisqusPage page);

	/*
	 * https://disqus.com/api/docs/forums/listPosts/
	 */

	@HttlCall("GET listPosts.json")
	DisqusResponse<List<DisqusPost>> listPosts(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar DisqusPage page);

	@HttlCall("GET listPosts.json")
	ListPostsBuilder listPosts(@HttlVar(name = "forum", required = true) String short_name);

	interface ListPostsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusPost>>> {

		ListPostsBuilder since(Date since);

		ListPostsBuilder since(Long since);

		ListPostsBuilder related(Related... related);

		ListPostsBuilder cursor(String cursor);

		ListPostsBuilder limit(int limit);

		ListPostsBuilder query(String query);

		ListPostsBuilder include(PostState... include);

		ListPostsBuilder order(Order order);

	}

	/*
	 * https://disqus.com/api/docs/forums/listThreads/
	 */

	@HttlCall("GET listThreads.json")
	DisqusResponse<List<DisqusThread>> listThreads(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar DisqusPage page);

	@HttlCall("GET listThreads.json")
	ListThreadsBuilder listThreads(@HttlVar(name = "forum", required = true) String short_name);

	interface ListThreadsBuilder extends HttlCallBuilder<DisqusResponse<List<DisqusThread>>> {

		ListThreadsBuilder thread(@HttlVar("thread") long... thread);

		ListThreadsBuilder threadIdent(@HttlVar("thread:ident") String... threadIdent);
		
		ListThreadsBuilder threadLink(@HttlVar("thread:link") String... threadLink);
		
		ListThreadsBuilder since(Date since);

		ListThreadsBuilder since(Long since);

		ListThreadsBuilder related(Related... related);

		ListThreadsBuilder cursor(String cursor);

		ListThreadsBuilder limit(int limit);

		ListThreadsBuilder query(String query);

		ListThreadsBuilder include(ThreadState... include);

		ListThreadsBuilder order(Order order);
	}

	/*
	 * https://disqus.com/api/docs/forums/listUsers/
	 */

	@HttlCall("GET listUsers.json")
	DisqusResponse<List<DisqusUser>> listUsers(@HttlVar(name = "forum", required = true) String short_name,
			@HttlVar DisqusPage page);


	@HttlCall("POST update.json")
	@HttlHeaders("X!-AUTH: true")
	UpdateForumBuilder update(@HttlVar(name = "forum", required = true) String forum);

	interface UpdateForumBuilder extends HttlCallBuilder<DisqusResponse<DisqusForum>> {


		UpdateForumBuilder guidelines(String guidelines);

		UpdateForumBuilder description(String description);

		UpdateForumBuilder name(String name);

		UpdateForumBuilder website(String website);

		UpdateForumBuilder commentsLinkOne(String value);

		UpdateForumBuilder commentsLinkZero(String value);

		UpdateForumBuilder commentsLinkMultiple(String value);

		UpdateForumBuilder commentPolicyText(String commentPolicyText);

		/**
		 * 1: OLDEST, 2: NEWEST, 4: HOT
		 */
		UpdateForumBuilder sort(Integer sort);

		//flagThreshold

		/**
		 * auto, dark, light
		 */
		UpdateForumBuilder colorScheme(String colorScheme);

		/**
		 * 1: LOW1
		 * 2: LOW2
		 * 3: AVERAGE
		 * 4: HIGH
		 */
		UpdateForumBuilder unapproveReputationLevel(Integer unapproveReputationLevel);

		/**
		 * auto, serif, sans-serif
		 */
		UpdateForumBuilder typeface(String typeface);

		UpdateForumBuilder twitterName(String twitterName);

		/**
		 * Tech, Living, Style, Business, Entertainment, Celebrity, Sports, Culture, Games, News
		 */
		UpdateForumBuilder category(String category);

		/**
		 * Looks up a forum category by ID
		 */
		UpdateForumBuilder forumCategory(Integer forumCategory);

		UpdateForumBuilder attach(ArgumentConfig.Attach... attach);

		UpdateForumBuilder adsProductVideoEnabled(@HttlVar(name = "adsProductVideoEnabled", setter = BooleanTo1or0Setter.class) Boolean adsProductVideoEnabled);

		UpdateForumBuilder adsPositionTopEnabled(@HttlVar(name = "adsPositionTopEnabled", setter = BooleanTo1or0Setter.class) Boolean adsPositionTopEnabled);

		UpdateForumBuilder adsProductStoriesEnabled(@HttlVar(name = "adsProductStoriesEnabled", setter =BooleanTo1or0Setter.class) Boolean adsProductStoriesEnabled);

		UpdateForumBuilder organicDiscoveryEnabled(@HttlVar(name = "organicDiscoveryEnabled", setter = BooleanTo1or0Setter.class) Boolean organicDiscoveryEnabled);

		UpdateForumBuilder adsProductDisplayEnabled(@HttlVar(name = "adsProductDisplayEnabled", setter = BooleanTo1or0Setter.class) Boolean adsProductDisplayEnabled);

		UpdateForumBuilder unapproveLinks(@HttlVar(name = "unapproveLinks", setter = BooleanTo1or0Setter.class) Boolean unapproveLinks);

		UpdateForumBuilder disableDisqusBranding(@HttlVar(name = "disableDisqusBranding", setter = BooleanTo1or0Setter.class) Boolean disableDisqusBranding);

		UpdateForumBuilder linkAffiliationEnabled(@HttlVar(name = "linkAffiliationEnabled", setter = BooleanTo1or0Setter.class) Boolean linkAffiliationEnabled);

		UpdateForumBuilder adsProductLinksEnabled(@HttlVar(name = "adsProductLinksEnabled", setter = BooleanTo1or0Setter.class) Boolean adsProductLinksEnabled);

		UpdateForumBuilder adsProductLinksThumbnailsEnabled(@HttlVar(name = "adsProductLinksThumbnailsEnabled", setter = BooleanTo1or0Setter.class) Boolean adsProductLinksThumbnailsEnabled);

		UpdateForumBuilder adsPositionBottomEnabled(@HttlVar(name = "adsPositionBottomEnabled", setter = BooleanTo1or0Setter.class) Boolean adsPositionBottomEnabled);

		UpdateForumBuilder adsPositionInthreadEnabled(@HttlVar(name = "adsPositionInthreadEnabled", setter = BooleanTo1or0Setter.class) Boolean adultContent);

		UpdateForumBuilder allowAnonPost(@HttlVar(name = "allowAnonPost", setter = BooleanTo1or0Setter.class) Boolean allowAnonPost);

		UpdateForumBuilder adultContent(@HttlVar(name = "adultContent", setter = BooleanTo1or0Setter.class) Boolean adultContent);

		UpdateForumBuilder flaggingNotifications(@HttlVar(name = "flaggingNotifications", setter = BooleanTo1or0Setter.class) Boolean flaggingNotifications);

		UpdateForumBuilder flaggingEnabled(@HttlVar(name = "flaggingEnabled", setter = BooleanTo1or0Setter.class) Boolean flaggingEnabled);

		UpdateForumBuilder validateAllPosts(@HttlVar(name = "validateAllPosts", setter = BooleanTo1or0Setter.class) Boolean validateAllPosts);

		UpdateForumBuilder mediaembedEnabled(@HttlVar(name = "mediaembedEnabled", setter = BooleanTo1or0Setter.class) Boolean mediaembedEnabled);

	}
}

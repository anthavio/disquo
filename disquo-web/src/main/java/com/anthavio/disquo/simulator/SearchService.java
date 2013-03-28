package com.anthavio.disquo.simulator;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.PostState;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.ArgumentConfig.ThreadState;
import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.response.DisqusCursor;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;

@Component
public class SearchService {

	@Autowired
	private StoreService store;

	@Autowired
	private CopyServant copier;

	public DisqusResponse<List<DisqusPost>> findPosts(MultivaluedMap<String, String> params) {

		List<Long> thread = JaxRsUtil.OptLongList(params, "thread");
		List<String> forum = JaxRsUtil.OptList(params, "forum");
		List<Long> category = JaxRsUtil.OptLongList(params, "category");

		List<Related> related = JaxRsUtil.OptList(params, "related", Related.class);
		List<PostState> include = JaxRsUtil.OptList(params, "include", PostState.class);

		Date since = JaxRsUtil.OptDate(params, "since", Disqus.DATE_FORMAT);
		String cursor = JaxRsUtil.OptStr(params, "cursor");
		int limit = JaxRsUtil.OptInt(params, "limit", 25);
		Order order = JaxRsUtil.OptEnum(params, "order", Order.class, Order.desc);

		//This is only search supported - by thread :(
		List<DisqusPost> list = null;
		if (thread.size() > 0) {
			list = store.listPosts(thread.get(0));
			list = copier.copyPosts(list, related);
		}

		DisqusCursor dcursor;
		if (list.size() < limit) {
			dcursor = new DisqusCursor("zxzxzx");
		} else {
			dcursor = new DisqusCursor("zxzxzx");
		}

		return new DisqusResponse<List<DisqusPost>>(0, list, dcursor);
	}

	public DisqusResponse<List<DisqusThread>> findThreads(MultivaluedMap<String, String> params) {

		List<Long> category = JaxRsUtil.OptLongList(params, "category");
		List<String> forum = JaxRsUtil.OptList(params, "forum");
		List<Long> thread = JaxRsUtil.OptLongList(params, "thread");
		List<String> author = JaxRsUtil.OptList(params, "author");
		List<Related> related = JaxRsUtil.OptList(params, "related", Related.class);
		List<ThreadState> include = JaxRsUtil.OptList(params, "include", ThreadState.class);

		Date since = JaxRsUtil.OptDate(params, "since", Disqus.DATE_FORMAT);
		String cursor = JaxRsUtil.OptStr(params, "cursor");
		int limit = JaxRsUtil.OptInt(params, "limit", 25);
		Order order = JaxRsUtil.OptEnum(params, "order", Order.class, Order.desc);

		//This is only search supported - by forum :(
		List<DisqusThread> list = null;
		if (forum.size() > 0) {
			list = store.listThreads(forum.get(0));
			list = copier.copyThreads(list, related);
		}

		DisqusCursor dcursor = new DisqusCursor("zxzxzx");

		return new DisqusResponse<List<DisqusThread>>(list, dcursor);
	}
}

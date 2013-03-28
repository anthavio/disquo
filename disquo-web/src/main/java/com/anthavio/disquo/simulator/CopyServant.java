package com.anthavio.disquo.simulator;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.response.DisqusCategory;
import com.anthavio.disquo.api.response.DisqusForum;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.response.DisqusUser;

/**
 * Dozer fails to copy Object type fields for Forum, Author and Category
 * 
 * Anyway we need set those fields according Related input
 * 
 * @author martin.vanek
 *
 */
@Component
public class CopyServant {

	protected static final DozerBeanMapper dozer = new DozerBeanMapper();

	public DisqusForum copy(DisqusForum origin, List<Related> related) {
		DisqusForum copy = dozer.map(origin, DisqusForum.class);

		if (related.contains(Related.author)) {
			copy.setAuthor(origin.getAuthor());
		} else {
			copy.setAuthor(null);
		}

		return copy;
	}

	public List<DisqusForum> copyForums(List<DisqusForum> list, List<Related> related) {
		List<DisqusForum> output = new ArrayList<DisqusForum>(list.size());
		for (DisqusForum origin : list) {
			DisqusForum copy = copy(origin, related);
			output.add(copy);
		}
		return output;
	}

	public DisqusThread copy(DisqusThread origin, List<Related> related) {
		DisqusThread copy = dozer.map(origin, DisqusThread.class);

		if (related.contains(Related.forum)) {
			copy.setForum(origin.getForum());
		} else {
			//replace detail with id
			DisqusForum item = (DisqusForum) origin.getForum();
			copy.setForum(item.getId());
		}

		if (related.contains(Related.author)) {
			copy.setAuthor(origin.getAuthor());
		} else {
			DisqusUser item = (DisqusUser) origin.getAuthor();
			copy.setAuthor(item.getId());
		}

		if (related.contains(Related.category)) {
			copy.setCategory(origin.getCategory());
		} else {
			DisqusCategory item = (DisqusCategory) origin.getCategory();
			if (item != null) {
				copy.setCategory(item.getId());
			}
		}
		return copy;
	}

	public List<DisqusThread> copyThreads(List<DisqusThread> list, List<Related> related) {
		List<DisqusThread> output = new ArrayList<DisqusThread>(list.size());
		for (DisqusThread origin : list) {
			DisqusThread copy = copy(origin, related);
			output.add(copy);
		}
		return output;
	}

	public DisqusPost copy(DisqusPost origin, List<Related> related) {
		DisqusPost copy = dozer.map(origin, DisqusPost.class);

		if (related.contains(Related.forum)) {
			copy.setForum(origin.getForum());
		} else {
			DisqusForum item = (DisqusForum) origin.getForum();
			copy.setForum(item.getId());
		}
		if (related.contains(Related.thread)) {
			copy.setThread(origin.getThread());
		} else {
			DisqusThread item = (DisqusThread) origin.getThread();
			copy.setThread(item.getId());
		}
		return copy;
	}

	public List<DisqusPost> copyPosts(List<DisqusPost> list, List<Related> related) {
		List<DisqusPost> output = new ArrayList<DisqusPost>(list.size());
		for (DisqusPost origin : list) {
			DisqusPost copy = copy(origin, related);
			output.add(copy);
		}
		return output;
	}
}

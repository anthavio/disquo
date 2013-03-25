package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.category.CategoryCreateMethod;
import com.anthavio.disquo.api.category.CategoryDetailsMethod;
import com.anthavio.disquo.api.category.CategoryListMethod;
import com.anthavio.disquo.api.category.CategoryListPostsMethod;
import com.anthavio.disquo.api.category.CategoryListThreadsMethod;

/**
 * 
 * @author martin.vanek
 *
 */
public class CategoryTest extends DisqusMethodTest {

	@Test
	public void category() {
		CategoryCreateMethod create = disqus.category().create("forum", "title");
		create.setDefault(false);
		assertThat(getParameters(create).size()).isEqualTo(3);

		CategoryDetailsMethod details = disqus.category().details(666);
		assertThat(getParameters(details).size()).isEqualTo(1);
	}

	@Test
	public void list() {
		CategoryListMethod list = disqus.category().list("forum");
		list.addForum("forum2", "forum3");
		list.setSince_id("123");
		list.setCursor("cursor");
		list.setLimit(666);
		list.setOrder(Order.desc);
		assertThat(getParameters(list).size()).isEqualTo(7);
	}

	@Test
	public void listPosts() {
		CategoryListPostsMethod listPosts = disqus.category().listPosts(0);
		listPosts.setSince(new Date());
		listPosts.setCursor("c");
		listPosts.setLimit(666);
		listPosts.setOrder(Order.desc);
		assertThat(getParameters(listPosts).size()).isEqualTo(5);
	}

	@Test
	public void listThreads() {
		CategoryListThreadsMethod listThreads = disqus.category().listThreads(0);
		listThreads.addRelated(Related.author, Related.forum);
		listThreads.setSince(new Date());
		listThreads.setCursor("c");
		listThreads.setLimit(666);
		listThreads.setOrder(Order.desc);
		assertThat(getParameters(listThreads).size()).isEqualTo(7);
	}
}

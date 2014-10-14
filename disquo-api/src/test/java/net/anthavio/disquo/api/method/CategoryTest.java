package net.anthavio.disquo.api.method;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiCategories.ListPostsBuilder;
import net.anthavio.disquo.api.ApiCategories.ListThreadsBuilder;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.junit.Test;

/**
 * 
 * @author martin.vanek
 *
 */
public class CategoryTest extends DisqusMethodTest {

	@Test
	public void category() {
		disqus.categories().create("forum", "title", false);
		assertThat(getParameters().size()).isEqualTo(3 + 1 + 1);

		disqus.categories().details(666);
		assertThat(getParameters().size()).isEqualTo(1 + 1);
	}

	@Test
	public void list() {
		disqus.categories().list("forum", "0:0:0", 5, Order.desc);
		assertThat(getParameters().size()).isEqualTo(4 + 1);
	}

	@Test
	public void listPosts() {
		ListPostsBuilder builder = disqus.categories().listPosts(123456);
		builder.since(new Date(1));
		builder.cursor("0:0:0");
		builder.limit(666);
		builder.order(Order.desc);
		builder.execute();

		assertThat(getParameters().size()).isEqualTo(5 + 1);
	}

	@Test
	public void listThreads() {
		ListThreadsBuilder builder = disqus.categories().listThreads(123456);
		builder.related(Related.author, Related.forum);
		builder.since(new Date(1));
		builder.cursor("0:0:0");
		builder.limit(123);
		builder.order(Order.desc);
		builder.execute();

		assertThat(getParameters().size()).isEqualTo(6 + 1);
	}
}

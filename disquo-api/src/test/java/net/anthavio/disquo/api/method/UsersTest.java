package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.PostState;
import net.anthavio.disquo.api.DisqusMethodTest;
import net.anthavio.disquo.api.DisqusPageable;

import org.testng.annotations.Test;

public class UsersTest extends DisqusMethodTest {

	@Test
	public void checkUsername() {
		disqus.users().checkUsername("token", "username");

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void details() {
		disqus.users().details(333);

		assertThat(getParameters().size()).isEqualTo(1 + 1);
	}

	@Test
	public void follow() {
		disqus.users().follow("token", 333);

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void unfollow() {
		disqus.users().unfollow("token", 333);

		assertThat(getParameters().size()).isEqualTo(2 + 1);
	}

	@Test
	public void listActiveForums() {
		DisqusPageable page = new DisqusPageable("cursor");
		page.setLimit(99);
		page.setSince(new Date());
		page.setOrder(Order.desc);
		disqus.users().listActiveForums(333, page);

		assertThat(getParameters().size()).isEqualTo(5 + 1);
	}

	@Test
	public void listForums() {
		DisqusPageable page = new DisqusPageable("cursor");
		page.setLimit(99);
		page.setSince(new Date());
		page.setOrder(Order.desc);
		disqus.users().listForums(333, page);

		assertThat(getParameters().size()).isEqualTo(5 + 1);
	}

	@Test
	public void listPosts() {
		DisqusPageable page = new DisqusPageable("cursor");
		page.setLimit(99);
		page.setSince(new Date());
		page.setOrder(Order.desc);

		disqus.users().listPosts(333, page, PostState.unapproved, PostState.approved, PostState.spam, PostState.deleted,
				PostState.flagged, PostState.highlighted);

		assertThat(getParameters().size()).isEqualTo(6 + 1);
	}

}

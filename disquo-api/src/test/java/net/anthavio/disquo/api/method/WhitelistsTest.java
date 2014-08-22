package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiWhitelists.ListWhitelistBuilder;
import net.anthavio.disquo.api.ApiWhitelists.WhitelistAddBuilder;
import net.anthavio.disquo.api.ApiWhitelists.WhitelistRemoveBuilder;
import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.testng.annotations.Test;

public class WhitelistsTest extends DisqusMethodTest {

	@Test
	public void list() {
		ListWhitelistBuilder method = disqus.whitelists().listBuilder(Identity.access("zxzxzx"), "forum");
		method.related(Related.forum);
		method.type(FilterType.ALL);
		method.since(new Date(1));
		method.cursor("cursor");
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(8 + 1);
	}

	@Test
	public void add() {
		WhitelistAddBuilder method = disqus.whitelists().addBuilder(Identity.access("zxzxzx"), "forum");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.notes("Test note");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(5 + 1);
	}

	@Test
	public void remove() {
		WhitelistRemoveBuilder method = disqus.whitelists().removeBuilder(Identity.access("zxzxzx"), "forum");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(4 + 1);
	}
}

package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiWhitelists.ListWhitelistBuilder;
import net.anthavio.disquo.api.ApiWhitelists.WhitelistAddBuilder;
import net.anthavio.disquo.api.ApiWhitelists.WhitelistRemoveBuilder;
import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.testng.annotations.Test;

public class WhitelistsTest extends DisqusMethodTest {

	@Test
	public void list() {
		ListWhitelistBuilder method = disqus.whitelists().listBuilder("token", "forum");
		method.related(Related.forum);
		method.type(FilterType.ALL);
		method.since(new Date(1));
		method.cursor("cursor");
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(8 + 1);
		String data = "/whitelists/list.json?access_token=token&api_key=publicKey&cursor=cursor&forum=forum&limit=99&order=desc&related=forum&since=1970-01-01+01%3A00%3A00.001&type=domain&type=word&type=ip&type=user&type=thread_slug&type=email";
		assertThat(getRequest().getPathAndQuery()).isEqualTo(data);
	}

	@Test
	public void add() {
		WhitelistAddBuilder method = disqus.whitelists().addBuilder("token", "forum");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.notes("Test note");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(5 + 1);
		String data = "access_token=token&api_key=publicKey&email=kremilek%40chaloupka.cz&email=vochomurka%40chaloupka.cz&forum=forum&notes=Test+note&user%3Ausername=kremilek&user%3Ausername=vochomurka";
		assertThat(getRequest().getBody().getPayload()).isEqualTo(data);
	}

	@Test
	public void remove() {
		WhitelistRemoveBuilder method = disqus.whitelists().removeBuilder("token", "forum");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(4 + 1);
		String data = "access_token=token&api_key=publicKey&email=kremilek%40chaloupka.cz&email=vochomurka%40chaloupka.cz&forum=forum&user%3Ausername=kremilek&user%3Ausername=vochomurka";
		assertThat(getRequest().getBody().getPayload()).isEqualTo(data);
	}
}

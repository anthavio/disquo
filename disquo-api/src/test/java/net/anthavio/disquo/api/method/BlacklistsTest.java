package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiBlacklists.BlacklistAddBuilder;
import net.anthavio.disquo.api.ApiBlacklists.BlacklistRemoveBuilder;
import net.anthavio.disquo.api.ApiBlacklists.ListBlacklistBuilder;
import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusMethodTest;

import org.testng.annotations.Test;

public class BlacklistsTest extends DisqusMethodTest {

	@Test
	public void list() {
		ListBlacklistBuilder method = disqus.blacklists().listBuilder("token", "forum");
		method.related(Related.forum);
		method.type(FilterType.ALL);
		method.since(new Date(1));
		method.cursor("cursor");
		method.limit(99);
		method.order(Order.desc);
		method.execute();

		assertThat(getParameters().size()).isEqualTo(8 + 1);
		String data = "/blacklists/list.json?access_token=token&api_key=publicKey&cursor=cursor&forum=forum&limit=99&order=desc&related=forum&since=1970-01-01+01%3A00%3A00.001&type=domain&type=word&type=ip&type=user&type=thread_slug&type=email";
		assertThat(getRequest().getPathAndQuery()).isEqualTo(data);
	}

	@Test
	public void add() {
		BlacklistAddBuilder method = disqus.blacklists().addBuilder("token", "forum");
		method.domain("porn.com", "spam.com");
		method.word("fuck", "suck");
		method.ip("127.0.0.1", "127.0.1.1");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.retroactive(true);
		method.notes("Test note");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(9 + 1);
		String data = "access_token=token&api_key=publicKey&domain=porn.com&domain=spam.com&email=kremilek%40chaloupka.cz&email=vochomurka%40chaloupka.cz&forum=forum&ip=127.0.0.1&ip=127.0.1.1&notes=Test+note&retroactive=true&user%3Ausername=kremilek&user%3Ausername=vochomurka&word=fuck&word=suck";
		assertThat(getRequest().getBody().getPayload()).isEqualTo(data);
	}

	@Test
	public void remove() {
		BlacklistRemoveBuilder method = disqus.blacklists().removeBuilder("token", "forum");
		method.domain("porn.com", "spam.com");
		method.word("fuck", "suck");
		method.ip("127.0.0.1", "127.0.1.1");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(7 + 1);
		String data = "access_token=token&api_key=publicKey&domain=porn.com&domain=spam.com&email=kremilek%40chaloupka.cz&email=vochomurka%40chaloupka.cz&forum=forum&ip=127.0.0.1&ip=127.0.1.1&user%3Ausername=kremilek&user%3Ausername=vochomurka&word=fuck&word=suck";
		assertThat(getRequest().getBody().getPayload()).isEqualTo(data);
	}
}

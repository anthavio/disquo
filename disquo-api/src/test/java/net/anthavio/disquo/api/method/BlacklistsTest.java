package net.anthavio.disquo.api.method;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import net.anthavio.disquo.api.ApiBlacklists.BlacklistAddBuilder;
import net.anthavio.disquo.api.ApiBlacklists.BlacklistRemoveBuilder;
import net.anthavio.disquo.api.ApiBlacklists.ListBlacklistBuilder;
import net.anthavio.disquo.api.ArgumentConfig.FilterType;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.ArgumentConfig.Related;
import net.anthavio.disquo.api.DisqusApi.Identity;
import net.anthavio.disquo.api.DisqusMethodTest;
import net.anthavio.httl.HttlParameterSetter.ConfigurableParamSetter;
import net.anthavio.httl.util.HttlUtil;

import org.junit.Test;

public class BlacklistsTest extends DisqusMethodTest {

	@Test
	public void list() {
		ListBlacklistBuilder method = disqus.blacklists().listBuilder(Identity.access("zxzxzx"), "forum");
		method.related(Related.forum);
		method.type(FilterType.ALL);
		method.since(new Date(1));
		method.cursor("cursor");
		method.limit(99);
		method.order(Order.desc);
		method.execute();
		String dtExpected = HttlUtil.urlencode(((ConfigurableParamSetter) disqus.getSender().getConfig().getParamSetter())
				.getDateFormat().format(new Date(1)));
		assertThat(getParameters().size()).isEqualTo(8 + 1);
		String data = "/api/3.0/blacklists/list.json?access_token=zxzxzx&api_key=publicKey&cursor=cursor&forum=forum&limit=99&order=desc&related=forum&since="
				+ dtExpected + "&type=domain&type=word&type=ip&type=user&type=thread_slug&type=email";
		assertThat(getRequest().getPathAndQuery()).isEqualTo(data);
	}

	@Test
	public void add() {
		BlacklistAddBuilder method = disqus.blacklists().addBuilder(Identity.access("zxzxzx"), "forum");
		method.domain("porn.com", "spam.com");
		method.word("fuck", "suck");
		method.ip("127.0.0.1", "127.0.1.1");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.retroactive(true);
		method.notes("Test note");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(9 + 1);
		String data = "access_token=zxzxzx&api_key=publicKey&domain=porn.com&domain=spam.com&email=kremilek%40chaloupka.cz&email=vochomurka%40chaloupka.cz&forum=forum&ip=127.0.0.1&ip=127.0.1.1&notes=Test+note&retroactive=true&user%3Ausername=kremilek&user%3Ausername=vochomurka&word=fuck&word=suck";
		assertThat(getRequest().getBody().getPayload()).isEqualTo(data);
	}

	@Test
	public void remove() {
		BlacklistRemoveBuilder method = disqus.blacklists().removeBuilder(Identity.access("zxzxzx"), "forum");
		method.domain("porn.com", "spam.com");
		method.word("fuck", "suck");
		method.ip("127.0.0.1", "127.0.1.1");
		method.user("kremilek", "vochomurka");
		method.email("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.execute();

		assertThat(getParameters().size()).isEqualTo(7 + 1);
		String data = "access_token=zxzxzx&api_key=publicKey&domain=porn.com&domain=spam.com&email=kremilek%40chaloupka.cz&email=vochomurka%40chaloupka.cz&forum=forum&ip=127.0.0.1&ip=127.0.1.1&user%3Ausername=kremilek&user%3Ausername=vochomurka&word=fuck&word=suck";
		assertThat(getRequest().getBody().getPayload()).isEqualTo(data);
	}
}

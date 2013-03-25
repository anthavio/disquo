package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.ArgumentConfig.FilterType;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.blacklists.BlacklistAdd;
import com.anthavio.disquo.api.blacklists.BlacklistList;
import com.anthavio.disquo.api.blacklists.BlacklistRemove;

public class BlacklistsTest extends DisqusMethodTest {

	@Test
	public void list() {
		BlacklistList method = disqus.blacklists().list("forum");
		method.addRelated(Related.forum);
		method.addType(FilterType.ALL);
		method.setSince(new Date());
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);

		assertThat(getParameters(method).size()).isEqualTo(12);
	}

	@Test
	public void add() {
		BlacklistAdd method = disqus.blacklists().add("forum");
		method.addDomain("porn.com", "spam.com");
		method.addWord("fuck", "suck");
		method.addIp("127.0.0.1", "127.0.1.1");
		method.addUser("kremilek", "vochomurka");
		method.addEmail("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.setRetroactive(true);
		method.setNotes("Test note");

		assertThat(getParameters(method).size()).isEqualTo(13);
	}

	@Test
	public void remove() {
		BlacklistRemove method = disqus.blacklists().remove("forum");
		method.addDomain("porn.com", "spam.com");
		method.addWord("fuck", "suck");
		method.addIp("127.0.0.1", "127.0.1.1");
		method.addUser("kremilek", "vochomurka");
		method.addEmail("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");

		assertThat(getParameters(method).size()).isEqualTo(11);
	}
}

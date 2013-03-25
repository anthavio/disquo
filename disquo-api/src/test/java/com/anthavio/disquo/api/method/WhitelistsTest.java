package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Date;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.ArgumentConfig.FilterType;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.ArgumentConfig.Related;
import com.anthavio.disquo.api.whitelists.WhitelistAdd;
import com.anthavio.disquo.api.whitelists.WhitelistList;
import com.anthavio.disquo.api.whitelists.WhitelistRemove;

public class WhitelistsTest extends DisqusMethodTest {

	@Test
	public void list() {
		WhitelistList method = disqus.whitelists().list("forum");
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
		WhitelistAdd method = disqus.whitelists().add("forum");
		method.addUser("kremilek", "vochomurka");
		method.addEmail("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");
		method.setNotes("Test note");

		assertThat(getParameters(method).size()).isEqualTo(6);
	}

	@Test
	public void remove() {
		WhitelistRemove method = disqus.whitelists().remove("forum");
		method.addUser("kremilek", "vochomurka");
		method.addEmail("kremilek@chaloupka.cz", "vochomurka@chaloupka.cz");

		assertThat(getParameters(method).size()).isEqualTo(5);
	}
}

package com.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.anthavio.disquo.api.DisqusMethodTest;
import com.anthavio.disquo.api.ArgumentConfig.Order;
import com.anthavio.disquo.api.reactions.ReactionDetailsMethod;
import com.anthavio.disquo.api.reactions.ReactionListMethod;
import com.anthavio.disquo.api.reactions.ReactionRemoveMethod;
import com.anthavio.disquo.api.reactions.ReactionRestoreMethod;

public class ReactionsTest extends DisqusMethodTest {

	@Test
	public void list() {
		ReactionListMethod method = disqus.reactions().list("forum");
		//?method.setSince(new Date());
		//?method.addRelated(null);
		method.setCursor("cursor");
		method.setLimit(99);
		method.setOrder(Order.desc);

		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void details() {
		ReactionDetailsMethod method = disqus.reactions().details("forum", 1);

		assertThat(getParameters(method).size()).isEqualTo(2);
	}

	@Test
	public void remove() {
		ReactionRemoveMethod method = disqus.reactions().remove("forum", 1);
		method.addReaction(2, 3);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}

	@Test
	public void restore() {
		ReactionRestoreMethod method = disqus.reactions().restore("forum", 1);
		method.addReaction(2, 3);
		assertThat(getParameters(method).size()).isEqualTo(4);
	}
}

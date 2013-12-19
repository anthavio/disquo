package net.anthavio.disquo.api.method;

import static org.fest.assertions.api.Assertions.assertThat;

import net.anthavio.disquo.api.DisqusMethodTest;
import net.anthavio.disquo.api.ArgumentConfig.Order;
import net.anthavio.disquo.api.reactions.ReactionDetailsMethod;
import net.anthavio.disquo.api.reactions.ReactionListMethod;
import net.anthavio.disquo.api.reactions.ReactionRemoveMethod;
import net.anthavio.disquo.api.reactions.ReactionRestoreMethod;

import org.testng.annotations.Test;


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

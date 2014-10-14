package net.anthavio.disquo.api.response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusThreadDeserializer extends JsonDeserializer<DisqusThread> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public DisqusThread deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
			JsonProcessingException {

		DisqusThread bean = new DisqusThread();
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String field = jp.getCurrentName();
			jp.nextToken();
			if ("id".equals(field)) {
				bean.setId(DeserializationUtils.getLong(jp));

			} else if ("forum".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
					DisqusForum forum = jp.readValueAs(DisqusForum.class);
					bean.setForum(forum);
				} else {
					bean.setForum(jp.getValueAsString());
				}
			} else if ("category".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
					DisqusCategory category = jp.readValueAs(DisqusCategory.class);
					bean.setCategory(category);
				} else {
					bean.setCategory(DeserializationUtils.getLong(jp));
				}
			} else if ("author".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
					DisqusUser user = jp.readValueAs(DisqusUser.class);
					bean.setAuthor(user);
				} else {
					bean.setAuthor(DeserializationUtils.getLong(jp));
				}
			} else if ("title".equals(field)) {
				bean.setTitle(jp.getValueAsString());
			} else if ("clean_title".equals(field)) {
				bean.setCleanTitle(jp.getValueAsString());
			} else if ("ipAddress".equals(field)) {
				bean.setIpAddress(jp.getValueAsString());
			} else if ("createdAt".equals(field)) {
				bean.setCreatedAt(DeserializationUtils.getDate(jp));
			} else if ("identifiers".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_ARRAY) {
					List<String> values = new ArrayList<String>();
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						String item = jp.getValueAsString();
						values.add(item);
					}
					bean.setIdentifiers(values);
				}
			} else if ("posts".equals(field)) {
				bean.setPosts(jp.getIntValue());
			} else if ("reactions".equals(field)) {
				bean.setReactions(jp.getIntValue());
			} else if ("userScore".equals(field)) {
				bean.setUserScore(jp.getIntValue());
			} else if ("likes".equals(field)) {
				bean.setLikes(jp.getIntValue());
			} else if ("dislikes".equals(field)) {
				bean.setDislikes(jp.getIntValue());
			} else if ("isClosed".equals(field)) {
				bean.setIsClosed(jp.getBooleanValue());
			} else if ("isDeleted".equals(field)) {
				bean.setIsDeleted(jp.getBooleanValue());
			} else if ("userSubscription".equals(field)) {
				bean.setUserSubscription(jp.getBooleanValue());
			} else if ("canModerate".equals(field)) {
				bean.setCanModerate(jp.getBooleanValue());
			} else if ("canPost".equals(field)) {
				bean.setCanPost(jp.getBooleanValue());
			} else if ("feed".equals(field)) {
				bean.setFeed(jp.getValueAsString());
			} else if ("slug".equals(field)) {
				bean.setSlug(jp.getValueAsString());
			} else if ("link".equals(field)) {
				bean.setLink(jp.getValueAsString());
			} else if ("message".equals(field)) {
				bean.setMessage(jp.getValueAsString());
			} else if ("highlightedPost".equals(field)) {
				// ignore - old unused field
			} else {
				//throw new DisqusException("Unexpected element '" + field + "'");
				logger.debug("Unexpected element '" + field + "'");
			}
		}
		return bean;
	}
}

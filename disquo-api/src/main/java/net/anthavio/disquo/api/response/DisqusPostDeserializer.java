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
public class DisqusPostDeserializer extends JsonDeserializer<DisqusPost> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public DisqusPost deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		DisqusPost bean = new DisqusPost();
		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String field = jp.getCurrentName();
			jp.nextValue();
			if ("id".equals(field)) {
				bean.setId(DeserializationUtils.getLong(jp));

			} else if ("parent".equals(field)) {
				bean.setParent(DeserializationUtils.getLong(jp));

			} else if ("thread".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
					DisqusThread thread = jp.readValueAs(DisqusThread.class);
					bean.setThread(thread);
				} else {
					bean.setThread(DeserializationUtils.getLong(jp));
				}
			} else if ("forum".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
					DisqusForum forum = jp.readValueAs(DisqusForum.class);
					bean.setForum(forum);
				} else {
					bean.setForum(jp.getValueAsString());
				}
			} else if ("createdAt".equals(field)) {
				bean.setCreatedAt(DeserializationUtils.getDate(jp));

			} else if ("author".equals(field)) {
				DisqusUser user = jp.readValueAs(DisqusUser.class);
				bean.setAuthor(user);

			} else if ("ipAddress".equals(field)) {
				bean.setIpAddress(jp.getValueAsString());

			} else if ("media".equals(field)) {
				if (jp.getCurrentToken() != JsonToken.VALUE_NULL) {
					List<DisqusMedia> values = new ArrayList<DisqusMedia>();
					while (jp.nextToken() != JsonToken.END_ARRAY) {
						DisqusMedia item = jp.readValueAs(DisqusMedia.class);
						values.add(item);
					}
					bean.setMedia(values);
				}
			} else if ("isApproved".equals(field)) {
				bean.setIsApproved(jp.getBooleanValue());
			} else if ("isDeleted".equals(field)) {
				bean.setIsDeleted(jp.getBooleanValue());
			} else if ("isEdited".equals(field)) {
				bean.setIsEdited(jp.getBooleanValue());
			} else if ("isSpam".equals(field)) {
				bean.setIsSpam(jp.getBooleanValue());
			} else if ("isFlagged".equals(field)) {
				bean.setIsFlagged(jp.getBooleanValue());
			} else if ("isJuliaFlagged".equals(field)) {
				bean.setIsJuliaFlagged(jp.getBooleanValue());
			} else if ("isHighlighted".equals(field)) {
				bean.setIsHighlighted(DeserializationUtils.getBoolean(jp));
			} else if ("raw_message".equals(field)) {
				bean.setRaw_message(jp.getValueAsString());
			} else if ("message".equals(field)) {
				bean.setMessage(jp.getValueAsString());
			} else if ("url".equals(field)) {
				bean.setUrl(jp.getValueAsString());
			} else if ("likes".equals(field)) {
				bean.setLikes(jp.getIntValue());
			} else if ("dislikes".equals(field)) {
				bean.setDislikes(jp.getIntValue());
			} else if ("numReports".equals(field)) {
				bean.setNumReports(DeserializationUtils.getInt(jp));
			} else if ("points".equals(field)) {
				bean.setPoints(jp.getIntValue());
			} else if ("userScore".equals(field)) {
				bean.setUserScore(jp.getIntValue());
			} else if ("approxLoc".equals(field)) {
				bean.setApproxLoc(jp.readValueAs(DisqusCoordinates.class));
			} else {
				//throw new DisqusException("Unexpected element '" + field + "'");
				this.logger.debug("Unexpected element '" + field + "'");
			}
		}
		return bean;
	}

}

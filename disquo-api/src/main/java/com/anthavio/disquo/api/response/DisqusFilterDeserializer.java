package com.anthavio.disquo.api.response;

import java.io.IOException;

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
public class DisqusFilterDeserializer extends JsonDeserializer<DisqusFilter> {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public DisqusFilter deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
			JsonProcessingException {

		DisqusFilter bean = new DisqusFilter();

		while (jp.nextToken() != JsonToken.END_OBJECT) {
			String field = jp.getCurrentName();
			jp.nextToken();
			if ("id".equals(field)) {
				bean.setId(DeserializationUtils.getLong(jp));
			} else if ("forum".equals(field)) {
				bean.setForum(jp.getValueAsString());
			} else if ("notes".equals(field)) {
				bean.setNotes(jp.getValueAsString());
			} else if ("value".equals(field)) {
				if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
					DisqusUser user = jp.readValueAs(DisqusUser.class);
					bean.setUser(user);
				} else {
					bean.setValue(jp.getValueAsString());
				}
			} else if ("type".equals(field)) {
				bean.setType(jp.getValueAsString());
			} else if ("createdAt".equals(field)) {
				bean.setCreatedAt(DeserializationUtils.getDate(jp));
			} else {
				//throw new DisqusException("Unexpected element '" + field + "'");
				logger.warn("Unexpected element '" + field + "'");
			}
		}
		return bean;
	}
}

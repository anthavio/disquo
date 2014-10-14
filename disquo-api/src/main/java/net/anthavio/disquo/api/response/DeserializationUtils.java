package net.anthavio.disquo.api.response;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.anthavio.disquo.api.DisqusApi;

import org.apache.commons.lang.UnhandledException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * 
 * @author martin.vanek
 *
 */
public class DeserializationUtils {

	//2012-11-14T15:12:54
	public static Date getDate(JsonParser jp) throws IOException {
		JsonToken ctoken = jp.getCurrentToken();
		if (ctoken == JsonToken.VALUE_NULL) {
			return null;
		} else if (ctoken == JsonToken.VALUE_NUMBER_INT) {
			return new Date(jp.getLongValue()); //Assuming that it is unix time
		} else if (ctoken == JsonToken.VALUE_STRING) {
			try {
				return new SimpleDateFormat(DisqusApi.DATE_FORMAT).parse(jp.getText());
			} catch (ParseException px) {
				throw new UnhandledException(px);
			}
		} else {
			throw new IllegalStateException("Unknown token " + ctoken);
		}
	}

	public static Long getLong(JsonParser jp) throws IOException {
		JsonToken ctoken = jp.getCurrentToken();
		if (ctoken == JsonToken.VALUE_NULL) {
			return null;
		} else if (ctoken == JsonToken.VALUE_NUMBER_INT) {
			return jp.getLongValue();
		} else if (ctoken == JsonToken.VALUE_STRING) {
			return Long.parseLong(jp.getText());
		} else {
			throw new IllegalStateException("Unknown token " + ctoken);
		}
	}

	public static Integer getInt(JsonParser jp) throws IOException {
		JsonToken ctoken = jp.getCurrentToken();
		if (ctoken == JsonToken.VALUE_NULL) {
			return null;
		} else if (ctoken == JsonToken.VALUE_NUMBER_INT) {
			return jp.getIntValue();
		} else if (ctoken == JsonToken.VALUE_STRING) {
			return Integer.parseInt(jp.getText());
		} else {
			throw new IllegalStateException("Unknown token " + ctoken);
		}
	}

	public static Boolean getBoolean(JsonParser jp) throws IOException {
		JsonToken ctoken = jp.getCurrentToken();
		if (ctoken == JsonToken.VALUE_NULL) {
			return null;
		} else if (ctoken == JsonToken.VALUE_FALSE) {
			return false;
		} else if (ctoken == JsonToken.VALUE_TRUE) {
			return true;
		} else {
			throw new IllegalStateException("Unknown token " + ctoken);
		}
	}

}

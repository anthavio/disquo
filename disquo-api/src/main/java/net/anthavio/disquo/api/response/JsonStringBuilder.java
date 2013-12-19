package net.anthavio.disquo.api.response;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author martin.vanek
 *
 */
public class JsonStringBuilder {

	private static final ObjectMapper jackson = new ObjectMapper();
	static {
		jackson.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	public static String toString(Object o) {
		return toString(o, true);
	}

	public static String toString(Object o, boolean pretty) {
		if (o == null) {
			return null;
		} else {
			StringWriter sw = new StringWriter();
			try {
				if (pretty) {
					jackson.writer(new DefaultPrettyPrinter()).writeValue(sw, o);
				} else {
					jackson.writeValue(sw, o);
				}
				return sw.toString();
			} catch (IOException e) {
				//XXX signalize exception ?
				return ToStringBuilder.reflectionToString(o);
			}
		}
	}
}

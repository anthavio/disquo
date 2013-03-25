package com.nature.disqus.sim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang.StringUtils;

import com.anthavio.disquo.api.DisqusServerException;

/**
 * 
 * @author martin.vanek
 *
 */
public class JaxRsUtil {

	// String

	public static String ReqStr(MultivaluedMap<String, String> form, String name) {
		List<String> list = form.get(name);
		if (list == null || list.size() == 0) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is missing");
		} else if (list.size() != 1) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is not multiparameter");
		}
		String value = list.get(0);
		if (StringUtils.isBlank(value)) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is blank");
		}
		return value;
	}

	public static String OptStr(MultivaluedMap<String, String> form, String name) {
		List<String> list = form.get(name);
		if (list == null || list.size() == 0) {
			return null;
		} else if (list.size() != 1) {
			throw new DisqusServerException(400, 2, "Parameter '" + name + "' is not multiparameter");
		}
		String value = list.get(0);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		return value;
	}

	// Integer

	public static int OptInt(MultivaluedMap<String, String> form, String name, int defval) {
		Integer value = OptInt(form, name);
		if (value != null) {
			return value;
		} else {
			return defval;
		}
	}

	public static Integer OptInt(MultivaluedMap<String, String> form, String name) {
		String value = OptStr(form, name);
		if (value != null) {
			return Integer.parseInt(value);
		} else {
			return null;
		}
	}

	public static int ReqInt(MultivaluedMap<String, String> form, String name) {
		String value = ReqStr(form, name);
		return Integer.parseInt(value);
	}

	// Long

	public static long OptLong(MultivaluedMap<String, String> form, String name, long defval) {
		Long value = OptLong(form, name);
		if (value != null) {
			return value;
		} else {
			return defval;
		}
	}

	public static Long OptLong(MultivaluedMap<String, String> form, String name) {
		String value = OptStr(form, name);
		if (value != null) {
			return Long.parseLong(value);
		} else {
			return null;
		}
	}

	public static long ReqLong(MultivaluedMap<String, String> form, String name) {
		String value = ReqStr(form, name);
		return Long.parseLong(value);
	}

	// Date

	public static Date OptDate(MultivaluedMap<String, String> form, String name, String dateFormat) {
		String value = OptStr(form, name);
		if (value != null) {
			try {
				return new SimpleDateFormat(dateFormat).parse(value);
			} catch (ParseException px) {
				throw new DisqusServerException(400, 2, "Parameter '" + name + "' value '" + value + "' is not valid date");
			}
		} else {
			return null;
		}
	}

	//Enum

	public static <T extends Enum<T>> T OptEnum(MultivaluedMap<String, String> params, String name, Class<T> klass,
			T defval) {
		T value = OptEnum(params, name, klass);
		if (value != null) {
			return value;
		} else {
			return defval;
		}
	}

	public static <T extends Enum<T>> T OptEnum(MultivaluedMap<String, String> params, String name, Class<T> klass) {
		String value = OptStr(params, name);
		if (value != null) {
			T valueOf = Enum.valueOf(klass, value);
			return valueOf;
		} else {
			return null;
		}
	}

	public static <T extends Enum<T>> T ReqEnum(MultivaluedMap<String, String> params, String name, Class<T> klass) {
		T value = OptEnum(params, name, klass);
		if (value != null) {
			return value;
		} else {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is missing");
		}
	}

	//List Enum

	public static <T extends Enum<T>> List<T> OptList(MultivaluedMap<String, String> params, String name, Class<T> klass,
			T defval) {
		List<T> value = OptList(params, name, klass);
		if (value != null) {
			return value;
		} else {
			List<T> list = new LinkedList<T>();
			list.add(defval);
			return list;
		}
	}

	public static <T extends Enum<T>> List<T> OptList(MultivaluedMap<String, String> params, String name, Class<T> klass) {
		List<String> values = OptList(params, name);
		List<T> list = new LinkedList<T>();
		for (String value : values) {
			T valueOf = Enum.valueOf(klass, value);
			list.add(valueOf);
		}
		return list;
	}

	public static <T extends Enum<T>> List<T> ReqList(MultivaluedMap<String, String> params, String name, Class<T> klass) {
		List<T> list = OptList(params, name, klass);
		if (list == null || list.size() == 0) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is missing");
		}
		return list;
	}

	// List String

	private static List<String> EMPTY = Collections.unmodifiableList(new LinkedList<String>());

	public static List<String> OptList(MultivaluedMap<String, String> params, String name) {
		List<String> values = params.get(name);
		if (values == null) {
			return EMPTY;
		} else {
			List<String> output = new LinkedList<String>();
			for (String value : values) {
				if (StringUtils.isNotBlank(value)) {
					output.add(value);
				}
			}
			return output;
		}
	}

	public static List<String> ReqList(MultivaluedMap<String, String> params, String name) {
		List<String> list = OptList(params, name);
		if (list == null || list.size() == 0) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is missing");
		}
		return list;
	}

	// List Integer

	public static List<Integer> OptIntList(MultivaluedMap<String, String> params, String name) {
		List<String> values = OptList(params, name);
		List<Integer> output = new LinkedList<Integer>();
		for (String value : values) {
			output.add(Integer.parseInt(value));
		}
		return output;
	}

	public static List<Integer> ReqIntList(MultivaluedMap<String, String> params, String name) {
		List<Integer> list = OptIntList(params, name);
		if (list == null || list.size() == 0) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is missing");
		}
		return list;
	}

	//List Long

	public static List<Long> OptLongList(MultivaluedMap<String, String> params, String name) {
		List<String> values = OptList(params, name);
		List<Long> output = new LinkedList<Long>();
		for (String value : values) {
			output.add(Long.parseLong(value));
		}
		return output;
	}

	public static List<Long> ReqLongList(MultivaluedMap<String, String> params, String name) {
		List<Long> list = OptLongList(params, name);
		if (list == null || list.size() == 0) {
			throw new DisqusServerException(400, 2, "Required parameter '" + name + "' is missing");
		}
		return list;
	}

}

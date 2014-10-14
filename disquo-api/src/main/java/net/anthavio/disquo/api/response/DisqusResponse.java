package net.anthavio.disquo.api.response;

import java.io.Serializable;

/**
 * 
 * @author martin.vanek
 *
 * @param <T>
 */
public class DisqusResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer code;

	private T response;

	private DisqusCursor cursor;

	public DisqusResponse(T response) {
		this(0, response, null);
	}

	public DisqusResponse(T response, DisqusCursor cursor) {
		this(0, response, cursor);
	}

	public DisqusResponse(int code, T response, DisqusCursor cursor) {
		this.code = code;
		this.response = response;
		this.cursor = cursor;
	}

	protected DisqusResponse() {
		// just in case...
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public DisqusCursor getCursor() {
		return cursor;
	}

	public void setCursor(DisqusCursor cursor) {
		this.cursor = cursor;
	}

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}
}

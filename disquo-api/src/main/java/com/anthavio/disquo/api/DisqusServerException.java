package com.anthavio.disquo.api;

public class DisqusServerException extends DisqusException {

	private static final long serialVersionUID = 1L;

	private final int httpCode;

	private final int disqusCode;

	private final String disqusMessage;

	public DisqusServerException(int httpCode, int disqusCode, String disqusMessage) {
		super("http: " + httpCode + " code: " + disqusCode + " " + disqusMessage);
		this.httpCode = httpCode;
		this.disqusCode = disqusCode;
		this.disqusMessage = disqusMessage;
	}

	public int getHttpCode() {
		return this.httpCode;
	}

	public int getDisqusCode() {
		return this.disqusCode;
	}

	public String getDisqusMessage() {
		return this.disqusMessage;
	}
}

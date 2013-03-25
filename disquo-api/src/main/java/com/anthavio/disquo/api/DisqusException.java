package com.anthavio.disquo.api;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class DisqusException extends NestableRuntimeException {

	private static final long serialVersionUID = 1L;

	public DisqusException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DisqusException(String msg) {
		super(msg);
	}

	public DisqusException(Throwable cause) {
		super(cause);
	}

}

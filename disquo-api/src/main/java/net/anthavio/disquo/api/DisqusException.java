package net.anthavio.disquo.api;

/**
 * 
 * @author martin.vanek
 *
 */
public class DisqusException extends RuntimeException {

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

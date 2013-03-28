package com.anthavio.disquo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.anthavio.hatatitla.HttpSender;
import com.anthavio.hatatitla.HttpSenderConfig;
import com.anthavio.hatatitla.SenderRequest;
import com.anthavio.hatatitla.SenderResponse;

/**
 * Sometimes we need to test what is sent remote server without actualy sending it...
 * 
 * @author martin.vanek
 *
 */
public class TestFakeSender extends HttpSender {

	private SenderRequest request; //from last doExecute invocation
	private String path;//from last doExecute invocation
	private String query;//from last doExecute invocation

	private SenderResponse response; //what to return form doExecute

	private boolean closed;

	public TestFakeSender(SenderResponse response) {
		super(new HttpSenderConfig("http://never.really.sent.anywhere/"));
		if (response == null) {
			throw new IllegalArgumentException("response is null");
		}
		this.response = response;
	}

	@Override
	public void close() throws IOException {
		this.closed = true;
	}

	@Override
	protected SenderResponse doExecute(SenderRequest request, String path, String query) throws IOException {
		this.request = request;
		this.path = path;
		this.query = query;
		return this.response;
	}

	public void setResponse(int code, String body) {
		this.response = new FakeResponse(code, body);
	}

	public boolean isClosed() {
		return closed;
	}

	/**
	 * @return request from last doExecute invocation
	 */
	public SenderRequest getRequest() {
		return request;
	}

	/**
	 * @return url path from last doExecute invocation
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return url query from last doExecute invocation
	 */
	public String getQuery() {
		return query;
	}

	public SenderResponse getResponse() {
		return response;
	}

	public static class FakeResponse extends SenderResponse {

		private byte[] bodyBytes;

		private boolean closed;

		public FakeResponse(int code, String body) {
			super(code, "fake " + code + " http response", null, null);
			this.bodyBytes = body.getBytes(Charset.forName("utf-8"));
		}

		public InputStream getStream() {
			return new ByteArrayInputStream(bodyBytes);
		}

		@Override
		public void close() throws IOException {
			this.closed = true;
		}

		public boolean isClosed() {
			return closed;
		}

	}

}

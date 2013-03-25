package com.anthavio.disquo.api;

/**
 * 
 * @author martin.vanek
 *
 */
public class HtmlSanitizer {

	public static enum DisallowedMode {
		REMOVE, ESCAPE;
	}

	private DisallowedMode dmode;

	//http://help.disqus.com/customer/portal/articles/466253-what-html-tags-are-allowed-within-comments-
	private String[] allowed = new String[] { "br", "a", "b", "blockquote", "caption", "cite", "code", "i", "p", "pre",
			"q", "samp", "span", "s", "strike", "strong", "u" };

	private HtmlSanitizer() {
		this.dmode = DisallowedMode.ESCAPE;
	}

	public String sanitize(String input, String[] allowedTags, boolean remove) {
		StringBuilder sb = new StringBuilder((int) (input.length() * 1.2));
		boolean inEname = false; //we seen '<' but we did not yet '>'
		for (int i = 0; i < input.length(); ++i) {
			char c = input.charAt(i);
			if (c == '<') {

			} else if (c == '>') {

			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}

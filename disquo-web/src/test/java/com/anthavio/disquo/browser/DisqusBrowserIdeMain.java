package com.anthavio.disquo.browser;

import com.anthavio.jetty.Jetty6Wrapper;

/**
 * Main class in IDE
 * 
 * @author martin.vanek
 *
 */
public class DisqusBrowserIdeMain {

	public static void main(String[] args) {
		new Jetty6Wrapper("src/main/jetty", 5959).start();
	}
}

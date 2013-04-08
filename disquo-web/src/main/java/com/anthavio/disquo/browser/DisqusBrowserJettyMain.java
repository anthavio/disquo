package com.anthavio.disquo.browser;

import com.anthavio.jetty.Jetty6Wrapper;

/**
 * Main class in standalone assembly. Don't work in IDE due to invalid path to jetty root directory
 * 
 * @author martin.vanek
 * 
 */
public class DisqusBrowserJettyMain {

	public static void main(String[] args) {
		try {
			new Jetty6Wrapper().start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}

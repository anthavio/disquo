package com.nature.disqus.browser;

import com.anthavio.jetty.Jetty6Wrapper;

/**
 * Main class in standalone assembly
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

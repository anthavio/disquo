package com.anthavio.disquo.browser;

import com.anthavio.jetty.Jetty6Wrapper;

public class SimpleMain {

	public static void main(String[] args) {
		try {
			Jetty6Wrapper jetty = new Jetty6Wrapper("src/main/jetty", 0);
			System.out.println(jetty.getPort());
			jetty.start();
			System.out.println(jetty.getPort());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}

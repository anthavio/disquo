package net.anthavio.disquo.browser;

import net.anthavio.sewer.jetty.JettyWrapper;

/**
 * Main class in IDE
 * 
 * -noverify -javaagent:${JREBEL_DIR}/jrebel.jar 
 * -Ddandelion.dev.mode=true
 * -Xms128m -Xmx256m -XX:MaxPermSize=128m
 * 
 * @author martin.vanek
 *
 */
public class DisqusBrowserIdeMain {

	public static void main(String[] args) {
		new JettyWrapper("src/main/jetty", 5959).start();
	}
}

package com.anthavio.jersey.server;

import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Jersey 1.x is using java util logging
 * 
 * This ServletContextListener implementation is just a simple SLF4JBridgeHandler installer
 * 
 * @author martin.vanek
 *
 */
public class SLF4JBridgeInitializer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Logger rootLogger = LogManager.getLogManager().getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		rootLogger.removeHandler(handlers[0]);
		SLF4JBridgeHandler.install();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SLF4JBridgeHandler.uninstall();
	}

}

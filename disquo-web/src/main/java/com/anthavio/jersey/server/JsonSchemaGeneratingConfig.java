package com.anthavio.jersey.server;

import java.util.List;

import com.sun.jersey.api.wadl.config.WadlGeneratorConfig;
import com.sun.jersey.api.wadl.config.WadlGeneratorDescription;

/**
 * Jersey 1.x WadlGeneratorConfig
 * 
 * web.xml example
 * 
 * <servlet>
 *	<servlet-name>JerseySpringServlet</servlet-name>
 *	<servlet-class>com.sun.jersey.spi.spring.container.servlet.SpringServlet</servlet-class>
 *	<init-param>
 * 		<param-name>com.sun.jersey.config.property.WadlGeneratorConfig</param-name>
 * 		<param-value>com.nature.jersey.server.JsonSchemaGeneratingConfig</param-value>
 *	</init-param>
 * </servlet>
 * 
 * @author martin.vanek
 *
 */
public class JsonSchemaGeneratingConfig extends WadlGeneratorConfig {

	@Override
	public List<WadlGeneratorDescription> configure() {
		WadlGeneratorConfigDescriptionBuilder builder = generator(JsonSchemaWadlGenerator.class);
		//XXX build Jackson JSON schema based version of WadlGeneratorJAXBGrammarGenerator
		return builder.descriptions();
	}
}

package com.nature.disqus.sim;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;

import com.anthavio.disquo.api.Disqus;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author martin.vanek
 *
 */
//@Provider
//@Produces(MediaType.APPLICATION_JSON)
public class DisqusJacksonContextResolver implements ContextResolver<ObjectMapper> {

	private ObjectMapper mapper = new ObjectMapper();

	public DisqusJacksonContextResolver() {
		//System.out.println("DisqusJacksonContextResolver init");
		mapper.setDateFormat(new SimpleDateFormat(Disqus.DATE_FORMAT));
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}

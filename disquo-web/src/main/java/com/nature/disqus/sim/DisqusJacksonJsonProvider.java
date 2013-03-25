package com.nature.disqus.sim;

import java.text.SimpleDateFormat;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.anthavio.disquo.api.Disqus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * 
 * @author martin.vanek
 *
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class DisqusJacksonJsonProvider extends JacksonJsonProvider {

	public DisqusJacksonJsonProvider() {
		//System.out.println("DisqusJacksonJsonProvider init");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat(Disqus.DATE_FORMAT));
		setMapper(mapper);
	}

}

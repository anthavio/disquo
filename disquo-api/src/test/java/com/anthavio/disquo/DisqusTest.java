package com.anthavio.disquo;

import java.io.IOException;
import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.posts.PostGetContextMethod;
import com.anthavio.disquo.api.response.DisqusImportDetails;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

public class DisqusTest {

	public static void main(String[] args) {

		TestInputData tidata = TestInputData.load("disqus-nature.properties");
		Disqus disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());
		try {
			disqus.setUseApplicationToken(true);
			DisqusResponse<List<DisqusImportDetails>> ilist = disqus.imports().list("jasmineimporttest").execute();

			DisqusImportDetails stats = ilist.getResponse().get(0);
			System.out.println(stats);

			DisqusResponse<DisqusImportDetails> idetails = disqus.imports().details("jasmineimporttest", stats.getId())
					.execute();

			DisqusImportDetails details = idetails.getResponse();
			System.out.println(details);
			if (true)
				return;

			String forum = tidata.getForum();
			PostGetContextMethod context = disqus.posts().getContext(768827349);
			context.pretty();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static <T> String jsonSchemaGenerator(Class<T> clazz, boolean format) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonSchema jsonSchema = mapper.generateJsonSchema(clazz);
		String string = jsonSchema.toString();
		if (format) {
			string = mapper.writer(new DefaultPrettyPrinter()).writeValueAsString(jsonSchema);
		}
		return string;
	}
}

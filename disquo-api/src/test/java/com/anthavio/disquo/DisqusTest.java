package com.anthavio.disquo;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.anthavio.disquo.api.Disqus;
import com.anthavio.disquo.api.posts.PostGetContextMethod;
import com.anthavio.disquo.api.response.DisqusImportDetails;
import com.anthavio.disquo.api.response.DisqusPost;
import com.anthavio.disquo.api.response.DisqusResponse;
import com.anthavio.disquo.api.response.DisqusThread;
import com.anthavio.disquo.api.threads.ThreadCreateMethod;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

public class DisqusTest {

	public static void main(String[] args) {

		TestInputData tidata = TestInputData.load("disqus.properties");
		Disqus disqus = new Disqus(tidata.getApplicationKeys(), tidata.getUrl());
		try {
			disqus.setUseApplicationToken(true);
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(Calendar.YEAR, 2010);
			calendar.set(Calendar.MONTH, 5);
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			//disqus.posts().create(1247667419, "Some post " + new Date()).setDate(calendar.getTime()).execute();

			ThreadCreateMethod method = disqus.threads().create("dajc", "Article without comments")
					.setIdentifier("id" + System.currentTimeMillis()).setDate(calendar.getTime());

			DisqusResponse<DisqusThread> response = method.execute();
			DisqusThread thread = response.getResponse();
			System.out.println(thread);
			if (true)
				return;

			DisqusResponse<List<DisqusImportDetails>> ilist = disqus.imports().list("testnaturefronthalf1").execute();

			DisqusImportDetails stats = ilist.getResponse().get(0);
			System.out.println(stats);

			DisqusResponse<DisqusImportDetails> idetails = disqus.imports().details("testnaturefronthalf1", stats.getId())
					.execute();

			DisqusImportDetails details = idetails.getResponse();
			System.out.println(details);
			if (true)
				return;
			String forum = tidata.getForum();
			PostGetContextMethod context = disqus.posts().getContext(768827349);
			DisqusResponse<List<DisqusPost>> execute = context.execute();
			for (DisqusPost post : execute.getResponse()) {
				System.out.println(post);
			}
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

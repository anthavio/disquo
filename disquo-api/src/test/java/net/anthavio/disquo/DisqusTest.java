package net.anthavio.disquo;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import net.anthavio.disquo.api.DisqusApi;
import net.anthavio.disquo.api.response.DisqusPost;
import net.anthavio.disquo.api.response.DisqusResponse;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

public class DisqusTest {

	public static void main(String[] args) {

		TestInputData tidata = TestInputData.load("disqus-test.properties");
		DisqusApi disqus = new DisqusApi(tidata.getApplicationKeys(), tidata.getUrl());
		try {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.set(Calendar.YEAR, 2010);
			calendar.set(Calendar.MONTH, 5);
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			//disqus.posts().create(1247667419, "Some post " + new Date()).setDate(calendar.getTime()).execute();
			/*
			DisqusResponse<List<DisqusImportDetails>> ilist = disqus.imports().list("testnaturefronthalf1", null);

			DisqusImportDetails stats = ilist.getResponse().get(0);
			System.out.println(stats);

			DisqusResponse<DisqusImportDetails> idetails = disqus.imports().details("testnaturefronthalf1",
					"" + stats.getId());

			DisqusImportDetails details = idetails.getResponse();
			System.out.println(details);
			*/

			//String forum = tidata.getForum();
			DisqusResponse<List<DisqusPost>> execute = disqus.posts().getContext(768827349, null);
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

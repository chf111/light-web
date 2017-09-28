package up.light.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class JsonUtil {
	private static ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

	public static String toJson(Object obj) throws JsonProcessingException {
		return writer.writeValueAsString(obj);
	}

}

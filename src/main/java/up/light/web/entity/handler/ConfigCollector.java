package up.light.web.entity.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

public class ConfigCollector implements ResultHandler<Map<String, String>> {
	private Map<String, String> configs = new HashMap<>();

	@Override
	public void handleResult(ResultContext<? extends Map<String, String>> resultContext) {
		Map<String, String> one = resultContext.getResultObject();
		String key = one.get("key");
		String value = one.get("value");
		if (key != null && value != null) {
			configs.put(key, value);
		}
	}

	public Map<String, String> getConfigs() {
		return configs;
	}

}

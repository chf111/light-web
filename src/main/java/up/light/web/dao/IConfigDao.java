package up.light.web.dao;

import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

public interface IConfigDao {

	int insertConfigs(int pid, Map<String, String> configs);

	int updateConfig(int pid, String key, String value);

	int deleteConfig(int pid, String key);

	void selectConfig(int pid, ResultHandler<Map<String, String>> handler);

}

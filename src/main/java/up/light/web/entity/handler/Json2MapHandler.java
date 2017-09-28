package up.light.web.entity.handler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 将JSON解析成Map<String, String>
 */
@MappedTypes(Map.class)
public class Json2MapHandler extends BaseTypeHandler<Map<String, String>> {
	private ObjectMapper mapper;

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Map<String, String> parameter, JdbcType jdbcType)
			throws SQLException {
		try {
			ps.setString(i, getMapper().writeValueAsString(parameter));
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String json = rs.getString(columnName);
		return json2Map(json);
	}

	@Override
	public Map<String, String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String json = rs.getString(columnIndex);
		return json2Map(json);
	}

	@Override
	public Map<String, String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String json = cs.getString(columnIndex);
		return json2Map(json);
	}

	private Map<String, String> json2Map(String json) {
		try {
			return getMapper().readValue(json, getType());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ObjectMapper getMapper() {
		if (mapper == null) {
			synchronized (this) {
				if (mapper == null) {
					mapper = new ObjectMapper();
				}
			}
		}
		return mapper;
	}

	private synchronized JavaType getType() {
		return getMapper().getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
	}

}

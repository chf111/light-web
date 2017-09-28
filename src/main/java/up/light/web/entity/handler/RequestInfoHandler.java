package up.light.web.entity.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import up.light.web.entity.json.RequestInfo;

@MappedTypes(RequestInfo.class)
public class RequestInfoHandler extends BaseTypeHandler<RequestInfo> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, RequestInfo parameter, JdbcType jdbcType)
			throws SQLException {
		throw new RuntimeException("unsupported operation");
	}

	@Override
	public RequestInfo getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String json = rs.getString(columnName);
		return json2RequestInfo(json);
	}

	@Override
	public RequestInfo getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String json = rs.getString(columnIndex);
		return json2RequestInfo(json);
	}

	@Override
	public RequestInfo getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String json = cs.getString(columnIndex);
		return json2RequestInfo(json);
	}

	private RequestInfo json2RequestInfo(String json) {
		// TODO implement json2RequestInfo
		return new RequestInfo();
	}

}

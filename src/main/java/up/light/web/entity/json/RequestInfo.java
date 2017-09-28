package up.light.web.entity.json;

import java.util.List;

/*
 * 对应t_service_info表的SERVICE_CONTENT字段
 */
public class RequestInfo {
	private String method;
	private List<FieldInfo> params;
	private List<FieldInfo> results;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<FieldInfo> getParams() {
		return params;
	}

	public void setParams(List<FieldInfo> params) {
		this.params = params;
	}

	public List<FieldInfo> getResults() {
		return results;
	}

	public void setResults(List<FieldInfo> results) {
		this.results = results;
	}

}

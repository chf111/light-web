package up.light.web.entity;

import java.util.Map;

import up.light.web.entity.json.RequestInfo;

public class StepDO {
	private int serviceId;
	private String url;
	private Map<String, String> resultPaths;
	private byte type;
	private byte orderNo;
	private RequestInfo info;

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getResultPaths() {
		return resultPaths;
	}

	public void setResultPaths(Map<String, String> resultPaths) {
		this.resultPaths = resultPaths;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(byte orderNo) {
		this.orderNo = orderNo;
	}

	public RequestInfo getInfo() {
		return info;
	}

	public void setInfo(RequestInfo info) {
		this.info = info;
	}

}

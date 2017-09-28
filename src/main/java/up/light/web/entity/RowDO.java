package up.light.web.entity;

import java.util.List;
import java.util.Map;

public class RowDO {
	private int id;
	private int mid;
	private Map<String, List<String>> values;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public Map<String, List<String>> getValues() {
		return values;
	}

	public void setValues(Map<String, List<String>> values) {
		this.values = values;
	}

}

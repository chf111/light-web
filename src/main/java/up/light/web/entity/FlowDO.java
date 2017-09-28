package up.light.web.entity;

import java.util.List;

public class FlowDO {
	private int id;
	private int mid;
	private List<StepDO> steps;
	private String initScript;

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

	public List<StepDO> getSteps() {
		return steps;
	}

	public void setSteps(List<StepDO> steps) {
		this.steps = steps;
	}

	public String getInitScript() {
		return initScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}

}
